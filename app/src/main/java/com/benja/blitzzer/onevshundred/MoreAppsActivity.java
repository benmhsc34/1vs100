package com.benja.blitzzer.onevshundred;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;

public class MoreAppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_apps);

        CardView ikq = findViewById(R.id.ikq);
        ikq.setCardElevation(25);
        ikq.setOnClickListener(v -> {
            // OPEN LINK
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());

    }
}
