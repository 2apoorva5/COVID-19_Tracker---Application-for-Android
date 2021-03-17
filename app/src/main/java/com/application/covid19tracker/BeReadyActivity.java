package com.application.covid19tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import maes.tech.intentanim.CustomIntent;

public class BeReadyActivity extends AppCompatActivity {

    ImageView back, logo, image1, image2, image3, image4, image5, image6, image7, image8, image9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_ready);

        back = findViewById(R.id.arrow_back);
        logo = findViewById(R.id.logo);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        YoYo.with(Techniques.Flash)
                .duration(1000)
                .repeat(1)
                .playOn(logo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url1 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-3.tmb-1920v.jpg?sfvrsn=1706a18f_24";
        String url2 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-2.tmb-1920v.jpg?sfvrsn=28a6f92d_4";
        String url3 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-ready-social-1.tmb-1920v.jpg?sfvrsn=c81745a7_4";
        String url4 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-smart-if-you-develop.tmb-1920v.jpg?sfvrsn=1486258a_21";
        String url5 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-smart-inform.tmb-1920v.jpg?sfvrsn=f6dbe358_24";
        String url6 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-safe.tmb-1920v.jpg?sfvrsn=1f6e4aef_24";
        String url7 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-support.tmb-1920v.jpg?sfvrsn=1856f2a3_25";
        String url8 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-address-stigma.tmb-1920v.jpg?sfvrsn=4615bfbe_24";
        String url9 = "https://www.who.int/images/default-source/health-topics/coronavirus/social-media-squares/be-kind-to-address-fear.tmb-1920v.jpg?sfvrsn=a8e99f14_24";

        Glide.with(BeReadyActivity.this).load(url1).into(image1);
        Glide.with(BeReadyActivity.this).load(url2).into(image2);
        Glide.with(BeReadyActivity.this).load(url3).into(image3);
        Glide.with(BeReadyActivity.this).load(url4).into(image4);
        Glide.with(BeReadyActivity.this).load(url5).into(image5);
        Glide.with(BeReadyActivity.this).load(url6).into(image6);
        Glide.with(BeReadyActivity.this).load(url7).into(image7);
        Glide.with(BeReadyActivity.this).load(url8).into(image8);
        Glide.with(BeReadyActivity.this).load(url9).into(image9);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(BeReadyActivity.this, "up-to-bottom");
    }
}
