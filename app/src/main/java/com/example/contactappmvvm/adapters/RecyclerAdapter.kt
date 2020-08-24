package com.example.contactappmvvm.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.contactappmvvm.databinding.ItemContactBinding
import com.example.contactappmvvm.model.Contact
import com.example.contactappmvvm.view.OnMoreClickListener
import kotlinx.android.synthetic.main.item_contact.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private var listener: OnMoreClickListener)
    :RecyclerView.Adapter<RecyclerViewHolder>(),Filterable {

    private  var contactList : ArrayList<Contact>? = null
    private  var filteredList : ArrayList<Contact>? = null

    fun setContact(nContactList: ArrayList<Contact>){
        contactList = nContactList
        filteredList = nContactList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount():Int = filteredList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int)  {
        val contact = filteredList!![position]
        contact.let { holder.bind(it) }
        holder.itemView.more.setOnClickListener {
            contact.let { it1 -> listener.onMoreClickListener(it1) }
        }
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val search = p0.toString()
                filteredList = if (search.isEmpty()) {
                    contactList
                }else {
                    val resultList = ArrayList<Contact>()
                    contactList?.forEach {
                        if (it.name.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))) {
                            resultList.add(it)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = filteredList
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                p1?.values?.let {
                    filteredList = it as ArrayList<Contact>
                    notifyDataSetChanged()
                }
            }
        }
    }


}

class RecyclerViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(contact: Contact){
        binding.contact = contact
        binding.executePendingBindings()
    }
}

