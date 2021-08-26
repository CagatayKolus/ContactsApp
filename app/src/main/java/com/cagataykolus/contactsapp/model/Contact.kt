package com.cagataykolus.contactsapp.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cagataykolus.contactsapp.model.Contact.Companion.TABLE_CONTACTS
import kotlinx.android.parcel.Parcelize

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
@Parcelize
@Entity(tableName = TABLE_CONTACTS)
data class Contact(
    @PrimaryKey
    @NonNull
    val id: String,
    val company_name: String,
    val createdAt: String,
    val department: String,
    val email: String,
    val name: String,
    val number: Int,
    val surname: String
) : Parcelable {
    companion object {
        const val TABLE_CONTACTS = "table_contacts"
    }
}