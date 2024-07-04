package com.example.test

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import org.json.JSONObject

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _points = MutableLiveData(sharedPreferences.getInt("points", 10000))
    val points: LiveData<Int> = _points

    private val _id = mutableStateOf(sharedPreferences.getString("id", "") ?: "")
    val id: State<String> = _id

    private val _redeemedItems = MutableLiveData<MutableMap<String, Boolean>>(loadRedeemedItems())
    // val redeemedItems: LiveData<MutableMap<String, Boolean>> = _redeemedItems

    private val _polygonData = MutableLiveData<List<PolygonData>>(loadPolygonData())
    val polygonData: LiveData<List<PolygonData>> = _polygonData

    private fun loadPolygonData(): List<PolygonData> {
        return listOf(
            PolygonData(
                id = "1",
                points = listOf(
                    LatLng(40.63787867676456, -8.65856742392844), // top left corner
                    LatLng(40.63361546575474, -8.661243742835701), // bottom left corner
                    LatLng(40.62004155057745, -8.658948487500192), // bottom right corner
                    LatLng(40.62452855358944, -8.64976460361052), // top right corner
                ),
                title = "Universidade de Aveiro",
                snippet = "Universidade de Aveiro"
            ),
            PolygonData(
                id = "2",
                points = listOf(
                    LatLng(40.63272744890467, -8.64855518938427), // top right corner
                    LatLng(40.63338985277366, -8.648786538128617), // extra
                    LatLng(40.6399927705059, -8.655116460602876), // top left corner
                    LatLng(40.63787867676456, -8.65856742392844), // bottom left corner
                    LatLng(40.63066400728303, -8.653815495400446), // bottom right corner
                ),
                title = "Parque da Macaca",
                snippet = "Jardins Proibidos"
            ),
            PolygonData(
                id = "3",
                points = listOf(
                    LatLng(40.64584894137766, -8.662885293949005), // top left corner
                    LatLng(40.6366802512532, -8.686951856569015), // extra
                    LatLng(40.63755790248674, -8.687716520992657), // extra
                    LatLng(40.63754339594855, -8.6886914681328), // extra
                    LatLng(40.626249207879724, -8.682347226061006), // bottom right corner
                    LatLng(40.63787867676456, -8.65856742392844), // top right corner
                    LatLng(40.639593752531106, -8.655944809760609), // extra
                ),
                title = "Salinas de Aveiro",
                snippet = "Bem Salgado"
            ),
            PolygonData(
                id = "4",
                points = listOf(
                    LatLng(40.63754339594855, -8.6886914681328), // top right corner
                    LatLng(40.630624731442886, -8.750970539177398), // bottom right corner
                    LatLng(40.640469790728964, -8.75011962660272), // extra
                    LatLng(40.64205154345762, -8.758143275839053), // extra
                    LatLng(40.642417082386906, -8.757913786302257), // extra
                    LatLng(40.64075503936687, -8.750588131372883), // extra
                    LatLng(40.64413984641427, -8.749853123659687), // bottom left corner
                    LatLng(40.65825001627285, -8.719445773361878), // extra
                    LatLng(40.65896005972578, -8.709161235108828), // extra
                    LatLng(40.65476769594774, -8.702753020122302), // extra
                ),
                title = "Praia da Barra",
                snippet = "Passa no Setimo"
            ),
            PolygonData(
                id = "5",
                points = listOf(
                    LatLng(40.63754339594855, -8.6886914681328), // top left corner
                    LatLng(40.630624731442886, -8.750970539177398), // bottom left corner
                    LatLng(40.608877485635745, -8.75648653881182), // bottom right corner
                    LatLng(40.6005038853122, -8.690439825852636), // top right corner
                    LatLng(40.626249207879724, -8.682347226061006), // extra
                ),
                title = "Praia da Costa Nova",
                snippet = "Ve as casas riscas"
            ),
            PolygonData(
                id = "6",
                points = listOf(
                    LatLng(40.626249207879724, -8.682347226061006), // bottom right corner
                    LatLng(40.6005038853122, -8.690439825852636), // extra
                    LatLng(40.615252910083306, -8.677101677162222), // bottom left corner
                    LatLng(40.62004155057745, -8.658948487500192), // top left corner
                    LatLng(40.63361546575474, -8.661243742835701), // extra
                    LatLng(40.63787867676456, -8.65856742392844), // top right corner
                ),
                title = "Passadiços de Aveiro",
                snippet = "Não Caias a Agua"
            ),
            PolygonData(
                id = "7",
                points = listOf(
                    LatLng(40.64101637820578, -8.641803370634964), // top left corner
                    LatLng(40.63749526174772, -8.652744098440001), // bottom left corner
                    LatLng(40.63338985277366, -8.648786538128617), // extra
                    LatLng(40.63272744890467, -8.64855518938427), // extra
                    LatLng(40.63066400728303, -8.653815495400446), // extra
                    LatLng(40.62452855358944, -8.64976460361052), // bottom right corner
                    LatLng(40.630406337092076, -8.642941462098724), // top right corner
                ),
                title= "Fabrica do Melia",
                snippet ="Não vais ser explorado"
            ),
            PolygonData(
                id = "8",
                points = listOf(
                    LatLng(40.64089602753747, -8.657386395925464), // bottom left corner
                    LatLng(40.639593752531106, -8.655944809760609), // extra
                    LatLng(40.6399927705059, -8.655116460602876), // extra
                    LatLng(40.63749526174772, -8.652744098440001), // bottom right corner
                    LatLng(40.64101637820578, -8.641803370634964), // top right corner
                    LatLng(40.64406001340473, -8.641126114819752), // top left corner
                ),
                title = "Pontes",
                snippet = "Atravessa para o QRCODE"
            ),
            PolygonData(
                id = "9",
                points = listOf(
                    LatLng(40.64302240946543, -8.659765781903431), // top left corner
                    LatLng(40.651240505405895, -8.645279160695267), // top right corner
                    LatLng(40.64406001340473, -8.641126114819752), // bottom right corner
                    LatLng(40.64089602753747, -8.657386395925464), // bottom left corner
                ),
                title = "Praça de Aveiro",
                snippet = "Vai ao Mercado"
            )
        )
    }

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

    fun updateId(newId: String) {
        try {
            _id.value = newId
            saveToPreferences("id", newId)
            Log.d("Message", id.value)
        } catch (e: Exception) {
           print(e)
        }
    }

    fun addPoints(pointsToAdd: Int) {
        _points.value = (_points.value ?: 0) + pointsToAdd
        saveToPreferences("points", _points.value ?: 0)
    }

    fun redeemItem(itemName: String, pointsToDeduct: Int): Boolean {
        val currentPoints = _points.value ?: 0
        val redeemed = _redeemedItems.value ?: mutableMapOf()
        return if (currentPoints >= pointsToDeduct && redeemed[itemName] == true) {
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

    fun removePolygonById(polygonId: String) {
        try {
            val updatedPolygonData = _polygonData.value?.filterNot { it.id == polygonId }
            _polygonData.value = updatedPolygonData ?: emptyList()
            Log.d("Message","${polygonData.value}")
        } catch (e: Exception) {
            print(e)
        }
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
