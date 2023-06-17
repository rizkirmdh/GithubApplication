package com.example.githubapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubapplication.databinding.ActivitySettingBinding
import com.example.githubapplication.local.SettingPreferences

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel by viewModels<SettingViewModel>{
        SettingViewModel.SettingFactory(SettingPreferences(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel.getTheme().observe(this, {
            if (it){
                binding.swTheme.text = "Dark Mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                binding.swTheme.text = "Light Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.swTheme.isChecked = it
        })

        binding.swTheme.setOnCheckedChangeListener(){ _, isChecked ->
            settingViewModel.saveTheme(isChecked)
        }
    }
}