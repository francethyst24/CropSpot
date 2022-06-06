package com.example.cropspot

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.common.utils.LocaleUtils
import com.example.cropspot.domain.dto.AppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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