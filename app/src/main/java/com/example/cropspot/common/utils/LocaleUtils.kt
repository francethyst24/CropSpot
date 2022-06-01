package com.example.cropspot.common.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.LocaleList
import java.util.*

class LocaleUtils constructor(
    baseContext: Context,
) : ContextWrapper(baseContext) {
    companion object {
        const val SELECTED_LANGUAGE = "locale.helper.selected.language"
    }

    init {
        val language = getPersistedData()
        setLocale(language)
    }

    fun getLanguage(): String = getPersistedData()

    private fun setLocale(language: String): ContextWrapper {
        persist(language)
        val localeToSwitchTo = Locale(language)
        val config = resources.configuration

        val localeList = LocaleList(localeToSwitchTo)
        LocaleList.setDefault(localeList)
        config.setLocales(localeList)

        val newContext = createConfigurationContext(config)
        return LocaleUtils(newContext)
    }

    private fun getPersistedData(): String {
        val sharedPref = getSharedPreferences(packageName, MODE_PRIVATE)
        return sharedPref.getString(SELECTED_LANGUAGE, null)
            ?: Locale.ENGLISH.language
    }

    private fun persist(language: String) {
        val sharedPref = getSharedPreferences(packageName, MODE_PRIVATE)
        sharedPref.edit().putString(SELECTED_LANGUAGE, language).apply()
    }
}