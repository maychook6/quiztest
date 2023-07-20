package com.example.quizkids2.splash;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.quizkids2.main.MainActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;

import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizkids2.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView welcome = findViewById(R.id.welcomeSplash);
        TextView quizkids = findViewById(R.id.quizkids);
        ImageView trivia = findViewById(R.id.trivia);


        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                startActivity( new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }.start();


        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(welcome);

        quizkids.setVisibility(View.INVISIBLE);
        quizkids.postDelayed(() -> quizkids.setVisibility(View.VISIBLE), 1500);
        YoYo.with(Techniques.FlipInX)
                .duration(1500)
                .repeat(1)
                .playOn(quizkids);

        trivia.setVisibility(View.INVISIBLE);
        trivia.postDelayed(() -> trivia.setVisibility(View.VISIBLE), 3000);
        YoYo.with(Techniques.ZoomInDown)
                .duration(2000)
                .repeat(1)
                .playOn(trivia);
    }
}
