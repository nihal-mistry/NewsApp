package com.nihalmistry.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.nihalmistry.newsapp.R
import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.databinding.ItemNewsListBinding

class NewsListAdapter: Adapter<NewsListAdapter.NewsItemViewHolder>() {
    private val items = mutableListOf<NewsArticle>()

    private val _clickLiveData = MutableLiveData<NewsArticle>()

    inner class NewsItemViewHolder(val binding: ItemNewsListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemNewsListBinding>(
            LayoutInflater.from(parent.context), R.layout.item_news_list, parent, false
        )
        return NewsItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val article = getItem(position)
        article?.let {
            holder.binding.article = it
            holder.itemView.setOnClickListener {
                _clickLiveData.postValue(getItem(holder.adapterPosition))
            }
        }
    }

    override fun getItemCount(): Int = items.size

    private fun getItem(position: Int): NewsArticle? {
        if (position < items.size)
            return items[position]
        return null
    }

    fun submitList(scrips: List<NewsArticle>) {
        items.clear()
        items.addAll(scrips)
        notifyDataSetChanged()
    }

    fun getArticleClickLiveData(): LiveData<NewsArticle> = _clickLiveData
}