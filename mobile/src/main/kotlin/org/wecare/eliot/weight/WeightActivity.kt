package org.wecare.eliot.weight

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
import android.view.View
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
import java.util.*

class WeightActivity : AppCompatActivity(), AnkoLogger {

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


    @Bind(R.id.editWeight)
    lateinit var weightEditText: EditText
    @Bind(R.id.helpText)
    lateinit var helpText: TextView
    @Bind(R.id.sendAction)
    lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        ButterKnife.bind(this)

        /**
         * Set up action
         */
        setUpToolBar()
        setUpAction()
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

    fun setUpToolBar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        toolbar?.navigationIconResource = R.drawable.ic_action_name

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun setUpAction() {
        RxTextView.afterTextChangeEvents(weightEditText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.view().text.length > 1) {
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
        data.type_data = "poids"
        data.origine = "mobile Patient"
        data.user_id = 1
        data.doc_url = ""

        dataService?.dataService?.sendData(data)
                ?.subscribeOn(Schedulers.newThread())
                ?.subscribe {

                    val ldata = Ldata()

                    ldata.data_id = it.id
                    ldata.unit = "Kg"
                    ldata.value = weightEditText.text.toString()

                    dataService?.dataService?.sendData(ldata)
                            ?.subscribeOn(Schedulers.newThread())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.doOnError {
                                val error = Snackbar.make(find<CoordinatorLayout>(R.id.mainLayout), "Erreur serveur", Snackbar.LENGTH_SHORT)
                                error.show()
                                weightEditText.setText("")
                            }
                            ?.subscribe {
                                val succes = Snackbar.make(find<CoordinatorLayout>(R.id.mainLayout), "Donnée envoyé", Snackbar.LENGTH_SHORT)
                                succes.show()
                                onBackPressed()
                            }
                }
    }
}
