package com.example.enzo.a1vs100;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CorrectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);

        TextView numberRemainingTV = findViewById(R.id.numberRemaining);

        SharedPreferences mPrefs = getSharedPreferences("SHARED", MODE_PRIVATE);
        int remaining = mPrefs.getInt("remaining", 200);
        int oldRemaining = mPrefs.getInt("oldRemaining", 100);

        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(oldRemaining, remaining);
        animator.addUpdateListener(animation -> numberRemainingTV.setText(String.valueOf(animation.getAnimatedValue())));
        animator.setEvaluator((TypeEvaluator<Integer>) (fraction, startValue, endValue) -> Math.round(startValue + (endValue - startValue) * fraction));
        animator.setDuration(1000);
        animator.start();

        mPrefs.edit().putInt("oldRemaining", remaining).apply();
        if (remaining == 0) {
            SharedPreferences mPreferences = getSharedPreferences("SHARED", MODE_PRIVATE);
            mPreferences.edit().putInt("oldRemaining", 100).apply();
        }

        int TIME_OUT = 2000;
        new Handler().postDelayed(this::finish, TIME_OUT);
    }

}
