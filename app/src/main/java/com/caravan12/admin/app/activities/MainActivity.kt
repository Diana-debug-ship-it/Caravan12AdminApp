package com.caravan12.admin.app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.caravan12.admin.app.R
import com.caravan12.admin.app.databinding.ActivityMainBinding
import com.caravan12.admin.app.fragments.TourRequestsFragment
import com.caravan12.admin.app.fragments.UsersFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myToolBar: androidx.appcompat.widget.Toolbar = binding.toolbarMainMenu
        setSupportActionBar(myToolBar)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.users -> {
                    showFragment(UsersFragment.newInstance())
                }
                R.id.tour_requests -> {
                    showFragment(TourRequestsFragment.newInstance())
                }
            }
            return@setOnItemSelectedListener true
        }


    }

    override fun onStart() {
        super.onStart()
        showFragment(UsersFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings ->
            {
                auth.signOut()
                startActivity(Intent(this, LogInActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, fragment)
            .commit()
    }
}