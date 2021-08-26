package com.cagataykolus.contactsapp.di.module

import com.cagataykolus.contactsapp.data.repository.ContactRepository
import com.cagataykolus.contactsapp.data.repository.DefaultContactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
@ExperimentalCoroutinesApi
@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ContactRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindContactRepository(repository: DefaultContactRepository): ContactRepository
}