package com.application.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import maes.tech.intentanim.CustomIntent;

public class AskWHOActivity extends AppCompatActivity {

    ImageView back, logo, image1, image2, image3, image4, image5, image6, image7, image8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_who);

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

        String url1 = "https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/safe-greetings.png?sfvrsn=2e97004e_2";
        String url2 = "https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/handshaking.png?sfvrsn=4aed53c5_2";
        String url3 = "https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/wearing-gloves.png?sfvrsn=ec69b46a_2";
        String url4 = "https://www.who.int/images/default-source/health-topics/coronavirus/eng-mythbusting-ncov-(19).tmb-1920v.png";
        String url5 = "https://www.who.int/images/default-source/health-topics/coronavirus/eng-mythbusting-ncov-(13).tmb-1920v.png?sfvrsn=d2a2dc01_1";
        String url6 = "https://www.who.int/images/default-source/health-topics/coronavirus/eng-mythbusting-ncov-(23).tmb-1920v.png?sfvrsn=b399c676_1";
        String url7 = "https://www.who.int/images/default-source/health-topics/coronavirus/eng-mythbusting-ncov-(30).tmb-1920v.png?sfvrsn=c0e196fb_1";
        String url8 = "https://www.who.int/images/default-source/health-topics/coronavirus/eng-mythbusting-ncov-(33).tmb-1920v.png?sfvrsn=a54904b3_1";

        Glide.with(AskWHOActivity.this).load(url1).into(image1);
        Glide.with(AskWHOActivity.this).load(url2).into(image2);
        Glide.with(AskWHOActivity.this).load(url3).into(image3);
        Glide.with(AskWHOActivity.this).load(url4).into(image4);
        Glide.with(AskWHOActivity.this).load(url5).into(image5);
        Glide.with(AskWHOActivity.this).load(url6).into(image6);
        Glide.with(AskWHOActivity.this).load(url7).into(image7);
        Glide.with(AskWHOActivity.this).load(url8).into(image8);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(AskWHOActivity.this, "up-to-bottom");
    }
}
