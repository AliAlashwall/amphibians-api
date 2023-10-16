package com.example.amphibians

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.data.Amphibians
import com.example.amphibians.data.AmphibiansRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface AmphUiState{
    data class Success( val amphibians : List<Amphibians>) : AmphUiState
    object Loading : AmphUiState
    object Error : AmphUiState
}

class AmphViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel(){
    var amphUiState : AmphUiState by mutableStateOf(AmphUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        viewModelScope.launch {
            amphUiState = try {
                val amphibians =amphibiansRepository.getAmph()      // get response from server
                AmphUiState.Success(amphibians)
            } catch (e: IOException) {
                AmphUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphViewModel(amphibiansRepository)
            }
        }
    }
}
