package com.example.imagesapp.presentation.imageDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesapp.data.repository.ImageDetailsRepo
import com.example.imagesapp.utils.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ImageDetailsViewModel(private val imageDetailsRepo: ImageDetailsRepo) : ViewModel() {

    private val _imageResized = MutableStateFlow("")
    val imageResized = _imageResized.asStateFlow()

    fun resizeImage(imageUrl: String, width: Int, height: Int) {
        viewModelScope.launch {
            supervisorScope {
                val uploadDeferred = async { imageDetailsRepo.uploadImage(imageUrl = imageUrl) }

                val uploadResult = uploadDeferred.await()
                uploadResult.collect {
                    when (it) {
                        is UiState.Success -> {
                            val uploadData = it.data

                            val resizeDeferred = async {
                                imageDetailsRepo.resizeImage(
                                        uploadData.headers()["Location"].toString(),
                                        method = "fit",
                                        width = width,
                                        height = height
                                    )
                            }

                            val resizeResult = resizeDeferred.await()
                            resizeResult.collectLatest { responseUiState ->
                                _imageResized.emit(responseUiState.toString())
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}