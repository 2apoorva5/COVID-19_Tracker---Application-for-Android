package com.application.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import maes.tech.intentanim.CustomIntent;

public class WhenHowActivity extends AppCompatActivity {

    ImageView back, logo, image1, image2, image3, image4, image5, image6, image7, image8, image9, image10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_when_how);

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
        image10 = findViewById(R.id.image10);

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

        String url1 = "https://www.who.int/images/default-source/departments/epi-win/how-to-use-mask-v0-1-digital.tmb-1920v.png?sfvrsn=c5bd5d00_2";
        String url2 = "https://www.who.int/images/default-source/departments/epi-win/when-to-use-a-mask-v0-1-digital.tmb-1920v.png?sfvrsn=58574bf9_2";
        String url3 = "https://www.who.int/images/default-source/health-topics/coronavirus/risk-communications/general-public/protect-yourself/infographics/masks-infographic---final.tmb-1920v.png?sfvrsn=c205429_1";
        String url4 = "https://www.who.int/images/default-source/health-topics/coronavirus/masks/masks-1.tmb-1920v.png?sfvrsn=38becf2f_12";
        String url5 = "https://www.who.int/images/default-source/health-topics/coronavirus/masks/masks-2.tmb-1920v.png?Culture=en&sfvrsn=50396714_12";
        String url6 = "https://www.who.int/images/default-source/health-topics/coronavirus/masks/masks-3.tmb-1920v.png?sfvrsn=15ef394a_12";
        String url7 = "https://www.who.int/images/default-source/health-topics/coronavirus/masks/masks-4.tmb-1920v.png?sfvrsn=72f32eb7_12";
        String url8 = "https://www.who.int/images/default-source/health-topics/coronavirus/masks/masks-5.tmb-1920v.png?sfvrsn=7e7ebd8f_12";
        String url9 = "https://www.who.int/images/default-source/health-topics/coronavirus/masks/masks-6.tmb-1920v.png?sfvrsn=39af642d_12";
        String url10 = "https://www.who.int/images/default-source/health-topics/coronavirus/masks/masks-7.tmb-1920v.png?sfvrsn=cd296145_12";

        Glide.with(WhenHowActivity.this).load(url1).into(image1);
        Glide.with(WhenHowActivity.this).load(url2).into(image2);
        Glide.with(WhenHowActivity.this).load(url3).into(image3);
        Glide.with(WhenHowActivity.this).load(url4).into(image4);
        Glide.with(WhenHowActivity.this).load(url5).into(image5);
        Glide.with(WhenHowActivity.this).load(url6).into(image6);
        Glide.with(WhenHowActivity.this).load(url7).into(image7);
        Glide.with(WhenHowActivity.this).load(url8).into(image8);
        Glide.with(WhenHowActivity.this).load(url9).into(image9);
        Glide.with(WhenHowActivity.this).load(url10).into(image10);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(WhenHowActivity.this, "up-to-bottom");
    }
}
