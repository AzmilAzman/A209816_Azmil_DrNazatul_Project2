package com.example.a209816_azmil_nazatul_project02

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a209816_azmil_nazatul_project02.data.FeedbackDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FeedbackRepository(
        FeedbackDatabase.getDatabase(application).feedbackDao()
    )

    var data by mutableStateOf(FeedbackData())
        private set

    var messageInput by mutableStateOf("")
        private set

    // Keadaan baharu untuk menyimpan URI gambar kamera dan Koordinat GPS
    var capturedImageUri by mutableStateOf<Uri?>(null)
        private set

    var latitudeInput by mutableStateOf("")
        private set

    var longitudeInput by mutableStateOf("")
        private set

    val profile = UserProfile()

    val historyList: StateFlow<List<HistoryItem>> = repository.allHistory
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setCategory(category: String) {
        data = data.copy(category = category)
    }

    fun updateInput(text: String) {
        messageInput = text
    }

    // Fungsi helper untuk mengemaskini URI Gambar dan GPS Lokasi
    fun updateCapturedImage(uri: Uri?) {
        capturedImageUri = uri
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        latitudeInput = latitude.toString()
        longitudeInput = longitude.toString()
    }

    fun submit() {
        data = data.copy(message = messageInput)

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val currentDate = sdf.format(Date())

        viewModelScope.launch {
            repository.insert(
                HistoryItem(
                    category = data.category,
                    message = messageInput,
                    date = currentDate,
                    status = "Dihantar",
                    // Hantar data Media & Lokasi ke Room Database repository anda
                    imageUri = capturedImageUri?.toString() ?: "",
                    latitude = latitudeInput,
                    longitude = longitudeInput
                )
            )
        }
    }

    fun reset() {
        messageInput = ""
        capturedImageUri = null
        latitudeInput = ""
        longitudeInput = ""
    }
}