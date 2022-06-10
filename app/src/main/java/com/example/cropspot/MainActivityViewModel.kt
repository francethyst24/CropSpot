package com.example.cropspot

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.cropspot.common.utils.LocaleUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private fun setCachedLanguage(context: Context, value: String) {
        LocaleUtils.setLocale(context, value)
    }

    fun setLanguage(context: Context, newLanguage: String) {
        setCachedLanguage(context, newLanguage)
    }

}