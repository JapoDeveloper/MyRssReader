package co.japo.myrssreader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by japodeveloper on 10/14/17.
 */

public class RssDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_display);
        Bundle extraData = getIntent().getExtras();
        getSupportActionBar().setTitle(extraData.getString("title"));


        WebView webView = (WebView) findViewById(R.id.rssDisplayView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(extraData.getString("url"));
    }
}

