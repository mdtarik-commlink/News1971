package com.commlinkinfotech.news1971.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.commlinkinfotech.news1971.R;
import com.commlinkinfotech.news1971.model.NewsItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


/**
 * Created by tarik on 5/1/16.
 */
public class TopNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<NewsItem> firstLevelTopNews;
    ArrayList<NewsItem> secondLevelTopNews;

    int lastBoundedView = -1;

    public TopNewsAdapter(Context context, ArrayList<NewsItem> topNews) {
        super();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.firstLevelTopNews = new ArrayList<>();
        this.secondLevelTopNews = new ArrayList<>();
        for (NewsItem item : topNews) {
            if (item.getNews_type() == NewsItem.NEWS_TYPE.FIRST_LEVEL_TOP_NEWS) {
                this.firstLevelTopNews.add(item);
            } else if (item.getNews_type() == NewsItem.NEWS_TYPE.SECOND_LEVEL_TOP_NEWS) {
                this.secondLevelTopNews.add(item);
            }
        }
    }

    public class FirstLevelViewHolder extends RecyclerView.ViewHolder {

        View view;
        ViewFlipper viewFlipper;

        public FirstLevelViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.viewFlipper = (ViewFlipper) view.findViewById(R.id.first_level_news_flipper);
        }
    }

    public class SecondLevelViewHolder extends RecyclerView.ViewHolder {

        View view;

        public SecondLevelViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @Override
    public int getItemCount() {
        if (firstLevelTopNews.size() > 0) {
            return 1 + secondLevelTopNews.size();
        } else {
            return secondLevelTopNews.size();
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ViewType.FIRST_LEVEL_VIEW;
        } else {
            return ViewType.SECOND_LEVEL_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == ViewType.FIRST_LEVEL_VIEW) {
            View view = this.layoutInflater.inflate(R.layout.first_level_top_news_viewflipper, parent, false);
            vh = new FirstLevelViewHolder(view);
        } else {
            View view = this.layoutInflater.inflate(R.layout.second_level_top_news_item_layout, parent, false);
            vh = new SecondLevelViewHolder(view);
        }
        return vh;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == ViewType.FIRST_LEVEL_VIEW) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (NewsItem newsItem : this.firstLevelTopNews) {
                View view = layoutInflater.inflate(R.layout.first_level_top_news_item_layout, null);
                ((TextView) view.findViewById(R.id.title)).setText(newsItem.getTitle());
                ((SimpleDraweeView) view.findViewById(R.id.image)).setImageURI(Uri.parse(newsItem.getFeatured_image_url()));
                ((FirstLevelViewHolder) holder).viewFlipper.addView(view);
            }
        } else {
            if (holder instanceof SecondLevelViewHolder) {
                ((TextView) ((SecondLevelViewHolder) holder).view.findViewById(R.id.title)).setText(this.secondLevelTopNews.get(position - 1).getTitle());
                ((SimpleDraweeView) ((SecondLevelViewHolder) holder).view.findViewById(R.id.image)).setImageURI(Uri.parse(this.secondLevelTopNews.get(position - 1).getFeatured_image_url()));
                if (lastBoundedView < position) {
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.grow_from_middle);
                    ((SecondLevelViewHolder) holder).view.startAnimation(animation);
                }
            }
        }

        this.lastBoundedView = position;
    }

    private static class ViewType {
        public static final int FIRST_LEVEL_VIEW = 101;
        public static final int SECOND_LEVEL_VIEW = 102;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        Log.i("tarik", "recycled");
    }

    public void addFirstLevelNews(ArrayList<NewsItem> newsItems) {
        if (this.firstLevelTopNews.size() == 0) {
            this.firstLevelTopNews.addAll(newsItems);
            this.notifyDataSetChanged();
        } else {
            this.firstLevelTopNews.addAll(newsItems);
            this.notifyItemChanged(0);
        }

    }

    public void addSecondLevelNews(ArrayList<NewsItem> newsItems) {
        this.secondLevelTopNews.addAll(newsItems);
        this.notifyDataSetChanged();
    }
}
