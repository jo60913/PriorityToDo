package com.huangliner.prioritytodo.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.huangliner.prioritytodo.R
import com.huangliner.prioritytodo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var appbarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setNavigation()
        setSupportActionBar(binding.tbMain)
    }

    private fun setNavigation() {
        binding.nvMain.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.drawerlayout_home ->{
                    this.navController.navigate(R.id.homeFragment)
                }

                R.id.drawerlayout_setting->{
                    this.navController.navigate(R.id.settingFragment)
                }

                R.id.drawerlayout_todo_list->{
                    this.navController.navigate(R.id.taskListFragment)
                }
            }
            binding.dlMain.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onStart() {
        super.onStart()
        navController = Navigation.findNavController(this, binding.navHostMain.id)
        NavigationUI.setupActionBarWithNavController(this, navController)
        appbarConfiguration = AppBarConfiguration.Builder(setOf(R.id.homeFragment))
            .setDrawerLayout(binding.dlMain)
            .build()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Timber.e("當前夜 ${destination.id} homefragment ${R.id.homeFragment}")
            if(destination.id == R.id.homeFragment) {
                Timber.e("home頁 修改toolbar icon")
                binding.tbMain.setNavigationIcon(R.drawable.ic_menu)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,appbarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}