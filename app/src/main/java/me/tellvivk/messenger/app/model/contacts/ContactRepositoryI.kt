package me.tellvivk.messenger.app.model.contacts

import io.reactivex.Single

interface ContactRepositoryI {

    fun getContacts(): Single<Contact>
    fun getContact(id: Int): Single<Contact>

}