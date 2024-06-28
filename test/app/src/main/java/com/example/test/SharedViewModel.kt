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
import org.json.JSONObject

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _points = MutableLiveData(sharedPreferences.getInt("points", 10000))
    val points: LiveData<Int> = _points

    private val _redeemedItems = MutableLiveData<MutableMap<String, Boolean>>(loadRedeemedItems())
    val redeemedItems: LiveData<MutableMap<String, Boolean>> = _redeemedItems

    private fun loadRedeemedItems(): MutableMap<String, Boolean> {
        val redeemedItemsJson = sharedPreferences.getString("redeemed_items", "{}")
        val jsonObject = JSONObject(redeemedItemsJson!!)
        val redeemedItemsMap = mutableMapOf<String, Boolean>()
        jsonObject.keys().forEach {
            redeemedItemsMap[it] = jsonObject.getBoolean(it)
        }
        return redeemedItemsMap
    }

    private fun saveRedeemedItems(redeemedItems: MutableMap<String, Boolean>) {
        val jsonObject = JSONObject(redeemedItems as Map<*, *>)
        sharedPreferences.edit().putString("redeemed_items", jsonObject.toString()).apply()
    }

    fun addPoints(pointsToAdd: Int) {
        _points.value = (_points.value ?: 0) + pointsToAdd
        saveToPreferences("points", _points.value ?: 0)
    }

    fun redeemItem(itemName: String, pointsToDeduct: Int): Boolean {
        val currentPoints = _points.value ?: 0
        val redeemed = _redeemedItems.value ?: mutableMapOf()
        return if (currentPoints >= pointsToDeduct && !(redeemed[itemName] ?: false)) {
            _points.value = currentPoints - pointsToDeduct
            redeemed[itemName] = true
            _redeemedItems.value = redeemed
            saveToPreferences("points", currentPoints - pointsToDeduct)
            saveRedeemedItems(redeemed)
            true
        } else {
            false
        }
    }

    fun isItemRedeemed(itemName: String): Boolean {
        return _redeemedItems.value?.get(itemName) ?: false
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
