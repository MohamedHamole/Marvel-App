package com.example.imagesapp.presentation.images

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesapp.data.local.ImagesEntity
import com.example.imagesapp.data.model.CharactersCardItem
import com.example.imagesapp.data.repository.ImagesListRepo
import com.example.imagesapp.utils.UiState
import com.example.imagesapp.utils.toCharactersCardItemList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ImagesViewModel(
    private val imagesListRepo: ImagesListRepo
): ViewModel() {

    private val charactersMS = MutableStateFlow<UiState<List<CharactersCardItem>>>(UiState.Empty)
    val charactersSF: StateFlow<UiState<List<CharactersCardItem>>> get() = charactersMS

    private val _characterFromDB = MutableStateFlow<List<ImagesEntity>>(emptyList())
    val charactersFromDB: StateFlow<List<ImagesEntity>> get() = _characterFromDB

    private val _selectedImageUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val selectedImageUri: StateFlow<Uri> get() = _selectedImageUri

    fun getCharacters(isInternet: Boolean){
        viewModelScope.launch {
            if (isInternet){
                imagesListRepo.getCharacters().collectLatest {
                        response -> charactersMS.emit(response)
                }
            } else {
                imagesListRepo.getAllImagesFromDB().collectLatest {
                        response -> charactersMS.emit(UiState.Success(response.toCharactersCardItemList()))
                }
            }

        }
    }

    fun insertImagesToDatabaseFromRemote(imagesEntity: List<ImagesEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
                imagesListRepo.insertImagesToDatabaseFromRemote(imagesEntity)
        }
    }

    fun handleSelectedImage(uri: Uri?) {
        // Process the selected image URI here
        // For example:
        viewModelScope.launch {
            // Store the image URI in the database, upload it to a server, etc.
            if (uri != null) {
                _selectedImageUri.emit(uri)
            }
        }
    }

}