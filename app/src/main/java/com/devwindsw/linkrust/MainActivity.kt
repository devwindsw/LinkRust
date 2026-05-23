package com.devwindsw.linkrust

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.devwindsw.linkrust.databinding.ActivityMainBinding
import com.sun.jna.Library
import com.sun.jna.Native

class MainActivity : AppCompatActivity(), JNACallback {

    companion object {
        const val LIB_RUST = "firstrust"
    }
    interface RustLibrary : Library {
        fun invokeCallbackViaJNA(callback: JNACallback?): Int

        companion object {
            val INSTANCE: RustLibrary? =
                Native.load(LIB_RUST, RustLibrary::class.java) as RustLibrary?
        }
    }

    init {
        System.loadLibrary(LIB_RUST)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            mView = view
            invokeCallbackViaJNA(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun invoke(string: String?) {
        val prompt = string.toString()
        mView?.let {
            Snackbar.make(it, prompt /*"Replace with your own action"*/, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    private fun invokeCallbackViaJNA(callback: JNACallback?) {
        RustLibrary.INSTANCE?.invokeCallbackViaJNA(callback)
    }
}