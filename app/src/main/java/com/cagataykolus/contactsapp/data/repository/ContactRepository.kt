package com.cagataykolus.contactsapp.data.repository

import androidx.annotation.MainThread
import com.cagataykolus.contactsapp.data.local.dao.ContactDao
import com.cagataykolus.contactsapp.data.remote.api.ContactApiService
import com.cagataykolus.contactsapp.model.Contact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
interface ContactRepository {
    fun getAllContacts(): Flow<Resource<List<Contact>>>
    fun getContactById(id: String): Flow<Contact>
    fun deleteContact(id: String): Flow<Resource<Contact>>
    fun addContact(
        id: String,
        company_name: String,
        createdAt: String,
        department: String,
        email: String,
        name: String,
        number: Int,
        surname: String
    ): Flow<Resource<Contact>>

    fun editContact(
        id: String,
        company_name: String,
        createdAt: String,
        department: String,
        email: String,
        name: String,
        number: Int,
        surname: String
    ): Flow<Resource<Contact>>
}

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is single source of data.
 */
@ExperimentalCoroutinesApi
class DefaultContactRepository @Inject constructor(
    private val contactDao: ContactDao,
    private val service: ContactApiService
) : ContactRepository {
    /**
     * Fetched the data from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    override fun getAllContacts(): Flow<Resource<List<Contact>>> {
        return object : NetworkBoundRepository<List<Contact>, List<Contact>>() {

            override suspend fun saveRemoteData(response: List<Contact>) =
                contactDao.addContacts(response)

            override fun fetchFromLocal(): Flow<List<Contact>> = contactDao.getAllContacts()

            override suspend fun fetchFromRemote(): Response<List<Contact>> = service.getContacts()
        }.asFlow()
    }

    @MainThread
    override fun getContactById(id: String): Flow<Contact> =
        contactDao.getContactById(id).distinctUntilChanged()

    override fun deleteContact(id: String): Flow<Resource<Contact>> {
        return object : NetworkBoundRepository<Contact, Contact>() {
            override suspend fun saveRemoteData(response: Contact) {
                contactDao.deleteContactById(id)
            }

            override fun fetchFromLocal(): Flow<Contact> = contactDao.getContactById(id)

            override suspend fun fetchFromRemote(): Response<Contact> {
                contactDao.deleteContactById(id)
                return service.deleteContact(id)
            }

        }.asFlow()
    }

    override fun addContact(
        id: String,
        company_name: String,
        createdAt: String,
        department: String,
        email: String,
        name: String,
        number: Int,
        surname: String
    ): Flow<Resource<Contact>> {
        return object : NetworkBoundRepository<Contact, Contact>() {
            override suspend fun saveRemoteData(response: Contact) {
                contactDao.addContact(response)
            }

            override fun fetchFromLocal(): Flow<Contact> = contactDao.getContactById(id)

            override suspend fun fetchFromRemote(): Response<Contact> =
                service.addContact(
                    Contact(
                        id = id,
                        company_name = company_name,
                        createdAt = createdAt,
                        department = department,
                        email = email,
                        name = name,
                        number = number,
                        surname = surname
                    )
                )

        }.asFlow()
    }

    override fun editContact(
        id: String,
        company_name: String,
        createdAt: String,
        department: String,
        email: String,
        name: String,
        number: Int,
        surname: String
    ): Flow<Resource<Contact>> {
        return object : NetworkBoundRepository<Contact, Contact>() {
            override suspend fun saveRemoteData(response: Contact) {
                contactDao.editContact(
                    id = id,
                    company_name = company_name,
                    createdAt = createdAt,
                    department = department,
                    email = email,
                    name = name,
                    number = number,
                    surname = surname
                )
            }

            override fun fetchFromLocal(): Flow<Contact> = contactDao.getContactById(id)

            override suspend fun fetchFromRemote(): Response<Contact> {
                return service.editContact(
                    id = id,
                    Contact(
                        id = id,
                        company_name = company_name,
                        createdAt = createdAt,
                        department = department,
                        email = email,
                        name = name,
                        number = number,
                        surname = surname
                    )
                )
            }
        }.asFlow()
    }
}