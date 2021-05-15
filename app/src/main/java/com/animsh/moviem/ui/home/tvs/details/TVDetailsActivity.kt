package com.animsh.moviem.ui.home.tvs.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.animsh.moviem.databinding.ActivityTvDetailsBinding
import com.animsh.moviem.models.tv.TV

class TVDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val data: TV? = intent.getParcelableExtra<TV>("tv")
            tv = data
        }
    }
}