package org.wecare.eliot.data

import rx.Observable
import java.util.HashMap
import kotlin.reflect.KClass

/**
 * Created by kevin on 24/03/2016.
 */
open class Repository {
    interface DataProvider<T : Any> {
        fun commit(data : T) : Observable<T>
        fun get() : Observable<T>
        fun update(data : T) : Observable<T>
        fun delete(data : T) : Observable<T>
    }

    var dataProviders = HashMap<String, DataProvider<*>>()

    open fun <T : Any> registerProvider(type : KClass<T>, dataProvider: DataProvider<T>) {
        dataProviders[type.simpleName!!] = dataProvider;
    }

    open fun <T : Any> commit(data : T) : Observable<T> {
        return (dataProviders[data.javaClass.simpleName] as? DataProvider<T>)?.commit(data) as Observable<T>
    }

    open fun <T : Any> get(type : KClass<T>) : Observable<T> {
        return dataProviders[type.simpleName]!!.get() as Observable<T>
    }

    open fun <T : Any> update(data : T) : Observable<T> {
        return (dataProviders[data.javaClass.simpleName] as? DataProvider<T>)?.update(data) as Observable<T>
    }

    open fun <T : Any> delete(data : T) : Observable<T> {
        return (dataProviders[data.javaClass.simpleName] as? DataProvider<T>)?.delete(data) as Observable<T>
    }
}
