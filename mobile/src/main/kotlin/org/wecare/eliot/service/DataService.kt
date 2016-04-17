package org.wecare.eliot.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DataService : Service() {
    val binder = DataServiceBinder()
    val retrofit : Retrofit
    public val dataService : InformationService

    init {
        val interceptor = HttpLoggingInterceptor();
        interceptor.level = HttpLoggingInterceptor.Level.BODY;
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = Retrofit.Builder()
                .baseUrl("http://172.17.122.114:5000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        dataService = retrofit.create(InformationService::class.java)
    }

    override fun onBind(intent : Intent) : IBinder = binder

    inner class DataServiceBinder : Binder() {
        fun getService(): DataService = this@DataService
    }
}