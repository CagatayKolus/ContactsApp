package com.cagataykolus.contactsapp.di.module

import com.cagataykolus.contactsapp.data.remote.api.ContactApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
@InstallIn(SingletonComponent::class)
@Module
class ContactApiServiceModule {
    @Singleton
    @Provides
    fun provideRetrofitService(): ContactApiService = Retrofit.Builder()
        .baseUrl(ContactApiService.CONTACT_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(ContactApiService::class.java)
}
