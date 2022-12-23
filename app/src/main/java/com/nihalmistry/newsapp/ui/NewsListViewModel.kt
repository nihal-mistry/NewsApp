package com.nihalmistry.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nihalmistry.newsapp.R
import com.nihalmistry.newsapp.base.BaseUiModel
import com.nihalmistry.newsapp.base.BaseViewModel
import com.nihalmistry.newsapp.data.NewsRepository
import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.utils.CoroutinesDispatcherProvider
import com.nihalmistry.newsapp.utils.Result
import kotlinx.coroutines.launch

class NewsListViewModel(
    val newsRepository: NewsRepository,
    dispatchers: CoroutinesDispatcherProvider
) :
    BaseViewModel<NewsListUiModel>(dispatchers) {

    // Store the list of articles fetched from API
    private val _articleList = MutableLiveData<List<NewsArticle>>()
    val articleList: LiveData<List<NewsArticle>>
        get() = _articleList

    // Runs suspend function in viewModelScope to fetch news article from NewsRepository
    // Emits UI state based on the API call result
    fun refreshTopHeadlines() = viewModelScope.launch {
        emitUiState(NewsListUiModel(showProgress = true))
        when (val result = newsRepository.getTopHeadlines()) {
            is Result.Success -> {
                _articleList.postValue(result.data)
                emitUiState(NewsListUiModel())
            }
            is Result.Error -> {
                emitUiState(NewsListUiModel(showError = true, errorTitle = R.string.failed_to_fetch_news, errorDescription = result.exception.message))
            }
        }
    }
}

// Data class to store UI state of NewsListActivity
data class NewsListUiModel(
    val showProgress: Boolean = false,
    val showError: Boolean = false,
    val errorTitle: Int = R.string.empty_string,
    val errorDescription: String? = ""
) : BaseUiModel() {
    override fun toString(): String {
        return "NewsListUiModel: showProgress = $showProgress"
    }
}
