package com.shimnssso.android.sevenminutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shimnssso.android.sevenminutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩 클래스의 인스턴스를 생성합니다.
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 생성된 뷰를 액티비티에 표시합니다.
        setContentView(binding.root)

        // Click event for start Button which we have created in XML.
        binding.llStart.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding.llBMI.setOnClickListener {
            // Launching the BMI Activity
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        binding.llHistory.setOnClickListener {
            // Launching the History Activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}