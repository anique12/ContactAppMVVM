package com.example.contactappmvvm.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactappmvvm.model.Contact

@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact:Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("Select * from Contact")
    fun getAllContacts():LiveData<List<Contact>>

    @Query("Delete from Contact")
    suspend fun deleteAllContacts()


}