package com.poc.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.poc.utility.AppConstants
import com.poc.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, bundle ->
            toolbar.title = bundle?.getString(AppConstants.KEY_PAGE_TITLE) ?: destination.label
            when (destination.id) {
                R.id.imageDetailFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                else -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navHostFragment.navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
