package com.nihalmistry.newsapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// Binding adapter to show / hide views based on boolean value
@BindingAdapter("showIf")
fun bindShowIf(view: View, show: Boolean) {
    view.visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

// Binding adapter to load image from url into an ImageView using Glide
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?)  {
    url?.let {
        Glide.with(view.context).load(it).into(view)
    }
}
