package com.animsh.moviem.utilities;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.animsh.moviem.R;
import com.animsh.moviem.model.movie.Genre;
import com.animsh.moviem.model.movie.ProductionCompany;
import com.animsh.moviem.model.movie.ProductionCountry;
import com.animsh.moviem.model.movie.SpokenLanguage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by animsh on 2/11/2021.
 */
public class BindingAdapters {

    @BindingAdapter("android:loadImageFromURL")
    public static void loadImageFromURL(ImageView imageView, String url) {
        try {
            imageView.setAlpha(0f);
            Picasso.get().load(url).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (Exception ignored) {

        }
    }

    @BindingAdapter("android:loadBlurImageFromURL")
    public static void loadBlurImageFromURL(ImageView imageView, String url) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(BlurImage.fastblur(bitmap, 1f, 100));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        imageView.setTag(target);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(target);
    }

    @BindingAdapter("android:loadMovieRuntime")
    public static void loadMovieRuntime(TextView textView, int runtime) {
        int hours = runtime / 60; //since both are ints, you get an int
        int minutes = Math.abs(runtime) % 60;
        textView.setText(MessageFormat.format("{0}h {1}m", hours, minutes));
    }

    @BindingAdapter("android:setAdultStatus")
    public static void setAdultStatus(TextView textView, boolean isAdult) {
        if (!isAdult) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @BindingAdapter("android:loadMovieGenres")
    public static void loadMovieGenres(TextView textView, List<Genre> genres) {
        String genresTxt = "";
        if (genres != null) {
            for (int i = 0; i < genres.size(); i++) {
                if (i == 0) {
                    genresTxt = genresTxt + genres.get(i).getName();
                } else {
                    genresTxt = genresTxt + ", " + genres.get(i).getName();
                }
            }
        } else {
            genresTxt = "?";
        }
        textView.setText(genresTxt.toString());
    }

    @BindingAdapter("android:loadProductionCompanies")
    public static void loadProductionCompanies(TextView textView, List<ProductionCompany> list) {
        String pcTxt = "";
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    pcTxt = pcTxt + list.get(i).getName();
                } else {
                    pcTxt = pcTxt + ", " + list.get(i).getName();
                }
            }
        } else {
            pcTxt = "?";
        }
        textView.setText(pcTxt.toString());
    }

    @BindingAdapter("android:loadProductionCountries")
    public static void loadProductionCountries(TextView textView, List<ProductionCountry> list) {
        String pcTxt = "";
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    pcTxt = pcTxt + list.get(i).getName();
                } else {
                    pcTxt = pcTxt + ", " + list.get(i).getName();
                }
            }
        } else {
            pcTxt = "?";
        }
        textView.setText(pcTxt.toString());
    }

    @BindingAdapter("android:loadSpokenLanguages")
    public static void loadSpokenLanguages(TextView textView, List<SpokenLanguage> list) {
        String pcTxt = "";
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    pcTxt = pcTxt + list.get(i).getName();
                } else {
                    pcTxt = pcTxt + ", " + list.get(i).getName();
                }
            }
        } else {
            pcTxt = "?";
        }
        textView.setText(pcTxt.toString());
    }
}
