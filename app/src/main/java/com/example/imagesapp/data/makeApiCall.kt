package com.example.imagesapp.data

import android.util.Log
import com.example.imagesapp.utils.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

suspend fun <T> makeAPiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, block: suspend () -> T) = flow {
    emit(UiState.Loading(true))
    val result = block()
    emit(UiState.Success(result))
//    emit(UiState.Loading(false))
//    emit(UiState.Empty)
}.flowOn(dispatcher).catch { t ->
    emit(UiState.Error(t.message.toString()))
    emit(UiState.Loading(false))
}