package com.benja.blitzzer.onevshundred;

import java.util.Collections;
import java.util.List;

public class QuestionBank {
    private List<com.benja.blitzzer.onevshundred.Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<com.benja.blitzzer.onevshundred.Question>questionList) {
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList);
        mNextQuestionIndex = 0;
    }

    public com.benja.blitzzer.onevshundred.Question getQuestion(){
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }
        return mQuestionList.get(mNextQuestionIndex++);
    }
}
