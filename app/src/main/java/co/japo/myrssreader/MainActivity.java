package co.japo.myrssreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import co.japo.myrssreader.adapter.FeedAdapter;
import co.japo.myrssreader.model.Feed;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alert;
    private List<Feed> feeds;
    private Spinner rssFeedSelector;
    private ListView searchResult;
    private Feed currentFeed;

    private void init(){
        feeds = new ArrayList(){
            {
                add(new Feed("Seleccione una opción",null));
                add(new Feed("Diario Libre","https://www.diariolibre.com/rss/portada.xml"));
                add(new Feed("New York Times","http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"));
                add(new Feed("Banco Interamericano de Desarrollo","http://www.iadb.org/rss/feeds/rss.cfm?feedType=news&feedname=ws&lang=es"));
                add(new Feed("Wired","https://www.wired.com/feed/rss"));
            }
        };

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wait a moment...");
        builder.setView(new ProgressBar(this));

        alert = builder.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        rssFeedSelector = (Spinner) findViewById(R.id.rssFeedSelector);

        rssFeedSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadRssFeed();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchResult = (ListView) findViewById(R.id.searchResult);
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Article article = (Article) adapterView.getItemAtPosition(i);
                final String url = article.getLink();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Que vista prefieres?");
                dialogBuilder.setPositiveButton("Desde app", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this,RssDisplayActivity.class);
                        intent.putExtra("url",url);
                        intent.putExtra("title",currentFeed.getName());
                        startActivity(intent);
                    }
                });
                dialogBuilder.setNegativeButton("Navegador externo",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });

                dialogBuilder.create().show();

            }
        });

        ArrayAdapter<Feed> adapter = new ArrayAdapter<Feed>(this,android.R.layout.simple_spinner_item,feeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rssFeedSelector.setAdapter(adapter);


    }

    public void loadRssFeed(){
        currentFeed = (Feed) rssFeedSelector.getSelectedItem();
        String url = ((Feed) rssFeedSelector.getSelectedItem()).getUrl();
        if(url != null) {
            alert.show();
            Parser parser = new Parser();
            parser.onFinish(new Parser.OnTaskCompleted() {

                @Override
                public void onTaskCompleted(ArrayList<Article> list) {
                    FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, list);
                    searchResult.setAdapter(feedAdapter);
                    alert.hide();
                }

                @Override
                public void onError() {

                }
            });
            parser.execute(url);
        }else{
            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, new ArrayList());
            searchResult.setAdapter(feedAdapter);
        }
    }
}
