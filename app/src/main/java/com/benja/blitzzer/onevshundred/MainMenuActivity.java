package com.benja.blitzzer.onevshundred;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button playButton = findViewById(R.id.playButton);
        Button statsButton = findViewById(R.id.statsButton);
        Button moreAppsButton = findViewById(R.id.moreAppsButton);


        playButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainMenuActivity.this, com.benja.blitzzer.onevshundred.QuestionsActivity.class);
            startActivity(myIntent);
        });

        statsButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainMenuActivity.this, com.benja.blitzzer.onevshundred.StatsActivity.class);
            startActivity(myIntent);
        });

        moreAppsButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainMenuActivity.this, com.benja.blitzzer.onevshundred.MoreAppsActivity.class);
            startActivity(myIntent);
        });
    }
}
