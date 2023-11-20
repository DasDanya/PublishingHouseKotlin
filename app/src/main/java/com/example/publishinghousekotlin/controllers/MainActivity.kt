package com.example.publishinghousekotlin.controllers

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.databinding.ActivityMainBinding
import com.example.publishinghousekotlin.http.responses.JwtResponse
import com.example.publishinghousekotlin.models.User
import com.example.publishinghousekotlin.models.UserRole

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        user = JwtResponse.getFromMemory(applicationContext, applicationContext.resources.getString(R.string.keyForJwtResponse))!!.user
        hideItemsMenu()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setUserInfo()
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

    private fun listenerOfSelectedItemNavView(){
        binding.navView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.exit ->{
                    JwtResponse.deleteFromMemory(applicationContext, applicationContext.resources.getString(R.string.keyForJwtResponse))

                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)

                    false
                }

                else -> {
                    false
                }
            }

        }
    }

}