package com.example.mydicodingevent.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mydicodingevent.R
import com.example.mydicodingevent.helper.ViewModelFactorySettings
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingsViewModel = ViewModelProvider(this,
            ViewModelFactorySettings(pref))[SettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked: Boolean ->
            settingsViewModel.saveThemeSetting(isChecked)
        }

        return view
    }
}