package com.cagataykolus.contactsapp.ui.contacts.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cagataykolus.contactsapp.databinding.ItemContactBinding
import com.cagataykolus.contactsapp.model.Contact

/**
 * Created by Çağatay Kölüş on 25.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * [RecyclerView.ViewHolder] implementation to inflate View for RecyclerView.
 */
class ContactsViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact, onItemClicked: (Contact) -> Unit) {
        binding.textviewItemContactNameSurname.text = contact.name + " " + contact.surname
        binding.textviewItemContactCompany.text = contact.company_name

        binding.root.setOnClickListener {
            onItemClicked(contact)
        }
    }
}
