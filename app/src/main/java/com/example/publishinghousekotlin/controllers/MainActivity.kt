package com.example.publishinghousekotlin.controllers


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.basics.Messages
import com.example.publishinghousekotlin.databinding.ActivityMainBinding
import com.example.publishinghousekotlin.http.responses.JwtResponse
import com.example.publishinghousekotlin.models.TypeProduct
import com.example.publishinghousekotlin.models.User
import com.example.publishinghousekotlin.models.UserRole


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var navController: NavController? = null
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener {
            goToAddItem()
        }

        user = JwtResponse.getFromMemory(applicationContext, applicationContext.resources.getString(R.string.keyForJwtResponse))!!.user
        hideItemsMenu()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_slideshow, R.id.typeProductsScreen
            ), drawerLayout
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController!!)

        setUserInfo()
        goToFragmentAfterAction()
        listenerOfSelectedItemNavView()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun hideItemsMenu(){
        if(user?.role == UserRole.CUSTOMER.name){
            val itemsToHide = setOf(R.id.nav_customers, R.id.nav_materials, R.id.nav_employees, R.id.nav_printingHouses, R.id.nav_typeProducts)

            for(itemId in itemsToHide){
                val menuItem = binding.navView.menu.findItem(itemId)
                menuItem?.isVisible = false
            }
        }
    }
    private fun setUserInfo(){
        val headerView = binding.navView.getHeaderView(0)
        val nameText = headerView.findViewById<TextView>(R.id.name)
        val emailText = headerView.findViewById<TextView>(R.id.email)

        nameText.text = user?.name
        emailText.text = user?.email

    }

    private fun goToAddItem(){
        val currentDestination = navController!!.currentDestination

        when (currentDestination?.id) {
            R.id.nav_home -> {
                Messages().showError("Nav home", binding.root)
            }
            R.id.nav_slideshow -> {
                Messages().showError("Nav Slideshow", binding.root)
            }
            else -> {
                val intent = Intent(this@MainActivity, SaveTypeProductActivity::class.java)
                intent.putExtra("typeProduct", TypeProduct())
                startActivity(intent)
            }
        }
    }

    private fun goToFragmentAfterAction(){
        val fragment = intent?.getStringExtra("fragment")

        if(fragment.equals("TypeProductFragment")){
            navController!!.navigate(R.id.typeProductsScreen)
        }
    }


    private fun listenerOfSelectedItemNavView(){
        binding.navView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.exit ->{
                    JwtResponse.deleteFromMemory(applicationContext, applicationContext.resources.getString(R.string.keyForJwtResponse))

                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.nav_typeProducts ->{
                    navController!!.navigate(R.id.typeProductsScreen)
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_customers ->{
                    navController!!.navigate(R.id.nav_slideshow)
                    true
                }
                R.id.nav_bookings ->{
                    navController!!.navigate(R.id.nav_home)
                    true
                }
                else -> {
                    false
                }
            }

        }
    }

}