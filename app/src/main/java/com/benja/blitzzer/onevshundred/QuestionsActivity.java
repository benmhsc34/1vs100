package com.benja.blitzzer.onevshundred;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;


    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = QuestionsActivity.class.getCanonicalName().concat("BUNDLE_EXTRA_SCORE");
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";
    public static final String BUNDLE_EXTRA_NAME = "currentName";

    private CollectionReference notebookRef = FirebaseFirestore.getInstance().collection("Question");

    int remaining = 100;


    private boolean mEnableTouchEvents;
    List<Question> questionList = new ArrayList<>();
    String name;

    TextView questionNumber;
    int questionNumero = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        FirebaseApp.initializeApp(this);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences mPreferences = getSharedPreferences("SHARED", MODE_PRIVATE);
        mPreferences.edit().putInt("oldRemaining", 100).apply();

        questionNumber = findViewById(R.id.questionNumber);

        Intent intent = getIntent();
        int number = intent.getIntExtra("countryFlag", 0);
        String str = intent.getStringExtra("countryName");

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> onBackPressed());


        ImageView imageView = findViewById(R.id.imageViewOfFlag);
        TextView textView = findViewById(R.id.textViewOfCountry);

        String language = prefs.getString("language", "English");

        imageView.setImageResource(number);
        textView.setText(str);

        notebookRef.get().addOnSuccessListener((queryDocumentSnapshots) -> {

            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                Question theQuestion = new Question((String) documentSnapshot.get("question"), Arrays.asList((String) documentSnapshot.get("answer0"),(String) documentSnapshot.get("answer1"),(String) documentSnapshot.get("answer2"),(String) documentSnapshot.get("answer3")),Integer.valueOf((String) documentSnapshot.get("answerIndex")), Integer.valueOf((String) documentSnapshot.get("pourcentage")));
                questionList.add(theQuestion);
            }
            mQuestionBank = new QuestionBank(questionList);

            if (savedInstanceState != null) {
                mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
                mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            } else {
                mScore = 0;
                mNumberOfQuestions = 100;
            }

            mEnableTouchEvents = true;

            // Wire widgets
            mQuestionTextView = findViewById(R.id.questionTextView);
            mAnswerButton1 = findViewById(R.id.activity_game_answer1_btn);
            mAnswerButton2 = findViewById(R.id.activity_game_answer2_btn);
            mAnswerButton3 = findViewById(R.id.activity_game_answer3_btn);
            mAnswerButton4 = findViewById(R.id.activity_game_answer4_btn);

            // Use the tag property to 'name' the buttons
            mAnswerButton1.setTag(0);
            mAnswerButton2.setTag(1);
            mAnswerButton3.setTag(2);
            mAnswerButton4.setTag(3);

            mAnswerButton1.setOnClickListener(this);
            mAnswerButton2.setOnClickListener(this);
            mAnswerButton3.setOnClickListener(this);
            mAnswerButton4.setOnClickListener(this);

            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onClick(View v) {
        final int responseIndex = (int) v.getTag();

        if (mCurrentQuestion.getAnswerIndex() == 0) {
            mAnswerButton1.setBackgroundResource(R.drawable.rounder_cornes_questions_green);
            mAnswerButton1.setTextColor(Color.WHITE);
        } else if (mCurrentQuestion.getAnswerIndex() == 1) {
            mAnswerButton2.setBackgroundResource(R.drawable.rounder_cornes_questions_green);
            mAnswerButton2.setTextColor(Color.WHITE);
        } else if (mCurrentQuestion.getAnswerIndex() == 2) {
            mAnswerButton3.setBackgroundResource(R.drawable.rounder_cornes_questions_green);
            mAnswerButton3.setTextColor(Color.WHITE);
        } else if (mCurrentQuestion.getAnswerIndex() == 3) {
            mAnswerButton4.setBackgroundResource(R.drawable.rounder_cornes_questions_green);
            mAnswerButton4.setTextColor(Color.WHITE);
        }

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            // Good answer diminuer nombre restant
            if (remaining > 10) {

                remaining = remaining - ((mCurrentQuestion.getPercentage() * remaining) / 100);
            } else {
                Random r = new Random();
                int i1 = r.nextInt(remaining + 1);
                remaining = i1;

            }
            SharedPreferences mPreferences = getSharedPreferences("SHARED", MODE_PRIVATE);
            mPreferences.edit().putInt("remaining", remaining).apply();

            Intent myIntent = new Intent(QuestionsActivity.this, com.benja.blitzzer.onevshundred.CorrectActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(myIntent);
        } else {


            if (responseIndex == 0) {
                mAnswerButton1.setBackgroundResource(R.drawable.rounder_cornes_questions_red);
                mAnswerButton1.setTextColor(Color.WHITE);

            } else if (responseIndex == 1) {
                mAnswerButton2.setBackgroundResource(R.drawable.rounder_cornes_questions_red);
                mAnswerButton2.setTextColor(Color.WHITE);

            } else if (responseIndex == 2) {
                mAnswerButton3.setBackgroundResource(R.drawable.rounder_cornes_questions_red);
                mAnswerButton3.setTextColor(Color.WHITE);

            } else if (responseIndex == 3) {
                mAnswerButton4.setBackgroundResource(R.drawable.rounder_cornes_questions_red);
                mAnswerButton4.setTextColor(Color.WHITE);

            }

            endGame();
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putString("winorlose", "lose").apply();
            prefs.edit().putInt("remaining", remaining).apply();

            int bestPosition = prefs.getInt("bestPosition", 100);
            if (bestPosition > remaining) {
                prefs.edit().putInt("bestPosition", remaining).apply();
            }
            int numberOfGames = prefs.getInt("numberOfGames", 0);
            prefs.edit().putInt("numberOfGames", numberOfGames + 1).apply();

            Intent myIntent = new Intent(QuestionsActivity.this, com.benja.blitzzer.onevshundred.ResultActivity.class);
            startActivity(myIntent);

            // Wrong answer
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(() -> {
            mEnableTouchEvents = true;

            // If this is the last question, ends the game.
            // Else, display the next question.
            if (remaining == 0) {
                // End the game
                endGame();
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putString("winorlose", "win").apply();
                int numberOfWins = prefs.getInt("numberOfWins", 0);
                int numberOfGames = prefs.getInt("numberOfGames", 0);
                prefs.edit().putInt("numberOfWins", numberOfWins + 1).apply();
                prefs.edit().putInt("numberOfGames", numberOfGames + 1).apply();

                Intent myIntent = new Intent(QuestionsActivity.this, com.benja.blitzzer.onevshundred.ResultActivity.class);
                startActivity(myIntent);
            } else {
                questionNumero++;
                mCurrentQuestion = mQuestionBank.getQuestion();
                questionNumber.setText(remaining + " remaining");
                displayQuestion(mCurrentQuestion);
                mAnswerButton4.setBackgroundResource(R.drawable.rounder_cornes_questions1);
                mAnswerButton3.setBackgroundResource(R.drawable.rounder_cornes_questions1);
                mAnswerButton2.setBackgroundResource(R.drawable.rounder_cornes_questions1);
                mAnswerButton1.setBackgroundResource(R.drawable.rounder_cornes_questions1);
                mAnswerButton4.setTextColor(Color.parseColor("#696969"));
                mAnswerButton3.setTextColor(Color.parseColor("#696969"));
                mAnswerButton2.setTextColor(Color.parseColor("#696969"));
                mAnswerButton1.setTextColor(Color.parseColor("#696969"));


            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private void endGame() {
        Intent intent = getIntent();

        finish();


    }


    private void displayQuestion(final Question question) {
        mAnswerButton1.setEnabled(false);
        mAnswerButton2.setEnabled(false);
        mAnswerButton3.setEnabled(false);
        mAnswerButton4.setEnabled(false);

        setQuestionSlowly(question.getQuestion());


        mAnswerButton1.setText("");
        mAnswerButton2.setText("");
        mAnswerButton3.setText("");
        mAnswerButton4.setText("");

        final Handler handler = new Handler();
        handler.postDelayed(() -> mAnswerButton1.setText(question.getChoiceList().get(0).trim()), 3000);
        handler.postDelayed(() -> mAnswerButton4.setText(question.getChoiceList().get(3).trim()), 3500);
        handler.postDelayed(() -> mAnswerButton3.setText(question.getChoiceList().get(2).trim()), 4000);
        handler.postDelayed(() -> {
                    mAnswerButton2.setText(question.getChoiceList().get(1).trim());
                    mAnswerButton1.setEnabled(true);
                    mAnswerButton2.setEnabled(true);
                    mAnswerButton3.setEnabled(true);
                    mAnswerButton4.setEnabled(true);
                }
                , 4500);


    }


    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {

        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }


    public void setQuestionSlowly(final String s) {
        TextView tv = findViewById(R.id.questionTextView);
        tv.setText("");
        final int[] i = new int[1];
        final int length = s.length();
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                char c = s.charAt(i[0]);
                Log.d("Strange", "" + c);
                tv.append(String.valueOf(c));
                i[0]++;
            }
        };

        final Timer timer = new Timer();
        TimerTask taskEverySplitSecond = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                if (i[0] == length - 1) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(taskEverySplitSecond, 500, 50);
    }
}
