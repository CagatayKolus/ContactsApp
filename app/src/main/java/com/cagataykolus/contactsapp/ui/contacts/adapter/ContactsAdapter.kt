package com.cagataykolus.contactsapp.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cagataykolus.contactsapp.databinding.ItemContactBinding
import com.cagataykolus.contactsapp.model.Contact
import com.cagataykolus.contactsapp.ui.contacts.viewholder.ContactsViewHolder
import java.util.*

/**
 * Created by Çağatay Kölüş on 25.08.2021.
 * cagataykolus@gmail.com
 */

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds [Contact] along with [ContactsViewHolder]
 * @param onItemClicked which will receive callback when item is clicked.
 */
class ContactsAdapter(
    private val onItemClicked: (Contact) -> Unit
) : ListAdapter<Contact, ContactsViewHolder>(DIFF_CALLBACK), Filterable {

    private var list = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactsViewHolder(
        ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    fun setData(list: MutableList<Contact>) {
        this.list = list
        submitList(list)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem == newItem
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val filteredList = mutableListOf<Contact>()
                val charSearch = constraint.toString()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(list)
                } else {
                    for (item in list) {
                        if (item.name.toLowerCase().contains(charSearch.toLowerCase(Locale.ROOT)) ||
                            item.surname.toLowerCase()
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as MutableList<Contact>)
            }
        }
    }
}
