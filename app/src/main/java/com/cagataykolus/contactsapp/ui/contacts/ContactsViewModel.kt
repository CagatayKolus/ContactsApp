package com.cagataykolus.contactsapp.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cagataykolus.contactsapp.data.repository.ContactRepository
import com.cagataykolus.contactsapp.model.Contact
import com.cagataykolus.contactsapp.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Çağatay Kölüş on 25.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * ViewModel for [ContactsViewModel].
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class ContactsViewModel @Inject constructor(private val contactRepository: ContactRepository) :
    ViewModel() {

    private val _contactsLiveData = MutableLiveData<State<List<Contact>>>()

    val contactsLiveData: LiveData<State<List<Contact>>> = _contactsLiveData

    private val _deletedContactLiveData = MutableLiveData<State<Contact>>()

    val deletedContactLiveData: LiveData<State<Contact>> = _deletedContactLiveData

    private val _editedContactLiveData = MutableLiveData<State<Contact>>()

    val editedContactLiveData: LiveData<State<Contact>> = _editedContactLiveData

    private val _addedContactLiveData = MutableLiveData<State<Contact>>()

    val addedContactLiveData: LiveData<State<Contact>> = _addedContactLiveData

    fun getContacts() {
        viewModelScope.launch {
            contactRepository.getAllContacts()
                .onStart { _contactsLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _contactsLiveData.value = state }
        }
    }

    fun deleteContact(contactId: String) {
        viewModelScope.launch {
            contactRepository.deleteContact(contactId)
                .onStart { _deletedContactLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _deletedContactLiveData.value = state }
        }
    }

    fun addContact(
        contact: Contact
    ) {
        viewModelScope.launch {
            contactRepository.addContact(
                contact.id,
                contact.company_name,
                contact.createdAt,
                contact.department,
                contact.email,
                contact.name,
                contact.number,
                contact.surname,
            )
                .onStart { _addedContactLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _addedContactLiveData.value = state }
        }
    }

    fun editContact(
        contact: Contact
    ) {
        viewModelScope.launch {
            contactRepository.editContact(
                contact.id,
                contact.company_name,
                contact.createdAt,
                contact.department,
                contact.email,
                contact.name,
                contact.number,
                contact.surname,
            )
                .onStart { _editedContactLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _editedContactLiveData.value = state }
        }
    }
}