package com.example.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PointsViewModel : ViewModel() {
    private val _points = MutableLiveData(100)
    val points: LiveData<Int> = _points

    private val _redeemedItems = MutableLiveData<MutableMap<String, Boolean>>(mutableMapOf())
    val redeemedItems: LiveData<MutableMap<String, Boolean>> = _redeemedItems

    fun addPoints(pointsToAdd: Int) {
        _points.value = (_points.value ?: 0) + pointsToAdd
    }

    fun redeemItem(itemName: String, pointsToDeduct: Int): Boolean {
        val currentPoints = _points.value ?: 0
        val redeemed = _redeemedItems.value ?: mutableMapOf()
        return if (currentPoints >= pointsToDeduct && !(redeemed[itemName] ?: false)) {
            _points.value = currentPoints - pointsToDeduct
            redeemed[itemName] = true
            _redeemedItems.value = redeemed
            true
        } else {
            false
        }
    }

    fun isItemRedeemed(itemName: String): Boolean {
        return _redeemedItems.value?.get(itemName) ?: false
    }
}