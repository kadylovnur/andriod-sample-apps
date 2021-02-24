package com.shimnssso.android.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shimnssso.android.projemanag.databinding.ActivityCardDetailsBinding

class CardDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}