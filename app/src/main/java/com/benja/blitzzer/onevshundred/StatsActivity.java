package com.benja.blitzzer.onevshundred;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        TextView numberOfWins = findViewById(R.id.numberOfWinsNumberTV);
        TextView numberOfGames = findViewById(R.id.numberOfGamesNumberTV);
        TextView bestPosition = findViewById(R.id.bestPositionNumberTV);
        Button backButton = findViewById(R.id.backButton);


        SharedPreferences mPreferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        int numberOfWinsInt = mPreferences.getInt("numberOfWins", 0);
        int numberOfGamesInt = mPreferences.getInt("numberOfGames", 0);
        int bestPositionInt = mPreferences.getInt("bestPosition", 0);

        numberOfWins.setText(numberOfWinsInt + "");
        numberOfGames.setText(numberOfGamesInt + "");
        bestPosition.setText("#" + bestPositionInt);

        if (numberOfWinsInt != 0) {
            CardView bestPositionLinearLayout = findViewById(R.id.bestPositionLL);
            bestPositionLinearLayout.setVisibility(View.INVISIBLE);
        }

        backButton.setOnClickListener(v -> onBackPressed());
    }
}
