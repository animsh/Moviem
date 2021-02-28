package com.animsh.moviem.bindingadapters

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.animsh.moviem.R
import com.animsh.moviem.util.Constants.Companion.IMAGE_W500
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.text.MessageFormat
import java.util.*
import kotlin.math.abs


/**
 * Created by animsh on 2/27/2021.
 */
class ItemBindingAdapter {
    companion object {
        @BindingAdapter("android:loadImageFromURL")
        @JvmStatic
        fun loadImageFromURL(imageView: ImageView, imageURL: String?) {
            try {
                imageView.alpha = 0f
                Glide.with(imageView.context).load(IMAGE_W500 + imageURL)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            @Nullable e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageView.animate().setDuration(300).alpha(1f).start()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageView.animate().setDuration(300).alpha(1f).start()
                            return false
                        }
                    }).into(imageView)
            } catch (ignored: Exception) {
            }

        }

        @BindingAdapter("android:loadRuntime")
        @JvmStatic
        fun loadRuntime(textView: TextView, runtime: Int) {
            val hours = runtime / 60 //since both are ints, you get an int
            val minutes = abs(runtime) % 60
            textView.text = MessageFormat.format("● {0}h {1}m", hours, minutes)
        }

        @BindingAdapter("android:loadLanguage")
        @JvmStatic
        fun loadLanguage(textView: TextView, language: String?) {
            textView.text = "● $language ".toUpperCase(Locale.getDefault())
        }


        @BindingAdapter("android:loadStatus")
        @JvmStatic
        fun loadStatus(textView: TextView, status: String?) {
            textView.text = "● $status "
        }

        @BindingAdapter("android:loadType")
        @JvmStatic
        fun loadType(textView: TextView, type: String?) {
            textView.text = "$type "
        }

        @BindingAdapter("android:loadSeasons")
        @JvmStatic
        fun loadSeasons(textView: TextView, seasons: Int) {
            textView.text = "● $seasons Seasons"
        }

        @BindingAdapter("android:setAdultStatus")
        @JvmStatic
        fun setAdultStatus(textView: TextView, isAdult: Boolean) {
            if (!isAdult) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

    }
}