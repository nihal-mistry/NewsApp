package com.nihalmistry.newsapp

import android.content.SharedPreferences
import com.nihalmistry.newsapp.data.NewsRepositoryImpl
import com.nihalmistry.newsapp.data.api.NewsApi
import com.nihalmistry.newsapp.data.api_models.GetTopHeadlinesResponse
import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.utils.Result
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.IOException

class NewsRepositoryUnitTest {

    val sharedPreferences: SharedPreferences = Mockito.mock(SharedPreferences::class.java)
    val newsApi: NewsApi = Mockito.mock(NewsApi::class.java)

    val newsRepository = NewsRepositoryImpl(newsApi, sharedPreferences)

    @Test
    fun `API call is successful`() = runBlocking {

        val getTopHeadlinesResponse = GetTopHeadlinesResponse()
        getTopHeadlinesResponse.articles = arrayListOf()
        getTopHeadlinesResponse.articles.add(NewsArticle(title = "test"))

        val response = Response.success(getTopHeadlinesResponse)

        whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn("us")
        whenever(newsApi.getTopHeadlines(anyString(), anyString())).thenReturn(response)

        val result = newsRepository.getTopHeadlines()
        when (result) {
            is Result.Success -> assertEquals("test", result.data[0].title)
            is Result.Error -> assertTrue(false)
        }
    }

    @Test
    fun `API call threw exception`() = runBlocking {

        val exception = "Failed to connect to newsapi.org"
        whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn("us")
        whenever(newsApi.getTopHeadlines(anyString(), anyString())).thenThrow(RuntimeException(exception))

        val result = newsRepository.getTopHeadlines()
        when (result) {
            is Result.Success -> assertTrue(false)
            is Result.Error -> {
                assertTrue(result.exception is IOException)
                assertEquals(exception, result.exception.message)
            }
        }
    }

    @Test
    fun `API call returned error`() = runBlocking {

        val responseBody = "API key invalid".toResponseBody()
        val response = Response.error<GetTopHeadlinesResponse>(401, responseBody)

        whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn("us")
        whenever(newsApi.getTopHeadlines(anyString(), anyString())).thenReturn(response)

        val result = newsRepository.getTopHeadlines()
        assert(result is Result.Error)
        val error = result as Result.Error
        assert(error.exception is IOException)
        // response.message returns "Response.error()" by default for synthetic error response
        assertEquals("Error : 401 Response.error()", error.exception.message)
    }
}