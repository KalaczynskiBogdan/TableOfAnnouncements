package com.example.tableofannouncements

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tableofannouncements.databinding.ActivityMainBinding
import com.example.tableofannouncements.dialoghelper.DialogConst
import com.example.tableofannouncements.dialoghelper.DialogHelper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val myAuth = FirebaseAuth.getInstance()

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
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.mainContent.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navV.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ans -> {}
            R.id.id_car -> {}
            R.id.id_pc -> {}
            R.id.id_smartphone -> {}
            R.id.id_dm -> {}
            R.id.id_sign_up -> {dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)}
            R.id.id_sign_in -> {dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)}
            R.id.id_out -> {}
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}