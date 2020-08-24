package com.example.contactappmvvm.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.contactappmvvm.R
import com.example.contactappmvvm.databinding.FragmentUpdateContactBinding
import com.example.contactappmvvm.model.Contact
import com.example.contactappmvvm.pickImageFromGallery
import com.example.contactappmvvm.saveImageToInternalStorageAsBitmap
import com.example.contactappmvvm.viewmodel.ContactViewModel
import com.example.contactappmvvm.viewmodel.ContactViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateContact : Fragment() {

    private lateinit var updateContactBinding: FragmentUpdateContactBinding
    private var imageUri : Uri? = null
    private lateinit var contactViewModel : ContactViewModel
    private lateinit var contact: Contact
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        updateContactBinding =  FragmentUpdateContactBinding.inflate(inflater, container, false)
        contactViewModel = ViewModelProvider(this, ContactViewModelProvider(requireActivity().application)).get(ContactViewModel::class.java)
        arguments?.getParcelable<Contact>("contact")?.let {
            contact = it
            updateUI(it)
        }
        updateContactBinding.imageView.setOnClickListener {
            pickImageFromGallery(101)
        }
        updateContactBinding.update.setOnClickListener {
            val mContact : Contact
            val name = updateContactBinding.name.text.toString()
            val email = updateContactBinding.email.text.toString()
            if(name.isEmpty()){
                updateContactBinding.name.error = "Please select a name"
                return@setOnClickListener
            }
            if(email.isEmpty()){
                updateContactBinding.email.error = "Please select an email"
                return@setOnClickListener
            }
            if(imageUri != null) {
                val mImageUri = saveImageToInternalStorageAsBitmap(imageUri!!,0)
                 mContact = Contact(contact.id,name,email,mImageUri.toString())
            }else{
                 mContact = Contact(contact.id,name,email,contact.imageUrl)
            }
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                contactViewModel.update(mContact)
            }
            findNavController().navigate(R.id.action_updateContact_to_homeFragment)
        }
        return updateContactBinding.root
    }

    private fun updateUI(contact: Contact) {
        updateContactBinding.contact = contact
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 || resultCode == Activity.RESULT_OK || data!=null){
            val mImageUri = data?.data!!
            imageUri = mImageUri
            updateContactBinding.imageView.setImageURI(mImageUri)
            Log.d("imageUri",mImageUri.toString())
        }
    }
}