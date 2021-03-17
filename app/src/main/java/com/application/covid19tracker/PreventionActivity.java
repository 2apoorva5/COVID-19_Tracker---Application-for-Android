package com.application.covid19tracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import maes.tech.intentanim.CustomIntent;

public class PreventionActivity extends AppCompatActivity {

    ImageView back, logo;
    TextView heading, link1, link2, link3, link4;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention);

        back = findViewById(R.id.arrow_back);
        logo = findViewById(R.id.logo);
        heading = findViewById(R.id.heading);
        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);
        link3 = findViewById(R.id.link3);
        link4 = findViewById(R.id.link4);

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

        String text = "COVID-19PREVENTION";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        spannableString.setSpan(foregroundColorSpan, 8, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        heading.setText(spannableString);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreventionActivity.this, AskWHOActivity.class));
                CustomIntent.customType(PreventionActivity.this, "bottom-to-up");
            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreventionActivity.this, BeReadyActivity.class));
                CustomIntent.customType(PreventionActivity.this, "bottom-to-up");
            }
        });

        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreventionActivity.this, WhenHowActivity.class));
                CustomIntent.customType(PreventionActivity.this, "bottom-to-up");
            }
        });

        link4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreventionActivity.this, MythBustersActivity.class));
                CustomIntent.customType(PreventionActivity.this, "bottom-to-up");
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_statistics:
                        startActivity(new Intent(PreventionActivity.this, StatisticsActivity.class));
                        CustomIntent.customType(PreventionActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_overview:
                        startActivity(new Intent(PreventionActivity.this, OverviewActivity.class));
                        CustomIntent.customType(PreventionActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_symptoms:
                        startActivity(new Intent(PreventionActivity.this, SymptomsActivity.class));
                        CustomIntent.customType(PreventionActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_prevention:
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(PreventionActivity.this, StatisticsActivity.class));
        CustomIntent.customType(PreventionActivity.this, "fadein-to-fadeout");
    }
}
