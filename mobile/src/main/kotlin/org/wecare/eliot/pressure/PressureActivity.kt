package org.wecare.eliot.pressure

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import com.jakewharton.rxbinding.widget.RxTextView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.wecare.eliot.R
import org.wecare.eliot.data.model.Data
import org.wecare.eliot.data.model.Ldata
import org.wecare.eliot.service.DataService
import org.wecare.eliot.utils.FadeScaleHide
import org.wecare.eliot.utils.FadeScaleShow
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PressureActivity : AppCompatActivity(), AnkoLogger {

    @Bind(R.id.editPressureDia)
    lateinit var diaEditText: EditText
    @Bind(R.id.editPressureSys)
    lateinit var sysEditText: EditText


    var dataService: DataService? = null
    val connectionService: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            debug("Service connected")
            dataService = (service as? DataService.DataServiceBinder)?.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            dataService = null
        }
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pressure)
        ButterKnife.bind(this)

        /**
         * Set up
         */
        setUpToolBar()
        setUpAction()
    }


    override fun onStart() {
        super.onStart()
        val intent = Intent(this, DataService::class.java)

        bindService(intent, connectionService, Context.BIND_AUTO_CREATE)
    }


    fun setUpToolBar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        toolbar?.navigationIconResource = R.drawable.ic_action_name

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        if (dataService != null) {
            unbindService(connectionService)
        }
    }


    fun setUpAction() {
        val helpText = find<TextView>(R.id.helpText)
        val sendButton = find<Button>(R.id.sendAction)

        RxTextView.afterTextChangeEvents(diaEditText)
                .mergeWith(RxTextView.afterTextChangeEvents(sysEditText))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (diaEditText.text.length > 1 && sysEditText.text.length > 1) {
                        helpText.FadeScaleHide(startDelay = 0)
                        sendButton.FadeScaleShow(startDelay = 500)
                    } else {
                        helpText.FadeScaleShow(startDelay = 500)
                        sendButton.FadeScaleHide(startDelay = 0)
                    }
                }
        sendButton.onClick {
            sendDataAction()
        }
    }

    @OnClick(R.id.sendAction)
    fun sendDataAction() {
        val data = Data()

        data.date = System.currentTimeMillis()
        data.type_data = "tension"
        data.origine = "mobile Patient"
        data.user_id = 1
        data.doc_url = ""

        dataService?.dataService?.sendData(data)
                ?.subscribeOn(Schedulers.newThread())
                ?.subscribe {

                    val ldata = Ldata()

                    ldata.data_id = it.id
                    ldata.unit = "dia"
                    ldata.value = diaEditText.text.toString()

                    dataService?.dataService?.sendData(ldata)
                            ?.subscribeOn(Schedulers.newThread())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.doOnError {
                                val error = Snackbar.make(find<CoordinatorLayout>(R.id.mainLayout), "Erreur serveur", Snackbar.LENGTH_SHORT)
                                error.show()
                                diaEditText.setText("")
                                sysEditText.setText("")
                            }
                            ?.subscribe {
                                val ldata = Ldata()

                                ldata.data_id = it.id
                                ldata.unit = "sys"
                                ldata.value = sysEditText.text.toString()

                                dataService?.dataService?.sendData(ldata)
                                        ?.subscribeOn(Schedulers.newThread())
                                        ?.observeOn(AndroidSchedulers.mainThread())
                                        ?.doOnError {
                                            val error = Snackbar.make(find<CoordinatorLayout>(R.id.mainLayout), "Erreur serveur", Snackbar.LENGTH_SHORT)
                                            error.show()
                                            diaEditText.setText("")
                                            sysEditText.setText("")
                                        }
                                        ?.subscribe {
                                            val succes = Snackbar.make(find<CoordinatorLayout>(R.id.mainLayout), "Donnée envoyé", Snackbar.LENGTH_SHORT)
                                            succes.show()
                                            onBackPressed()
                                        }
                            }
                }
    }
}
