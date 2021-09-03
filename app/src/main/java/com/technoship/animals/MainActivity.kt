package com.technoship.animals

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.technoship.animals.localizationSetter.LocalizationLanguageController

class MainActivity : LocalizationActivity() {

    private lateinit var openAppButton: Button
    private lateinit var changeLanguageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openAppButton = findViewById(R.id.openAppBtn)
        changeLanguageButton = findViewById(R.id.languageBtn)

        LocalizationLanguageController(this, changeLanguageButton,object :LocalizationLanguageController.OnLocalizationChanged{
            override fun onChanged(language: String?) {
                if (language != null) {
                    setLanguage(language)
                }
            }
        })

        openAppButton.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.technoship.animalssounds")))
            } catch (anfe: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.technoship.animalssounds")))
            }
        }
    }
}
