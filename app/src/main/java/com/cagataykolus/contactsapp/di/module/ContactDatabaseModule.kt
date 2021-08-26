package com.cagataykolus.contactsapp.di.module

import android.app.Application
import com.cagataykolus.contactsapp.data.local.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
@InstallIn(SingletonComponent::class)
@Module
class ContactDatabaseModule {
    @Singleton
    @Provides
    fun provideContactDatabase(application: Application) = ContactDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideContactDao(database: ContactDatabase) = database.getContactDao()
}
