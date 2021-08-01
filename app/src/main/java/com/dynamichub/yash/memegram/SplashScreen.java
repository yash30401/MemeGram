package com.dynamichub.yash.memegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView memeGramTitle,createdBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        memeGramTitle=findViewById(R.id.memeGramTitle);
        createdBy=findViewById(R.id.createdBy);

        Animation aniFade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        memeGramTitle.startAnimation(aniFade);
        createdBy.startAnimation(aniFade);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashScreen.this,Categories.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}