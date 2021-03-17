package com.application.covid19tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import maes.tech.intentanim.CustomIntent;

public class MythBustersActivity extends AppCompatActivity {

    ImageView back, logo, image1, image2, image3, image4, image5, image6, image7, image8, image9, image10,
            image11, image12, image13, image14, image15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myth_busters);

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
        image11 = findViewById(R.id.image11);
        image12 = findViewById(R.id.image12);
        image13 = findViewById(R.id.image13);
        image14 = findViewById(R.id.image14);
        image15 = findViewById(R.id.image15);

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

        String url1 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/eng-mythbusting-ncov-(15).tmb-1920v.png?sfvrsn=a8b9e94_1";
        String url2 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-sun-exposure.tmb-1920v.jpg?sfvrsn=658ce588_4";
        String url3 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-recovery.tmb-1920v.jpg?sfvrsn=1404cfd0_4";
        String url4 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-breathing-exercice.tmb-1920v.jpg?sfvrsn=db06f4a9_4";
        String url5 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-alcohol.tmb-1920v.jpg?sfvrsn=19ea13fb_4";
        String url6 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/52.tmb-1920v.png?sfvrsn=862374e_4";
        String url7 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-cold-snow.tmb-1920v.png?sfvrsn=1e557ba_4";
        String url8 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-hot-bath.tmb-1920v.png?sfvrsn=f1ebbc_4";
        String url9 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mb-mosquito-bite.tmb-1920v.png?sfvrsn=a1d90f6_4";
        String url10 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbusters-27.tmb-1920v.png?sfvrsn=d17bc6bb_7";
        String url11 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/mythbusters-31.tmb-1920v.png?sfvrsn=e5989655_4";
        String url12 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbusters-25.tmb-1920v.png?sfvrsn=d3bf829c_8";
        String url13 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbusters-33.tmb-1920v.png?sfvrsn=47bfd0aa_8";
        String url14 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/mythbuster-3.tmb-1920v.png?sfvrsn=10657e42_12";
        String url15 = "https://www.who.int/images/default-source/health-topics/coronavirus/myth-busters/web-mythbusters/mythbuster-4.tmb-1920v.png?sfvrsn=e163bada_12";

        Glide.with(MythBustersActivity.this).load(url1).into(image1);
        Glide.with(MythBustersActivity.this).load(url2).into(image2);
        Glide.with(MythBustersActivity.this).load(url3).into(image3);
        Glide.with(MythBustersActivity.this).load(url4).into(image4);
        Glide.with(MythBustersActivity.this).load(url5).into(image5);
        Glide.with(MythBustersActivity.this).load(url6).into(image6);
        Glide.with(MythBustersActivity.this).load(url7).into(image7);
        Glide.with(MythBustersActivity.this).load(url8).into(image8);
        Glide.with(MythBustersActivity.this).load(url9).into(image9);
        Glide.with(MythBustersActivity.this).load(url10).into(image10);
        Glide.with(MythBustersActivity.this).load(url11).into(image11);
        Glide.with(MythBustersActivity.this).load(url12).into(image12);
        Glide.with(MythBustersActivity.this).load(url13).into(image13);
        Glide.with(MythBustersActivity.this).load(url14).into(image14);
        Glide.with(MythBustersActivity.this).load(url15).into(image15);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(MythBustersActivity.this, "up-to-bottom");
    }
}
