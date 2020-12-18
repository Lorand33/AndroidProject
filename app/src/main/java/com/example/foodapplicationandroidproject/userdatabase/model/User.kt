package com.example.foodapplicationandroidproject.userdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table", primaryKeys = ["email", "username"],indices = [ Index(value = ["email"],unique = true), Index(value = ["username"],unique = true)])
data class User(
        val firstName: String,
        val lastName: String,
        val email: String,
        val username: String,
        val telephone: String,
        val password: String
):Parcelable
