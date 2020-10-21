package com.konadev.barbazz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // NavController se encarga de toda la navegación, apunta al fragment container del main activity
        navController = findNavController(R.id.nav_host_fragment)
        // navegación en el actionBar
        NavigationUI.setupActionBarWithNavController(this,navController)
    }
    // flecha para retornar al fragment principal
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}