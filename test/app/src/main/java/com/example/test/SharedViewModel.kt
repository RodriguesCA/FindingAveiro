package com.example.test

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _points = MutableLiveData(sharedPreferences.getInt("points", 0))
    val points: LiveData<Int> get() = _points

    fun addPoints(newPoints: Int) {
        _points.value = (_points.value ?: 0) + newPoints
        saveToPreferences("points", _points.value ?: 0)
    }

    private val _username = mutableStateOf(sharedPreferences.getString("username", "") ?: "")
    val username: State<String> = _username

    private val _password = mutableStateOf(sharedPreferences.getString("password", "") ?: "")
    val password: State<String> = _password

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
        saveToPreferences("username", newUsername)
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
        saveToPreferences("password", newPassword)
    }

    private fun saveToPreferences(key: String, value: String) {
        viewModelScope.launch {
            sharedPreferences.edit().putString(key, value).apply()
        }
    }

    private fun saveToPreferences(key: String, value: Int) {
        viewModelScope.launch {
            sharedPreferences.edit().putInt(key, value).apply()
        }
    }
}
