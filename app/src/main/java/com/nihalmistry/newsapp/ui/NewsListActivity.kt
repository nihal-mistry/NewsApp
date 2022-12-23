package com.nihalmistry.newsapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.nihalmistry.newsapp.R
import com.nihalmistry.newsapp.databinding.ActivityNewsListBinding
import com.nihalmistry.newsapp.di.USER_PREFS
import com.nihalmistry.newsapp.utils.Constants
import com.nihalmistry.newsapp.utils.Constants.Companion.COUNTRY_FLAG_MAP
import com.nihalmistry.newsapp.utils.Constants.Companion.COUNTRY_KEY
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class NewsListActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivityNewsListBinding
    private val adapter: NewsListAdapter = NewsListAdapter()

    val newsListVM by viewModel<NewsListViewModel>()
    val prefs by inject<SharedPreferences>(named(USER_PREFS))

    private val chooseCountryDialogFragment = ChooseCountryDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)
        binding.viewModel = newsListVM

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvNews.layoutManager = layoutManager
        binding.rvNews.setHasFixedSize(false)
        binding.rvNews.adapter = adapter

        val dividerItemDecoration = MaterialDividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDividerThicknessResource(this, R.dimen.news_list_divider_thickness)
        dividerItemDecoration.setDividerColorResource(this, android.R.color.transparent)

        binding.rvNews.addItemDecoration(dividerItemDecoration)

        binding.btnRetry.setOnClickListener {
            newsListVM.refreshTopHeadlines()
        }

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

        prefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val currentLanguage = prefs.getString(COUNTRY_KEY, "us")!!
        val flagIcon = COUNTRY_FLAG_MAP.get(currentLanguage)!!
        menu?.add(Menu.NONE, R.id.language_menu, Menu.NONE, R.string.select_language)
            ?.setIcon(flagIcon)
            ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.language_menu -> showLanguagePopup()
        }
        return true
    }

    override fun onStop() {
        prefs.unregisterOnSharedPreferenceChangeListener(this)
        binding.unbind()
        super.onStop()
    }

    fun showLanguagePopup() {
        if (!chooseCountryDialogFragment.isAdded)
            chooseCountryDialogFragment.show(supportFragmentManager, "chooseCountry")
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key?.let {
            if (key == COUNTRY_KEY) {
                binding.rvNews.smoothScrollToPosition(0)
                newsListVM.refreshTopHeadlines()
            }
        }
    }
}