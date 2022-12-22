package com.nihalmistry.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nihalmistry.newsapp.R
import com.nihalmistry.newsapp.databinding.ActivityNewsListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding
    private val adapter: NewsListAdapter = NewsListAdapter()

    val newsListVM by viewModel<NewsListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)
        binding.viewModel = newsListVM

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvNews.layoutManager = layoutManager
        binding.rvNews.setHasFixedSize(false)
        binding.rvNews.adapter = adapter

        newsListVM.articleList.observe(this) {
            adapter.submitList(it)
        }

        newsListVM.uiModel.observe(this) {
            binding.invalidateAll()
        }

        adapter.getArticleClickLiveData().observe(this) {
            val intent = Intent(this@NewsListActivity, NewsDetailActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }

        newsListVM.refreshTopHeadlines()
    }
}