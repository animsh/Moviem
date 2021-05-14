package com.animsh.moviem.bindingadapters

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.animsh.moviem.R
import com.animsh.moviem.models.movie.Genre
import com.animsh.moviem.models.movie.ProductionCompany
import com.animsh.moviem.models.movie.ProductionCountry
import com.animsh.moviem.models.movie.SpokenLanguage
import com.animsh.moviem.util.BlurImage.fastblur
import com.animsh.moviem.util.Constants.Companion.IMAGE_W500
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
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

        @BindingAdapter("android:loadBlurImageFromURL")
        @JvmStatic
        fun loadBlurImageFromURL(imageView: ImageView, url: String?) {
            val target: com.squareup.picasso.Target = object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                    imageView.setImageBitmap(fastblur(bitmap!!, 1f, 100))
                    Log.d("logData", "onBitmapLoaded: inside picasso")
                }

                override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                    Log.d("logData", "onBitmapLoaded: inside picasso f" + e?.message)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            }

            imageView.tag = target
            Picasso.get()
                .load(IMAGE_W500 + url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(target)
        }

        @BindingAdapter("android:loadRuntime")
        @JvmStatic
        fun loadRuntime(textView: TextView, runtime: Int) {
            val hours = runtime / 60 //since both are ints, you get an int
            val minutes = abs(runtime) % 60
            when (textView.id) {
                R.id.bRuntime -> {
                    textView.text = MessageFormat.format("{0}h {1}m", hours, minutes)
                }
                R.id.tRuntime -> {
                    textView.text = MessageFormat.format("{0}h {1}m/ep", hours, minutes)
                }
                else -> {
                    textView.text = MessageFormat.format("● {0}h {1}m", hours, minutes)
                }
            }
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

        @BindingAdapter("android:loadMovieGenres")
        @JvmStatic
        fun loadMovieGenres(textView: TextView, genres: List<Genre>?) {
            var genresTxt = ""
            if (genres != null) {
                for (i in genres.indices) {
                    genresTxt = if (i == 0) {
                        genresTxt + genres[i].name
                    } else {
                        genresTxt + ", " + genres[i].name
                    }
                }
            } else {
                genresTxt = "?"
            }
            textView.text = genresTxt
        }


        @BindingAdapter("android:loadProductionCompanies")
        @JvmStatic
        fun loadProductionCompanies(textView: TextView, list: List<ProductionCompany>?) {
            var pcTxt = ""
            if (list != null) {
                for (i in list.indices) {
                    pcTxt = if (i == 0) {
                        pcTxt + list[i].name
                    } else {
                        pcTxt + ", " + list[i].name
                    }
                }
            } else {
                pcTxt = "?"
            }
            textView.text = pcTxt
        }

        @BindingAdapter("android:loadProductionCountries")
        @JvmStatic
        fun loadProductionCountries(textView: TextView, list: List<ProductionCountry>?) {
            var pcTxt = ""
            if (list != null) {
                for (i in list.indices) {
                    pcTxt = if (i == 0) {
                        pcTxt + list[i].name
                    } else {
                        pcTxt + ", " + list[i].name
                    }
                }
            } else {
                pcTxt = "?"
            }
            textView.text = pcTxt
        }

        @BindingAdapter("android:loadSpokenLanguages")
        @JvmStatic
        fun loadSpokenLanguages(textView: TextView, list: List<SpokenLanguage>?) {
            var pcTxt = ""
            if (list != null) {
                for (i in list.indices) {
                    pcTxt = if (i == 0) {
                        pcTxt + list[i].name
                    } else {
                        pcTxt + ", " + list[i].name
                    }
                }
            } else {
                pcTxt = "?"
            }
            textView.text = pcTxt
        }

        @BindingAdapter("android:loadGender")
        @JvmStatic
        fun loadGender(textView: TextView, gender: Int) {
            if (gender == 2) {
                textView.text = "Male"
            } else {
                textView.text = "Female"
            }
        }

    }
}