package org.wecare.eliot.service

import org.wecare.eliot.data.model.Data
import org.wecare.eliot.data.model.Ldata
import org.wecare.eliot.data.model.Resource
import org.wecare.eliot.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

interface InformationService {
    @GET("user")
    fun getUser() : Observable<List<User>>

    @GET("user/{id}")
    fun getUserById(@Path("id") id : Long) : Observable<User>

    @GET("data")
    fun getData() : Observable<Data>

    @POST("data")
    fun sendData(@Body data : Data) : Observable<Data>

    @POST("ldata")
    fun sendData(@Body ldata : Ldata) : Observable<Ldata>
}