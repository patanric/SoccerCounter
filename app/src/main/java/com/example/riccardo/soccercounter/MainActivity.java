package com.example.riccardo.soccercounter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String FORMAT = "%02d:%02d:%02d";
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int foulTeamA = 0;
    int foulTeamB = 0;
    CountDownTimer countDownTimer;
    private TextView mTimer;
    private Button mGoalA;
    private Button mGoalB;
    private Button mFoulA;
    private Button mFoulB;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTimer = (TextView) findViewById(R.id.timerTextView);
        mGoalA = (Button) findViewById(R.id.goalA);
        mGoalB = (Button) findViewById(R.id.goalB);
        mFoulA = (Button) findViewById(R.id.foulA);
        mFoulB = (Button) findViewById(R.id.foulB);
        showAlertBox();
    }

    private void showAlertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_text);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (countDownTimer != null) countDownTimer.cancel();
                if (!"".equals(input.getText().toString())) {
                    time = input.getText().toString();
                } else time = "10";
                countDownTimer = new CountDownTimer((Long.parseLong(time) * 1000) + 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimer.setText("" + String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }

                    @Override
                    public void onFinish() {
                        mTimer.setText(R.string.time_over);
                        mGoalA.setEnabled(false);
                        mGoalB.setEnabled(false);
                        mFoulA.setEnabled(false);
                        mFoulB.setEnabled(false);
                    }
                }.start();
            }
        });
        builder.setNegativeButton("Quit app!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void addGoalTeamA(View v) {
        scoreTeamA += 1;
        displayGoalTeamA(scoreTeamA);
    }

    public void addFoulTeamA(View v) {
        foulTeamA += 1;
        displayFoulTeamA(foulTeamA);
    }

    public void displayGoalTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayFoulTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_fouls);
        scoreView.setText(String.valueOf(score));
    }


    public void addThreeTeamB(View v) {
        scoreTeamB += 1;
        displayGoalTeamB(scoreTeamB);
    }

    public void addFoulTeamB(View v) {
        foulTeamB += 1;
        displayFoulTeamB(foulTeamB);
    }

    public void displayGoalTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayFoulTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_fouls);
        scoreView.setText(String.valueOf(score));
    }

    public void restartAll(View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        foulTeamA = 0;
        foulTeamB = 0;
        displayGoalTeamA(scoreTeamA);
        displayGoalTeamB(scoreTeamB);
        displayFoulTeamA(foulTeamA);
        displayFoulTeamB(foulTeamB);
        mGoalA.setEnabled(true);
        mGoalB.setEnabled(true);
        mFoulA.setEnabled(true);
        mFoulB.setEnabled(true);
        showAlertBox();

    }
}
