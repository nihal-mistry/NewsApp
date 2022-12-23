package com.nihalmistry.newsapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.nihalmistry.newsapp.R
import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)
        firebaseAnalytics = Firebase.analytics

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "News Detail")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "NewsDetailActivity")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val article = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("article") as NewsArticle
        } else {
            intent.getSerializableExtra("article", NewsArticle::class.java)
        }

        supportActionBar?.title = article?.author

        binding.article = article
    }

    override fun onStop() {
        binding.unbind()
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            firebaseAnalytics.logEvent("news_detail_home_clicked") {}
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}