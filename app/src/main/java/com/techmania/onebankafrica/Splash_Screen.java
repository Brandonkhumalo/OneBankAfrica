package com.techmania.onebankafrica;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.techmania.onebankafrica.auth.Login;

public class Splash_Screen extends AppCompatActivity {
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashImage = findViewById(R.id.imageSplashScreen);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.oba_splash);
        splashImage.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_Screen.this, Login.class);
                startActivity(i);
                finish();
            }
        },4000);
    }
}