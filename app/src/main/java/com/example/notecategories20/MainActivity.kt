package com.example.notecategories20

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.notecategories20.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var navController : NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) //binding
        setContentView(binding.root)


        binding.apply { // per aprire il menu tramite il pulstane
            navMenu.bringToFront()
            setSupportActionBar(toolbar)
            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )


            val drawerLayout: DrawerLayout = binding.drawerLayout
            val navView: NavigationView = binding.navMenu
            val navController = findNavController(R.id.fragment_container_view)

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.categories_home_fragment ->{
                        if(navController.currentDestination?.id != R.id.categories_home_fragment){
                            navController.navigate(R.id.categories_home_fragment)
                        }
                        drawerLayout.closeDrawers()
                    }
                    R.id.home ->{
                        if(navController.currentDestination?.id != R.id.homeFragment){
                            navController.navigate(R.id.homeFragment)
                        }
                        drawerLayout.closeDrawers()
                    }
                    R.id.item_linkedin->{
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/lorenzo-angella-6281791a2/"))
                        startActivity(browserIntent)
                    }
                    R.id.item_infoapp->{
                        if(navController.currentDestination?.id != R.id.infoApp){
                            navController.navigate(R.id.infoApp)
                        }
                        drawerLayout.closeDrawers()
                    }
                }
                true
            }
        }
    }

    private fun replicateFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commit()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //funzione per attivare il toggle
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container_view)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}