package com.example.tableofannouncements

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tableofannouncements.accounthelper.GoogleSignInClient
import com.example.tableofannouncements.databinding.ActivityMainBinding
import com.example.tableofannouncements.dialoghelper.DialogConst
import com.example.tableofannouncements.dialoghelper.DialogHelper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvAccountEmail: TextView
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

    override fun onStart() {
        super.onStart()
        uiUpdate(myAuth.currentUser)
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
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
        toggle.syncState()

        binding.navV.setNavigationItemSelectedListener(this)

        tvAccountEmail = binding.navV.getHeaderView(0).findViewById(R.id.tvAccountEmail)
    }

    fun uiUpdate(user: FirebaseUser?){
        tvAccountEmail.text = if (user == null){
            resources.getString(R.string.not_reg)
        }else{
            user.email
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ans -> {googleSignIn()}
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
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun googleSignIn(){
        val googleAuthClient = GoogleSignInClient(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            googleAuthClient.signIn()
        }

    }
}