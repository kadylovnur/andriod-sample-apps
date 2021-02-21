package com.shimnssso.android.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shimnssso.android.projemanag.R
import com.shimnssso.android.projemanag.databinding.ActivityCreateBoardBinding

class CreateBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCreateBoardActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarCreateBoardActivity.setNavigationOnClickListener { onBackPressed() }
    }
}