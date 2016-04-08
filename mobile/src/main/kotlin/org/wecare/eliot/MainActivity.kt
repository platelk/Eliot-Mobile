package org.wecare.eliot

import android.content.Intent
import android.os.Bundle
import org.jetbrains.anko.*
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import org.wecare.eliot.data.Repository
import org.wecare.eliot.data.UserProvider
import org.wecare.eliot.data.model.User

class MainActivity : AppCompatActivity(), AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolbar()
        setUpFabButton()
        //setUpNavigationDrawer()
        setUpSectionNavigation()
    }


    fun setUpToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
    }

    fun setUpFabButton() {
        val fab = findViewById(R.id.fab) as ImageView?
        fab?.setOnClickListener(View.OnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() })
    }

    fun setUpSectionNavigation() {
        val weightSection = findViewById(R.id.weightSection) as CardView?

        weightSection?.setOnClickListener({ view ->
            startActivity<WeightActivity>()
        })
    }

    fun setUpNavigationDrawer() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView?.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer?.isDrawerOpen(GravityCompat.START)!!) {
            drawer?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        drawer?.closeDrawer(GravityCompat.START)
        return true
    }
}
