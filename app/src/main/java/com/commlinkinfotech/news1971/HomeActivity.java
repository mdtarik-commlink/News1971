package com.commlinkinfotech.news1971;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.commlinkinfotech.news1971.asyncTask.BackgroundTask;
import com.commlinkinfotech.news1971.asyncTask.BackgroundTaskListener;
import com.commlinkinfotech.news1971.fragments.HomeFragment;
import com.commlinkinfotech.news1971.interfaces.OnFragmentInteractionListener;
import com.commlinkinfotech.news1971.interfaces.ProgressLoaderListener;
import com.commlinkinfotech.news1971.model.NewsItem;
import com.commlinkinfotech.news1971.model.NewsMenuItem;
import com.commlinkinfotech.news1971.utils.Constants;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, ProgressLoaderListener, BackgroundTaskListener {

    private Toolbar toolbar;
    private int progressLoaderViewCounter = 0;
    private RelativeLayout breaking_news_container;
    private ViewFlipper breaking_news;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.inflateMenu(R.menu.activity_home_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        // selecting the first item
        navigationView.setCheckedItem(R.id.nav_home);
        this.onNavigationItemSelected(navigationView.getMenu().getItem(0));

        breaking_news_container = (RelativeLayout) findViewById(R.id.breaking_news_container);
        breaking_news = (ViewFlipper) findViewById(R.id.breaking_news);

        BackgroundTask breakingNewsBackgroundTask = new BackgroundTask(this, this, Constants.ASYNCTASK_IDENTIFIERS.GET_BREAKING_NEWS);
        breakingNewsBackgroundTask.execute();

        BackgroundTask menuBackgroundTask = new BackgroundTask(this, this, Constants.ASYNCTASK_IDENTIFIERS.GET_MENU);
        menuBackgroundTask.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, HomeFragment.newInstance(getResources().getString(R.string.app_name))).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(String TAG, Object data) {
        getSupportActionBar().setTitle((String) data);
    }

    @Override
    public void showProgressLoader() {
        if (++this.progressLoaderViewCounter > 0) {
            ProgressBar progressBar = (ProgressBar) toolbar.findViewById(R.id.progress_loader);
            progressBar.setVisibility(View.VISIBLE);
        }
        ;
    }

    @Override
    public void hideProgressLoader() {
        if (--this.progressLoaderViewCounter <= 0) {
            ProgressBar progressBar = (ProgressBar) toolbar.findViewById(R.id.progress_loader);
            progressBar.setVisibility(View.GONE);
            this.progressLoaderViewCounter = 0;
        }
    }

    @Override
    public void onPreExecuteAsynctask(int identifier) {
        switch (identifier) {
            case Constants.ASYNCTASK_IDENTIFIERS.GET_BREAKING_NEWS:
            case Constants.ASYNCTASK_IDENTIFIERS.GET_MENU:
                this.showProgressLoader();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPostExecuteAsynctask(int identifier, Object data) {
        if (identifier == Constants.ASYNCTASK_IDENTIFIERS.GET_BREAKING_NEWS) {
            this.hideProgressLoader();
            if (data != null && data instanceof ArrayList<?>) {
                if (((ArrayList<NewsItem>) data).size() > 0) {
                    for (NewsItem item : (ArrayList<NewsItem>) data) {
                        LayoutInflater lf = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        TextView textView = (TextView) lf.inflate(R.layout.breaking_news_item, null);
                        textView.setText(item.getTitle());
                        textView.setSelected(true);
                        breaking_news.addView(textView);
                    }

                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
                    animation.setDuration(1000);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            breaking_news_container.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            breaking_news_container.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    breaking_news_container.startAnimation(animation);
                } else {
                    breaking_news_container.setVisibility(View.GONE);
                }
            }
        } else if (identifier == Constants.ASYNCTASK_IDENTIFIERS.GET_MENU) {
            this.hideProgressLoader();
            if (data != null && data instanceof ArrayList<?>) {
                if (((ArrayList<NewsMenuItem>) data).size() > 0) {
                    for (NewsMenuItem item : (ArrayList<NewsMenuItem>) data) {
                        Menu navMenu = navigationView.getMenu();
                        MenuItem item_category = navMenu.getItem(1);
                        Menu menu_category = item_category.getSubMenu();
                        menu_category.add(R.id.nav_category_group, Integer.valueOf(item.getId()), Menu.NONE, item.getTitle());
                    }
                }

                this.showHomeButtonInstruction();

            }
        }

    }

    public void showHomeButtonInstruction(){
        TapTargetView.showFor(this,
                TapTarget.forToolbarNavigationIcon(toolbar,
                        "আপনার পছন্দের বিষয়ের খবর দেখতে এখানে ক্লিক করে যে কোন বিষয় নির্বাচন করতে পারেন।",
                        ""
                ).id(1)
                .outerCircleColor(R.color.colorPrimary)
        );
    }
}
