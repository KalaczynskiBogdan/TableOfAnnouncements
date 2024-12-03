package com.example.tableofannouncements.ui

import androidx.navigation.fragment.NavHostFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.ActivityMainBinding
import com.example.tableofannouncements.utils.DialogConst
import com.example.tableofannouncements.ui.dialogs.DialogHelper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvAccountEmail: TextView
    private lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val myAuth = FirebaseAuth.getInstance()
    private var showMenuItem = true
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            showMenuItem = destination.id == R.id.mainFragment
            invalidateOptionsMenu()
        }
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(myAuth.currentUser)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_item, menu)
        menu?.findItem(R.id.id_add_new_ad)?.isVisible = showMenuItem
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_add_new_ad) {
            navController.navigate(R.id.action_mainFragment_to_addNewAdsFragment)
            invalidateOptionsMenu()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.black)
        toggle.syncState()

        binding.navV.setNavigationItemSelectedListener(this)

        tvAccountEmail = binding.navV.getHeaderView(0).findViewById(R.id.tvAccountEmail)
    }

    fun uiUpdate(user: FirebaseUser?) {
        tvAccountEmail.text = if (user == null) {
            resources.getString(R.string.not_reg)
        } else {
            user.email
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ans -> {}

            R.id.id_car -> {}
            R.id.id_pc -> {}
            R.id.id_smartphone -> {}
            R.id.id_dm -> {}
            R.id.id_sign_up -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
            }

            R.id.id_sign_in -> {
                dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
            }

            R.id.id_out -> {
                uiUpdate(null)
                myAuth.signOut()
                CoroutineScope(Dispatchers.IO).launch { dialogHelper.accHelper.signOutFromGoogle() }
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}