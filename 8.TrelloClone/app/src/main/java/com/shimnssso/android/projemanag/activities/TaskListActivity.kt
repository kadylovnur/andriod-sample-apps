package com.shimnssso.android.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shimnssso.android.projemanag.databinding.ActivityTaskListBinding

class TaskListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}