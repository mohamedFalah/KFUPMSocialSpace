package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.kfupmsocialspace.model.News;

public class NewsWebView extends AppCompatActivity {

    private WebView webView;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        Bundle data = getIntent().getExtras();
        news = data.getParcelable("clickedNews");

        webView = (WebView) findViewById(R.id.news_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(news.getPage());
    }
}
