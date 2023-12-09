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
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.basics.Messages
import com.example.publishinghousekotlin.databinding.ActivityMainBinding
import com.example.publishinghousekotlin.http.responses.JwtResponse
import com.example.publishinghousekotlin.models.EmployeeDTO
import com.example.publishinghousekotlin.models.Material
import com.example.publishinghousekotlin.models.PrintingHouse
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
                R.id.nav_home, R.id.materialsScreen, R.id.typeProductsScreen, R.id.printingHousesScreen, R.id.employeesScreen, R.id.productsScreen
            ), drawerLayout
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController!!)

        hidingAddingBooking()
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
            R.id.materialsScreen -> {
                val intent = Intent(this@MainActivity, SaveMaterialActivity::class.java)
                intent.putExtra("material", Material())
                startActivity(intent)
            }
            R.id.printingHousesScreen ->{
                val intent = Intent(this@MainActivity, SavePrintingHouseActivity::class.java)
                intent.putExtra("printingHouse", PrintingHouse())
                startActivity(intent)
            }
            R.id.employeesScreen ->{
                val intent = Intent(this@MainActivity, SaveEmployeeActivity::class.java)
                intent.putExtra("employee", EmployeeDTO())
                startActivity(intent)
            }R.id.productsScreen ->{
                val intent = Intent(this@MainActivity, SaveProductActivity::class.java)
                startActivity(intent)
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
        }else if(fragment.equals("MaterialFragment")){
            navController!!.navigate(R.id.materialsScreen)
        } else if(fragment.equals("PrintingHouseFragment")){
            navController!!.navigate(R.id.printingHousesScreen)
        }else if(fragment.equals("EmployeeFragment")){
            navController!!.navigate(R.id.employeesScreen)
        }

    }

    private fun hidingAddingBooking(){
        binding.appBarMain.fab.isVisible = user?.role != UserRole.ADMINISTRATOR.name
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
                    binding.appBarMain.fab.isVisible = true
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_materials ->{
                    navController!!.navigate(R.id.materialsScreen)
                    binding.appBarMain.fab.isVisible = true
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_printingHouses -> {
                    navController!!.navigate(R.id.printingHousesScreen)
                    binding.appBarMain.fab.isVisible = true
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_employees ->{
                    navController!!.navigate(R.id.employeesScreen)
                    binding.appBarMain.fab.isVisible = true
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_products->{
                    navController!!.navigate(R.id.productsScreen)
                    binding.appBarMain.fab.isVisible = user?.role != UserRole.ADMINISTRATOR.name
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_bookings ->{
                    navController!!.navigate(R.id.nav_home)
                    hidingAddingBooking()
                    binding.drawerLayout.closeDrawers()
                    true
                }
                else -> {
                    false
                }
            }

        }
    }

}