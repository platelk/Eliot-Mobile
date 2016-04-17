package org.wecare.eliot.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import com.jakewharton.rxbinding.view.clicks
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.wecare.eliot.MainActivity
import org.wecare.eliot.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpToolbar()

        find<Button>(R.id.login).clicks().subscribe {
            startActivity<MainActivity>()
        }
    }

    fun setUpToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        toolbar?.navigationIconResource = R.drawable.ic_action_name
    }
}