package com.example.contactappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.contactappmvvm.local.ContactDatabase
import com.example.contactappmvvm.model.Contact
import com.example.contactappmvvm.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel (application: Application):AndroidViewModel(application){

    private var repository : ContactRepository
    var allContacts : LiveData<List<Contact>>

    init {
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        repository = ContactRepository(contactDao)
        allContacts = repository.allContacts
    }

    fun insert(contact: Contact)= viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }

    suspend fun delete(contact: Contact) = viewModelScope.launch ( Dispatchers.IO){
        repository.delete(contact)
    }

    suspend fun deleteAll() = viewModelScope.launch (Dispatchers.IO){
        repository.deleteAll()
    }

    suspend fun update(contact: Contact) = viewModelScope.launch (Dispatchers.IO){
        repository.update(contact)
    }


}