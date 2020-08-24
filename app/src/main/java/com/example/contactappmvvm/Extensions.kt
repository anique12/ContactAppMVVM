package com.example.contactappmvvm

import android.app.Activity
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.contactappmvvm.model.Contact
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.pickImageFromGallery(REQ_CODE:Int) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    startActivityForResult(intent, REQ_CODE)
}

@Throws(IOException::class)
fun Fragment.convertUriToByteArray(uri:Uri):ByteArray?{
    return context?.contentResolver?.openInputStream(uri)?.readBytes()
}

fun Fragment.convertByteArrayToBitmap(byteArray: ByteArray):Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Fragment.convertUriToBitmap(uri: Uri):Bitmap{
    return MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
}

fun Fragment.convertBitmapToCompressedByteArray(bitmap: Bitmap):ByteArray{
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG,0,baos)
    return baos.toByteArray()
}

fun Fragment.saveImageToInternalStorageAsBitmap(uri: Uri,quality:Int):Uri{
    val bitmap = convertUriToBitmap(uri)
    val wrapper = ContextWrapper(context?.applicationContext)
    var file = wrapper.getDir("images",Context.MODE_PRIVATE)
    file = File(file,"${UUID.randomUUID()}.jpg")
    try {
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG,quality,stream)
        stream.flush()
        stream.close()
    }catch (e:IOException){
        e.printStackTrace()
    }
    return Uri.parse(file.absolutePath)
}

fun Fragment.deleteConfirmationDialog():Boolean{
    var i = false
    val builder = AlertDialog.Builder(context!!)
    builder.setTitle("Confirmation!")
    builder.setMessage("Are you sure want to delete?")
    builder.setPositiveButton("Yes"){ _: DialogInterface, _: Int ->
        i = true
    }
    builder.setNegativeButton("No"){ dialog: DialogInterface, _: Int ->
        dialog.dismiss()
    }
    val mDialog = builder.create()
    mDialog.setCancelable(false)
    mDialog.show()
    return i
}

fun Fragment.showToast(text:String){
    Toast.makeText(context!!,text,Toast.LENGTH_SHORT).show()
}

fun  Fragment.storeObjectInSharedPreference(key:String ,mObject: Any){
    val gson = Gson()
    val json = gson.toJson(mObject)
    val pref = context?.getSharedPreferences("contact",Context.MODE_PRIVATE)?.edit()
    pref?.putString(key,json)
    pref?.apply()
}

fun <T> Fragment.getObjectFromSharedPreference(key: String,mObject:Class<T>):T{
    val gson = Gson()
    val pref = context?.getSharedPreferences("contact",Context.MODE_PRIVATE)
    val json = pref?.getString(key,null)
    val finalObject = gson.fromJson(json,mObject)
    return finalObject
}