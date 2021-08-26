package com.cagataykolus.contactsapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cagataykolus.contactsapp.R
import com.cagataykolus.contactsapp.databinding.FragmentDetailBinding
import com.cagataykolus.contactsapp.model.Contact
import com.cagataykolus.contactsapp.model.State
import com.cagataykolus.contactsapp.ui.contacts.ContactsViewModel
import com.cagataykolus.contactsapp.ui.detail.dialog.EditContactDialog
import com.cagataykolus.contactsapp.ui.detail.dialog.EditContactDialogAction
import com.cagataykolus.contactsapp.utils.isConnectedToInternet
import com.cagataykolus.contactsapp.utils.showToast
import com.cagataykolus.contactsapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Çağatay Kölüş on 25.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * [DetailFragment] is fragment for [Contact] data.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding { FragmentDetailBinding.bind(it) }
    private val viewModel by viewModels<DetailViewModel>()
    private val viewModelContacts by viewModels<ContactsViewModel>()

    private lateinit var currentContact: Contact

    override fun onStart() {
        super.onStart()

        observeDeleteContact()
    }

    companion object {
        const val CONTACT_DETAILS = "CONTACT_DETAILS"
        const val CONTACT_DELETED = "CONTACT_DELETED"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = arguments?.getParcelable<Contact>(CONTACT_DETAILS)
        content?.let {
            currentContact = it
            initView(it)
        }

        binding.buttonDetailEdit.setOnClickListener {
            showEditContactDialog(currentContact)
        }

        binding.buttonDetailDelete.setOnClickListener {
            showDeleteContactDialog()
        }
    }

    private fun initView(contact: Contact) {
        binding.run {
            // Fill details
            textviewDetailNameSurname.text =
                String.format(getString(R.string.name_surname, contact.name, contact.surname))
            textviewDetailCompany.text = contact.company_name
            textviewDetailNumber.text = contact.number.toString()
            textviewDetailDepartment.text = contact.department
            textviewDetailEmail.text = contact.email
        }
    }

    private fun deleteContact(id: String) = viewModelContacts.deleteContact(id)

    private fun editContact(contact: Contact) = viewModelContacts.editContact(contact)

    private fun showContactLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbarDetailLoading.visibility = View.VISIBLE
        } else {
            binding.progressbarDetailLoading.visibility = View.GONE
        }
    }

    private fun showDeleteContactDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.delete_contact_title))
        alertDialog.setMessage(getString(R.string.delete_contact_description))
        alertDialog.setPositiveButton(
            getString(R.string.yes)
        ) { _, _ ->
            if (isConnectedToInternet()) {
                deleteContact(currentContact.id)
            } else {
                showToast(getString(R.string.internet_connectivity_fail))
            }
        }
        alertDialog.setNegativeButton(
            getString(R.string.cancel)
        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()
    }

    private fun showEditContactDialog(currentContact: Contact) {
        val dialog = EditContactDialog(requireContext(), currentContact)
        dialog.onAction { action ->
            when (action) {
                EditContactDialogAction.EditContactClicked -> {
                    if (isConnectedToInternet()) {
                        editContact(contact = dialog.getContact())
                        dialog.dismissDialog()
                        initView(contact = dialog.getContact())
                    } else {
                        showToast(getString(R.string.internet_connectivity_fail))
                    }
                }
            }
        }.showDialog()
    }

    private fun observeDeleteContact() {
        viewModelContacts.deletedContactLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> showContactLoading(true)
                is State.Success -> {
                    findNavController().navigate(
                        R.id.action_detailFragment_to_contactsFragment,
                        bundleOf(CONTACT_DELETED to true)
                    )
                    showContactLoading(false)
                }
                is State.Error -> {
                    showToast(state.message)
                    showContactLoading(false)
                }
            }
        }
    }
}