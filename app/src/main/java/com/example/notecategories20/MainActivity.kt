package com.example.notecategories20

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.notecategories20.Note.CategoryNoteFragment
import com.example.notecategories20.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var navController : NavController

    private lateinit var appBarConfiguration: AppBarConfiguration
    var transatcion :FragmentTransaction = supportFragmentManager.beginTransaction()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) //binding
        setContentView(binding.root)

        binding.apply {
            navMenu.bringToFront()
            setSupportActionBar(toolbar)

            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                mainLayout,
                R.string.nav_open,
                R.string.nav_close
            )
            mainLayout.addDrawerListener(toggle)

            //--- mettere i listener dei item menu qui---
            navMenu.setNavigationItemSelectedListener {
               
                when (it.itemId) {
                    R.id.category -> {

                    }
                    R.id.item_linkedin -> {
                        val uri = Uri.parse("https://www.linkedin.com/in/lorenzo-angella-6281791a2/")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
                true
            //--- mettere i listener dei item menu qui---
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //funzione per attivare il toggle
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}