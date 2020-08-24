package com.example.contactappmvvm.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactappmvvm.model.Contact

@Database(entities = [Contact::class],version = 13)
abstract class ContactDatabase:RoomDatabase(){

    abstract fun contactDao() : ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ContactDatabase::class.java,
                    "contact_database"
                ). fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}