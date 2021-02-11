package com.animsh.moviem.utilities;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
}
