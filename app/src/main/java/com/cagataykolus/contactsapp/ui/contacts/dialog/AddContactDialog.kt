package com.cagataykolus.contactsapp.ui.contacts.dialog

import android.content.Context
import android.view.LayoutInflater
import com.cagataykolus.contactsapp.R
import com.cagataykolus.contactsapp.databinding.DialogContactBinding
import com.cagataykolus.contactsapp.model.Contact
import com.cagataykolus.contactsapp.utils.getCurrentDate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Çağatay Kölüş on 26.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * [ContactDialog] is dialog for [Contact] data.
 */
class AddContactDialog(context: Context) : BottomSheetDialog(context) {
    private val dialog: BottomSheetDialog = BottomSheetDialog(context)
    private var action: ((AddContactDialogAction) -> Unit)? = null

    private lateinit var contact: Contact

    init {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DialogContactBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.buttonDialogContactOkay.setOnClickListener {
            if (isInputCorrect(binding)) {
                contact =
                    Contact(
                        id = "",
                        company_name = binding.textinputlayoutDialogContactCompany.editText?.text.toString(),
                        createdAt = getCurrentDate(),
                        department = binding.textinputlayoutDialogContactDepartment.editText?.text.toString(),
                        email = binding.textinputlayoutDialogContactEmail.editText?.text.toString(),
                        name = binding.textinputlayoutDialogContactName.editText?.text.toString(),
                        number = binding.textinputlayoutDialogContactNumber.editText?.text.toString()
                            .toInt(),
                        surname = binding.textinputlayoutDialogContactSurname.editText?.text.toString()
                    )
                action?.invoke(AddContactDialogAction.AddContactClicked)
            }
        }
    }

    private fun isInputCorrect(binding: DialogContactBinding): Boolean {
        return checkField(binding.textinputlayoutDialogContactNumber) &&
                checkField(binding.textinputlayoutDialogContactName) &&
                checkField(binding.textinputlayoutDialogContactSurname) &&
                checkField(binding.textinputlayoutDialogContactEmail) &&
                checkField(binding.textinputlayoutDialogContactCompany) &&
                checkField(binding.textinputlayoutDialogContactDepartment)
    }

    private fun checkField(textInputLayout: TextInputLayout): Boolean {
        return if (textInputLayout.editText?.text?.isEmpty()!!) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = context.getString(R.string.empty_field)
            false
        } else {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    fun onAction(action: (AddContactDialogAction) -> Unit) = apply {
        this.action = action
    }

    fun showDialog() = apply {
        dialog.show()
    }

    fun dismissDialog() = apply {
        dialog.dismiss()
    }

    fun getContact(): Contact {
        return contact
    }
}

sealed class AddContactDialogAction {
    object AddContactClicked : AddContactDialogAction()
}