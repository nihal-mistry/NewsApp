package com.nihalmistry.newsapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nihalmistry.newsapp.R

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
    Glide.with(view.context).load(url)
        .placeholder(R.drawable.news_placeholder)
        .fitCenter()
        .into(view)
}
