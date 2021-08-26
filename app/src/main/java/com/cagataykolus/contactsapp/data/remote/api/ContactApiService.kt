package com.cagataykolus.contactsapp.data.remote.api

import com.cagataykolus.contactsapp.model.Contact
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */

/*
 * Service to fetch data using endpoint [CONTACT_API_URL].
 */
interface ContactApiService {
    @GET("contacts")
    suspend fun getContacts(): Response<List<Contact>>

    @POST("contacts")
    suspend fun addContact(@Body contact: Contact): Response<Contact>

    @DELETE("contacts/{id}")
    suspend fun deleteContact(@Path("id") id: String): Response<Contact>


    @PUT("contacts/{id}")
    suspend fun editContact(@Path("id") id: String, @Body contact: Contact): Response<Contact>

    companion object {
        const val CONTACT_API_URL = "https://611dfa027d273a0017e2f997.mockapi.io/interview/v1/"
    }
}