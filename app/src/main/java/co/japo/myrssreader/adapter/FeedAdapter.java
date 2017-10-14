package co.japo.myrssreader.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prof.rssparser.Article;

import java.util.List;

import co.japo.myrssreader.R;
import co.japo.myrssreader.model.Feed;

/**
 * Created by japodeveloper on 10/14/17.
 */

public class FeedAdapter extends ArrayAdapter<Article>{

    public FeedAdapter(@NonNull Context context, @NonNull List<Article> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertedView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertedView == null){
            convertedView = inflater.inflate(R.layout.activity_rss_search_item,parent,false);
        }

        TextView title = (TextView) convertedView.findViewById(R.id.title);
        TextView description = (TextView) convertedView.findViewById(R.id.description);
        TextView pubDate = (TextView) convertedView.findViewById(R.id.pubDate);

        Article article = getItem(position);

        title.setText(article.getTitle());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(article.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        }else{
            description.setText(Html.fromHtml(article.getDescription()));
        }
        pubDate.setText(article.getPubDate().toString());

        return convertedView;
    }

}
