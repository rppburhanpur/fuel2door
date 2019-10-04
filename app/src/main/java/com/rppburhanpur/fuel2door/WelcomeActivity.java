package com.rppburhanpur.fuel2door;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.PrintStream;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PersonalViewPagerAdapater personalViewPagerAdapater;
    //private LinearLayout dotsLayout;
    //private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private WelcomeActivityChecker welcomeActivityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        welcomeActivityChecker = new WelcomeActivityChecker(this);
        if(!welcomeActivityChecker.isFirstTimeLaunch()){
            lauchHomeScreen();
            finish();
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btnSkip = (Button) findViewById(R.id.skip_button);
        btnNext = (Button) findViewById(R.id.next_button);

        layouts = new int[]{R.layout.welcome_slider1,R.layout.welcome_slider2,R.layout.welcome_slider3};

         personalViewPagerAdapater = new PersonalViewPagerAdapater();

        viewPager.setAdapter(personalViewPagerAdapater);

        viewPager.addOnPageChangeListener(onPageChangeListener);


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lauchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(+1);
                if(current < layouts.length){
                    viewPager.setCurrentItem(current);
                }else {
                    lauchHomeScreen();
                }
            }
        });

    }
    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    private void lauchHomeScreen(){
        welcomeActivityChecker.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this,User_Registration.class));
        finish();
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == layouts.length-1){
                btnNext.setText("Got it");
                btnSkip.setVisibility(View.GONE);
            }else {
                btnNext.setText("Next");
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    class PersonalViewPagerAdapater extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public PersonalViewPagerAdapater() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
