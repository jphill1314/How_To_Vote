package com.example.jphil.how_to_vote;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    String[] questions, policyCat, examples;
    int questionNum, policyNum, totalQs;
    int[] sAns, eAns, fAns;

    Toolbar tb;
    RadioButton nButton, saButton, aButton, dButton, sdButton;
    TextView tvQuestion, tvExample;
    FloatingActionButton fabPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("For whatever reason I have to do this in order to change the title later");
        if(tb != null){
            setSupportActionBar(tb);

            if(getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        saButton = (RadioButton) findViewById(R.id.s_agree);
        aButton = (RadioButton) findViewById(R.id.agree);
        nButton = (RadioButton) findViewById(R.id.neutral);
        dButton = (RadioButton) findViewById(R.id.disagree);
        sdButton = (RadioButton) findViewById(R.id.s_disagree);

        tvQuestion = (TextView) findViewById(R.id.question);
        tvExample = (TextView) findViewById(R.id.examples_textview);

        fabPrev = (FloatingActionButton) findViewById(R.id.fab_prev);

        policyCat = getResources().getStringArray(R.array.policy_categories);
        policyNum = 0;

        loadPolicyCat(policyNum, true);
    }

    private void loadPolicyCat(int catNum, boolean atStart){
        String cat = policyCat[catNum];
        tb.setTitle(cat);

        switch (cat){
            case "Social Policy":
                questions = getResources().getStringArray(R.array.social_policy_questions);
                examples = getResources().getStringArray(R.array.social_policy_examples);
                if(sAns == null){
                    sAns = initAnswers(questions.length);
                }
                break;
            case "Economic Policy":
                questions = getResources().getStringArray(R.array.economic_policy_questions);
                examples = getResources().getStringArray(R.array.economic_policy_examples);
                if(eAns == null){
                    eAns = initAnswers(questions.length);
                }
                break;
            case "Foreign Policy":
                questions = getResources().getStringArray(R.array.foreign_policy_question);
                examples = getResources().getStringArray(R.array.foreign_policy_examples);
                if(fAns == null){
                    fAns = initAnswers(questions.length);
                }
                break;
        }

        totalQs = questions.length;
        if(atStart) {
            questionNum = -1;
        }
        else{
            questionNum = totalQs - 2;
        }
        loadNextQuestion();
    }

    private int[] initAnswers(int length){
        int[] ans = new int[length];
        for(int x = 0; x < length; x++){
            ans[x] = 0;
        }
        return ans;
    }

    public void onRadioButtonClicked(View view){
        switch (view.getId()){
            case R.id.s_agree:
                saveAnswer(2);
                break;
            case R.id.agree:
                saveAnswer(1);
                break;
            case R.id.neutral:
                saveAnswer(0);
                break;
            case R.id.disagree:
                saveAnswer(-1);
                break;
            case R.id.s_disagree:
                saveAnswer(-2);
                break;
        }
    }

    private void saveAnswer(int value){
        switch (policyNum){
            case 0:
                sAns[questionNum] = value;
                break;
            case 1:
                eAns[questionNum] = value;
                break;
            case 2:
                fAns[questionNum] = value;
                break;
        }
    }

    public void onFabClicked(View view){
        switch (view.getId()){
            case R.id.fab_next:
                loadNextQuestion();
                break;
            case R.id.fab_prev:
                loadPreviousQuestion();
                break;
        }
    }

    private void loadNextQuestion(){
        if(questionNum < totalQs - 1) {
            tvQuestion.setText(questions[++questionNum]);
            tvExample.setText(examples[questionNum]);
        }
        else{
            if(policyNum < policyCat.length - 1) {
                loadPolicyCat(++policyNum, true);
            }
            else{
                Intent i = new Intent(this, QuizResultsActivity.class);
                i.putExtra(QuizResultsActivity.SOCIAL_KEY, sAns);
                i.putExtra(QuizResultsActivity.ECON_KEY, eAns);
                i.putExtra(QuizResultsActivity.FOREIGN_KEY, fAns);
                startActivity(i);
            }
        }

        switch (policyNum){
            case 0:
                loadAnswer(sAns, questionNum);
                break;
            case 1:
                loadAnswer(eAns, questionNum);
                break;
            case 2:
                loadAnswer(fAns, questionNum);
                break;
        }

        if(fabPrev.getVisibility() == View.GONE && questionNum != 0){
            fabPrev.setVisibility(View.VISIBLE);
        }
    }

    private void loadAnswer(int[] ans, int index){
        switch (ans[index]){
            case 2:
                saButton.setChecked(true);
                break;
            case 1:
                aButton.setChecked(true);
                break;
            case -1:
                dButton.setChecked(true);
                break;
            case -2:
                sdButton.setChecked(true);
                break;
            default:
                nButton.setChecked(true);
        }
    }

    private void loadPreviousQuestion(){
        if(questionNum > 0){
            tvQuestion.setText(questions[--questionNum]);
            tvExample.setText(examples[questionNum]);

            if(questionNum == 0 && policyNum == 0){
                fabPrev.setVisibility(View.GONE);
            }
        }
        else{
            if(policyNum > 0) {
                loadPolicyCat(--policyNum, false);
            }
            else{
                return;
            }
        }

        switch (policyNum){
            case 0:
                loadAnswer(sAns, questionNum);
                break;
            case 1:
                loadAnswer(eAns, questionNum);
                break;
            case 2:
                loadAnswer(fAns, questionNum);
                break;
        }
    }
}
