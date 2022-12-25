package com.example.food.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.food.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()


    }
    private fun initComponent(){

        // BottomNavigationView
        val btnNavigation = findViewById<BottomNavigationView>(R.id.btn_nav)
        val navNavigation = Navigation.findNavController(this, R.id.base_fragment)
        NavigationUI.setupWithNavController(btnNavigation,navNavigation)



    }
}