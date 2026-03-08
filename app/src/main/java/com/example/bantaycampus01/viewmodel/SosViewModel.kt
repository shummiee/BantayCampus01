package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bantaycampus01.model.SosAlert
import com.example.bantaycampus01.repository.SosRepository

class SosViewModel : ViewModel() {

    private val repository = SosRepository()

    fun sendSOS(
        userId: String,
        userName: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val sosAlert = SosAlert(
            userId = userId,
            userName = userName
        )
        repository.sendSOS(sosAlert, onSuccess, onFailure)
    }
}