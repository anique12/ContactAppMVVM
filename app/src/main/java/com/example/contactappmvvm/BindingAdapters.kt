
package com.example.contactappmvvm

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File

@BindingAdapter("imageUrl")
fun setImage(view:CircleImageView,string: String?){
    string?.let {
        view.setImageURI(Uri.parse(it))
    }
}
