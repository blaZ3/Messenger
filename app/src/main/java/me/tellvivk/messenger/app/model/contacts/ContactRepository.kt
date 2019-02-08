package me.tellvivk.messenger.app.model.contacts

import io.reactivex.Single

class ContactRepository: ContactRepositoryI {

    override fun getContacts(): Single<Contact> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContact(id: Int): Single<Contact> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}