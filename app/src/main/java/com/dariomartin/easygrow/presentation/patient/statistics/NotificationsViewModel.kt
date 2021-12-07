package com.dariomartin.easygrow.presentation.patient.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Stats are not available yet"
    }
    val text: LiveData<String> = _text
}