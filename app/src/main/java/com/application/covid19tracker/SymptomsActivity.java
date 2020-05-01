package com.application.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import maes.tech.intentanim.CustomIntent;

public class SymptomsActivity extends AppCompatActivity {

    ImageView back, logo, covid;
    TextView heading;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        back = findViewById(R.id.arrow_back);
        logo = findViewById(R.id.logo);
        heading = findViewById(R.id.heading);
        covid = findViewById(R.id.covid_image);

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

        String text = "COVID-19SYMPTOMS";
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

        String url = "https://s.france24.com/media/display/d83662fc-7030-11ea-878f-005056bf87d6/w:1240/p:16x9/270320-covid-cell-m.webp";

        Glide.with(SymptomsActivity.this).load(url).into(covid);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_statistics:
                        startActivity(new Intent(SymptomsActivity.this, StatisticsActivity.class));
                        CustomIntent.customType(SymptomsActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_overview:
                        startActivity(new Intent(SymptomsActivity.this, OverviewActivity.class));
                        CustomIntent.customType(SymptomsActivity.this, "fadein-to-fadeout");
                        break;

                    case R.id.menu_symptoms:
                        break;

                    case R.id.menu_prevention:
                        startActivity(new Intent(SymptomsActivity.this, PreventionActivity.class));
                        CustomIntent.customType(SymptomsActivity.this, "fadein-to-fadeout");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SymptomsActivity.this, StatisticsActivity.class));
        CustomIntent.customType(SymptomsActivity.this, "fadein-to-fadeout");
    }
}
