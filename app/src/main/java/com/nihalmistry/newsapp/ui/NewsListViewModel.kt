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

    private val _articleList = MutableLiveData<List<NewsArticle>>()
    val articleList: LiveData<List<NewsArticle>>
        get() = _articleList

    fun refreshTopHeadlines() = viewModelScope.launch {
        emitUiState(NewsListUiModel(showProgress = true))
        val result = newsRepository.getTopHeadlines()
        when (result) {
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
