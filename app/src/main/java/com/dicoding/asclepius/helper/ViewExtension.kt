package com.dicoding.asclepius.helper

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar

fun MaterialToolbar.setBackNav() {
    this.setNavigationOnClickListener {
        (context as Activity).finish()
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .error(
            Glide.with(this.context)
                .load(url))
        .centerCrop()
        .into(this)
}