package com.example.contactappmvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactappmvvm.local.ContactDao
import com.example.contactappmvvm.model.Contact

class ContactRepository (private val contactDao: ContactDao){

    val allContacts = contactDao.getAllContacts()

    suspend fun insert(contact: Contact){
        contactDao.insertContact(contact)
    }

    suspend fun delete(contact: Contact){
        contactDao.deleteContact(contact)
    }

    suspend fun update(contact: Contact){
        contactDao.updateContact(contact)
    }

    suspend fun deleteAll(){
        contactDao.deleteAllContacts()
    }
}
