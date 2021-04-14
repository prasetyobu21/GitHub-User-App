package com.dicoding.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivitySettingBinding

class Setting : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = AlarmReceiver()

        binding.apply {
            btnTurnOnReminder.setOnClickListener {
                alarmReceiver.setRepeatingAlarm(
                    this@Setting,
                    AlarmReceiver.TYPE_REPEATING,
                    "09:00",
                    "Get back and find your connection in GitHub"
                )
            }

            btnTurnOffReminder.setOnClickListener {
                alarmReceiver.cancelAlarm(this@Setting, AlarmReceiver.TYPE_REPEATING)
            }
        }
    }
}