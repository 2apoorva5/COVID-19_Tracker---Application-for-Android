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

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import maes.tech.intentanim.CustomIntent;

public class OverviewActivity extends AppCompatActivity {

    ImageView back, logo, covid;
    TextView heading;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        back = findViewById(R.id.arrow_back);
        logo = findViewById(R.id.logo);
        heading = findViewById(R.id.heading);
        covid = findViewById(R.id.covid_image);

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

        String text = "COVID-19OVERVIEW";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        spannableString.setSpan(foregroundColorSpan, 8, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        heading.setText(spannableString);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url = "https://www.who.int/images/default-source/health-topics/coronavirus/corona-virus-getty.tmb-1200v.jpg?Culture=en&sfvrsn=217a6a68_24";

        Glide.with(OverviewActivity.this).load(url).into(covid);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_statistics:
                        startActivity(new Intent(OverviewActivity.this, StatisticsActivity.class));
                        CustomIntent.customType(OverviewActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_overview:
                        break;

                    case R.id.menu_symptoms:
                        startActivity(new Intent(OverviewActivity.this, SymptomsActivity.class));
                        CustomIntent.customType(OverviewActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_prevention:
                        startActivity(new Intent(OverviewActivity.this, PreventionActivity.class));
                        CustomIntent.customType(OverviewActivity.this, "fadein-to-fadeout");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OverviewActivity.this, StatisticsActivity.class));
        CustomIntent.customType(OverviewActivity.this, "fadein-to-fadeout");
    }
}
