package com.example.contactappmvvm.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id:Long,
    @ColumnInfo val name:String,
    @ColumnInfo val email:String,
    @ColumnInfo val imageUrl:String):Parcelable{

    constructor(name: String,email: String,imageUrl: String):this(0,name,email, imageUrl)


}