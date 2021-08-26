package com.cagataykolus.contactsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cagataykolus.contactsapp.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * Data Access Object (DAO) for [com.cagataykolus.contactsapp.data.local.ContactDatabase]
 */
@Dao
interface ContactDao {
    /**
     * Inserts [contacts] into the [Contact.TABLE_CONTACTS] table.
     * Duplicate values are replaced in the table.
     * @param contacts
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContacts(contacts: List<Contact>)

    /**
     * Inserts [contact] into the [Contact.TABLE_CONTACTS] table.
     * Duplicate values are replaced in the table.
     * @param contact
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: Contact)

    /**
     * Deletes where [Contact.id] = [id] from the [[Contact.TABLE_CONTACTS] table.
     */
    @Query("DELETE FROM ${Contact.TABLE_CONTACTS} WHERE id = :id")
    fun deleteContactById(id: String)

    /**
     * Updates where [Contact.id] = [id] from the [Contact.TABLE_CONTACTS] table.
     * @param name
     * @param surname
     * @param company_name
     * @param createdAt
     * @param department
     * @param email
     * @param number
     */
    @Query("UPDATE ${Contact.TABLE_CONTACTS} set name = :name, surname = :surname, company_name = :company_name, createdAt = :createdAt, department = :department, email = :email, number = :number WHERE id = :id")
    fun editContact(
        id: String,
        company_name: String,
        createdAt: String,
        department: String,
        email: String,
        name: String,
        number: Int,
        surname: String
    )

    /**
     * Fetches the data from the [Contact.TABLE_CONTACTS] table whose value is [contactId].
     * @param id is unique ID of [Contact]
     * @return [Flow] of [Contact] from database table.
     */
    @Query("SELECT * FROM ${Contact.TABLE_CONTACTS} WHERE id = :id")
    fun getContactById(id: String): Flow<Contact>

    /**
     * Fetches all the data from the [Contact.TABLE_CONTACTS] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${Contact.TABLE_CONTACTS}")
    fun getAllContacts(): Flow<List<Contact>>
}