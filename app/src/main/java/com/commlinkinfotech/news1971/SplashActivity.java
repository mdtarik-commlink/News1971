package com.commlinkinfotech.news1971;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.commlinkinfotech.news1971.utils.UtilityFunctions;

public class SplashActivity extends AppCompatActivity {

    ImageView background, logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        background = (ImageView) findViewById(R.id.background);
        logo = (ImageView) findViewById(R.id.logo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.animateBackground();
    }

    private void animateBackground() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                background.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                background.setVisibility(View.VISIBLE);
                animateLogo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        background.startAnimation(animation);
    }

    private void animateLogo() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                logo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.VISIBLE);
                moveToHomeActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo.startAnimation(animation);
    }

    private void moveToHomeActivity() {

        if (UtilityFunctions.isNetworkAvailable(this)) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            SplashActivity.this.finish();
        } else {
            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "ইন্টারনেট সংযোগ চালু করুন।", Snackbar.LENGTH_INDEFINITE)
                    .setAction("চালু করেছি", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moveToHomeActivity();
                        }
                    });
            View view = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
        }


    }
}
