package com.cagataykolus.contactsapp.ui.contacts

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cagataykolus.contactsapp.R
import com.cagataykolus.contactsapp.databinding.FragmentContactsBinding
import com.cagataykolus.contactsapp.model.Contact
import com.cagataykolus.contactsapp.model.State
import com.cagataykolus.contactsapp.ui.contacts.adapter.ContactsAdapter
import com.cagataykolus.contactsapp.ui.contacts.dialog.AddContactDialog
import com.cagataykolus.contactsapp.ui.contacts.dialog.AddContactDialogAction
import com.cagataykolus.contactsapp.ui.detail.DetailFragment.Companion.CONTACT_DELETED
import com.cagataykolus.contactsapp.ui.detail.DetailFragment.Companion.CONTACT_DETAILS
import com.cagataykolus.contactsapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Çağatay Kölüş on 25.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * [ContactsFragment] is fragment for [Contact] data.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    private val binding by viewBinding { FragmentContactsBinding.bind(it) }
    private val viewModel by viewModels<ContactsViewModel>()
    private val adapter = ContactsAdapter(this::onItemClicked)

    override fun onResume() {
        super.onResume()
        getContacts()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()

        observeContacts()
        handleNetworkChanges()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeDeletedContact()
        observeContactFilter()
    }

    private fun initView() {
        binding.run {
            binding.recyclerviewContactsList.adapter = adapter
            binding.recyclerviewContactsList.setHasFixedSize(true)
            binding.swiperefreshlayoutContactsRefresh.setOnRefreshListener { getContacts() }
        }

        viewModel.contactsLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                getContacts()
            }
        }
    }

    private fun getContacts() = viewModel.getContacts()

    private fun onItemClicked(contact: Contact) {
        findNavController().navigate(
            R.id.action_contactsFragment_to_detailFragment,
            bundleOf(CONTACT_DETAILS to contact)
        )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_contact -> {
                showAddContactDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeContacts() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        adapter.setData(state.data.toMutableList())
                    }
                    showLoading(false)
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        }
    }

    private fun observeDeletedContact() {
        val content = arguments?.getBoolean(CONTACT_DELETED)
        content?.let { isDeleted ->
            if (isDeleted) {
                showToast(getString(R.string.contact_deleted))
                getContacts()
                arguments?.remove(CONTACT_DELETED)
            }
        }
    }

    private fun showAddContactDialog() {
        val dialog = AddContactDialog(requireContext())
        dialog.onAction { action ->
            when (action) {
                AddContactDialogAction.AddContactClicked -> {
                    if (isConnectedToInternet()) {
                        hideKeyboard()
                        addContact(dialog.getContact())
                        dialog.dismissDialog()
                        showToast(getString(R.string.contact_added))
                        getContacts()
                    } else {
                        showToast(getString(R.string.internet_connectivity_fail))
                    }
                }
            }
        }.showDialog()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.swiperefreshlayoutContactsRefresh.isRefreshing = isLoading
    }

    private fun addContact(contact: Contact) =
        viewModel.addContact(contact)

    private fun observeContactFilter() = binding.searchviewContactsSearch.setOnQueryTextListener(filterContacts())

    private fun filterContacts() = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            adapter.filter.filter(newText)
            return false
        }
    }

    /**
     * Observes network changes.
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(requireContext()).observe(this) { isConnected ->
            if (!isConnected) {
                binding.textviewContactsNetworkStatus.text =
                    getString(R.string.internet_connectivity_fail)
                binding.linearlayoutContactsNetworkStatus.apply {
                    show()
                    setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.connectivity_fail
                        )
                    )
                }
            } else {
                if (viewModel.contactsLiveData.value is State.Error || adapter.itemCount == 0) {
                    getContacts()
                }
                binding.textviewContactsNetworkStatus.text =
                    getString(R.string.internet_connectivity_success)
                binding.linearlayoutContactsNetworkStatus.apply {
                    setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.connectivity_success
                        )
                    )

                    animate()
                        .alpha(1f)
                        .setStartDelay(1000L)
                        .setDuration(1000L)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        }
    }
}