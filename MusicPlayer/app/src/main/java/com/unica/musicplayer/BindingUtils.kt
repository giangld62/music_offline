package com.unica.musicplayer

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingUtils {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(iv:ImageView,link:ByteArray){
        Glide.with(iv)
                .load(link)
                .into(iv)
    }
}