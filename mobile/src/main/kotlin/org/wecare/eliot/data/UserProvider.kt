package org.wecare.eliot.data

import org.wecare.eliot.data.model.User
import rx.Observable


/**
 * Created by kevin on 24/03/2016.
 */
class UserProvider() : Repository.DataProvider<User> {
    override fun update(data: User): Observable<User> {
        return Observable.just(data)
    }

    override fun delete(data: User): Observable<User> {
        return Observable.just(data)
    }

    override fun get(): Observable<User> {
        return Observable.just(User("Kevin", "PLATEL"))
    }

    override fun commit(data: User) : Observable<User> {
        return Observable.just(data)
    }

}