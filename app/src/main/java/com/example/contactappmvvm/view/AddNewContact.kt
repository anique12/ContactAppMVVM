package com.example.contactappmvvm.view

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contactappmvvm.R
import com.example.contactappmvvm.databinding.FragmentAddNewContactBinding
import com.example.contactappmvvm.model.Contact
import com.example.contactappmvvm.pickImageFromGallery
import com.example.contactappmvvm.saveImageToInternalStorageAsBitmap
import com.example.contactappmvvm.viewmodel.ContactViewModel
import com.example.contactappmvvm.viewmodel.ContactViewModelProvider


class AddNewContact : Fragment() {

    private lateinit var addNewContactBinding: FragmentAddNewContactBinding
    private val REQCODE = 101
    private var imageUri : Uri? = null
    private lateinit var contactViewModel : ContactViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNewContactBinding =  FragmentAddNewContactBinding.inflate(inflater, container, false)
        contactViewModel = ViewModelProvider(this, ContactViewModelProvider(requireActivity().application)).get(ContactViewModel::class.java)
        addNewContactBinding.imageView.setOnClickListener {
            pickImageFromGallery(REQCODE)
        }
        addNewContactBinding.done.setOnClickListener {
            val name  = addNewContactBinding.name.text.toString()
            val email = addNewContactBinding.email.text.toString()
            if(name.isEmpty()){
                addNewContactBinding.name.error = "Please select a name"
                return@setOnClickListener
            }
            if(email.isEmpty()){
                addNewContactBinding.name.error = "Please select an email"
                return@setOnClickListener
            }
            val contact = Contact(name,email,imageUri.toString())
            contactViewModel.insert(contact)
            findNavController().navigate(R.id.action_addNewContact_to_homeFragment)
        }
        return addNewContactBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQCODE || resultCode == Activity.RESULT_OK || data!=null){
            val mImageUri = data?.data!!
            addNewContactBinding.imageView.setImageURI(mImageUri)
            imageUri = saveImageToInternalStorageAsBitmap(mImageUri,0)
            Log.d("imageUri",mImageUri.toString())
        }
    }

}