package org.wecare.eliot

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import com.jakewharton.rxbinding.view.clicks
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.wecare.eliot.pressure.PressureActivity
import org.wecare.eliot.service.DataService
import org.wecare.eliot.weight.WeightActivity


class MainActivity : AppCompatActivity(), AnkoLogger {

    var dataService : DataService? = null
    val connectionService : ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            dataService = (service as? DataService.DataServiceBinder)?.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            dataService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolbar()
        setUpNavigation()
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, DataService::class.java)

        bindService(intent, connectionService, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (dataService != null) {
            unbindService(connectionService)
        }
    }

    fun setUpToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        toolbar?.navigationIconResource = R.drawable.ic_action_name
    }

    fun setUpNavigation() {
        find<CardView>(R.id.weightSection).clicks().subscribe { startActivity<WeightActivity>() }
        find<CardView>(R.id.pressureSection).clicks().subscribe { startActivity<PressureActivity>() }
    }
}
