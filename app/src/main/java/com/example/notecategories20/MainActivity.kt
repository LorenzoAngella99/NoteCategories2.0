package com.example.notecategories20

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notecategories20.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater) //binding
        setContentView(binding.root)



        binding.apply{
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

            //--- mettere i listener dei item menu qui---
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