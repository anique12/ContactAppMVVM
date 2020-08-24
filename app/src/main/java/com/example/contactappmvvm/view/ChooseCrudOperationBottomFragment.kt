package com.example.contactappmvvm.view

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.contactappmvvm.R
import com.example.contactappmvvm.databinding.FragmentChooseCrudOperationBottomBinding
import com.example.contactappmvvm.getObjectFromSharedPreference
import com.example.contactappmvvm.model.Contact
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseCrudOperationBottomFragment : BottomSheetDialogFragment() {

    private lateinit var chooseCrudOperationBottomBinding: FragmentChooseCrudOperationBottomBinding
    private lateinit var listener : CrudListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = targetFragment as HomeFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        chooseCrudOperationBottomBinding =  FragmentChooseCrudOperationBottomBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        chooseCrudOperationBottomBinding.update.setOnClickListener {
            dismiss()
            val bundle = Bundle()
            bundle.putParcelable("contact",getObjectFromSharedPreference("contact",Contact::class.java))
            findNavController().navigate(R.id.action_homeFragment_to_updateContact,bundle)
        }
        chooseCrudOperationBottomBinding.delete.setOnClickListener {
            dismiss()
            showDeleteConfirmation()
        }
        return chooseCrudOperationBottomBinding.root
    }

    private fun showDeleteConfirmation() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation!")
        builder.setMessage("Are you sure want to delete?")
        builder.setPositiveButton("Yes"){ _: DialogInterface, _: Int ->
            listener.delete()
        }
        builder.setNegativeButton("No"){ dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        val mDialog = builder.create()
        mDialog.setCancelable(false)
        mDialog.show()
    }

}