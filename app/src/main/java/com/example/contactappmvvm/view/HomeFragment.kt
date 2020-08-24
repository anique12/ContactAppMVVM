package com.example.contactappmvvm.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.contactappmvvm.*
import com.example.contactappmvvm.adapters.RecyclerAdapter
import com.example.contactappmvvm.databinding.FragmentHomeBinding
import com.example.contactappmvvm.model.Contact
import com.example.contactappmvvm.viewmodel.ContactViewModel
import com.example.contactappmvvm.viewmodel.ContactViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class HomeFragment : Fragment(),OnMoreClickListener,CrudListener {

    private lateinit var listener : OnMoreClickListener
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var crudListener: CrudListener
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        listener = this
        crudListener = this
        adapter = RecyclerAdapter(listener)
        homeBinding.recyclerView.adapter = adapter
        contactViewModel = ViewModelProvider(this,ContactViewModelProvider(requireActivity().application)).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(viewLifecycleOwner, Observer {
            adapter.setContact(it as ArrayList<Contact>)
        })
        homeBinding.add.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNewContact)
        }
        homeBinding.searchBar.addTextChangedListener(textWatcher)
        return homeBinding.root
    }

    private val textWatcher = object : TextWatcher{
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            adapter.filter.filter(p0.toString())
        }

    }

    override fun onMoreClickListener(contact: Contact) {
        storeObjectInSharedPreference("contact",contact)
        val fragment = ChooseCrudOperationBottomFragment()
        fragment.setTargetFragment(this,1)
        val bundle = Bundle()
        bundle.putParcelable("contact",contact)
        fragment.show(requireFragmentManager(), "")
    }

    override fun delete() {
        val selectedContact = getObjectFromSharedPreference("contact",Contact::class.java)
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            contactViewModel.delete(selectedContact)
        }
    }

}

interface OnMoreClickListener{
     fun onMoreClickListener(contact: Contact)
}


interface CrudListener{
    fun delete()
}