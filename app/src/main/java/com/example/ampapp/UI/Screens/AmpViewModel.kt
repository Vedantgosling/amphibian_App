package com.example.ampapp.UI.Screens

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ampapp.AmpdataApp
import com.example.ampapp.Data.AmpPhotoRepo
import com.example.ampapp.Model.Amp
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AmpUiState{
    data class Success(val amps : List<Amp>):AmpUiState
    object Error:AmpUiState
    object Loading:AmpUiState
}

class AmpViewModel(private val ampPhotoRepo: AmpPhotoRepo): ViewModel() {
    var ampUiState: AmpUiState by mutableStateOf(AmpUiState.Loading)
        private set

    init {
        getAmps()
    }

    fun getAmps(){
        viewModelScope.launch{
            ampUiState = AmpUiState.Loading
            ampUiState = try {
                AmpUiState.Success(ampPhotoRepo.getAmpsphotos())
            }catch (e:IOException){
                AmpUiState.Error
            }catch (e:retrofit2.HttpException){
                AmpUiState.Error
            }
        }
    }

    companion object{
        val Factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application: AmpdataApp
                application = (this[APPLICATION_KEY] as AmpdataApp)
                val amprepo = application.Container.ampPhotoRepo
                AmpViewModel(amprepo)

            }
        }
    }


}