// PointsViewModel.kt
package com.example.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PointsViewModel : ViewModel() {
    private val _points = MutableLiveData(0)
    val points: LiveData<Int> get() = _points

    fun addPoints(newPoints: Int) {
        _points.value = (_points.value ?: 0) + newPoints
    }
}
