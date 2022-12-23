package com.nihalmistry.newsapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nihalmistry.newsapp.utils.CoroutinesDispatcherProvider
import kotlinx.coroutines.withContext

// Base ViewModel class which stores UI state object and allows emitting new UI state via main
// dispatcher
open class BaseViewModel<T: BaseUiModel>(
    val dispatchers: CoroutinesDispatcherProvider
): ViewModel() {

    private var _uiModel = MutableLiveData<T>()
    val uiModel: LiveData<T>
        get() = _uiModel

    // Emits new UI state object via main dispatcher
    suspend fun emitUiState(uiModel: T) {
        withContext(dispatchers.main) {
            _uiModel.value = uiModel
        }
    }
}