package com.example.fyptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewPagerAdapter adapter;
    private ViewPager mSlideView;
    private Button mButton;
    private TextView[] mdots;
    private LinearLayout mTutLinear;
    private Animation fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideView = findViewById(R.id.intro_swiper);
        mTutLinear = findViewById(R.id.linearLayout);
        adapter = new ViewPagerAdapter(this);
        mSlideView.setAdapter(adapter);
        mSlideView.addOnPageChangeListener(viewListener);
        mdots = new TextView[3];
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        mButton = findViewById(R.id.get_started);
        mButton.setEnabled(false);
        mButton.setVisibility(View.INVISIBLE);
        mButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        });
    }

    public void addDotsIndicator(int pos) {
        mTutLinear.removeAllViews();
        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(getApplicationContext());
            mdots[i].setText("     " + (i + 1));
            mdots[i].setTextSize(25);
            mdots[i].setTextColor(Color.GRAY);
            mTutLinear.addView(mdots[i]);
        }
        if (mdots.length > 0) {
            mdots[pos].setTextColor(Color.WHITE);
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mSlideView.startAnimation(fade_in);
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            if (position == mdots.length - 1) {
                mButton.setEnabled(true);
                mButton.setVisibility(View.VISIBLE);
                mButton.setText("Let's Get Started!");
                mButton.setBackgroundResource(R.drawable.my_buttonborder_drawable_highlight);
            } else {
                mButton.setEnabled(false);
                mButton.setVisibility(View.INVISIBLE);
                mButton.setBackgroundResource(R.drawable.my_buttonborder_drawable);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
