package com.nam.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
     String MILIS_LEFT="keyMillis";
    String RUNNING="keyRunning";
    TextView t1, t2;
    EditText e1;
    ImageView iv1, iv2, iv3;

    static final long START_TIME_IN_MILLIS=61000;
    long timeLeft=START_TIME_IN_MILLIS;
    boolean timerRunning;
    CountDownTimer countDownTimer;
    int mn,sc;
    String timeFormated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);

        e1 = findViewById(R.id.ed1);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv2.setVisibility(View.INVISIBLE);
        iv3.setVisibility(View.INVISIBLE);
        if(savedInstanceState!=null)
        {
            timeLeft=savedInstanceState.getLong(MILIS_LEFT);
            timerRunning=savedInstanceState.getBoolean(RUNNING);

            if(timerRunning) {
                startTimer();
                iv1.setVisibility(View.INVISIBLE);
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
            }else{
                updateCountDownText();
                iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.INVISIBLE);
                iv3.setVisibility(View.INVISIBLE);
            }
        }
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    pauseTimer();

            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
    }

    private void resetTimer() {
        if(e1.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Chưa nhập bài tập",Toast.LENGTH_SHORT).show();
        }else{
        //    timeLeft=START_TIME_IN_MILLIS;
            t1.setText(""+"You spent "+timeFormated+" on "+e1.getText().toString().trim()+" last time");
            pauseTimer();
        }
    }

    private void pauseTimer() {
        countDownTimer.cancel();;
        timerRunning=false;
        iv1.setVisibility(View.VISIBLE);
        iv2.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
        }
    }

    private void startTimer()
    {
        countDownTimer=new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long l) {
                timeLeft=l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning=true;
        iv2.setVisibility(View.VISIBLE);
        iv3.setVisibility(View.VISIBLE);
        iv1.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
         mn=(int)(timeLeft/1000)/60;
         sc=(int)(timeLeft/1000)%60;
         timeFormated=String.format(Locale.getDefault(),"%02d:%02d",mn,sc);
        t2.setText(timeFormated);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(MILIS_LEFT,timeLeft);
        outState.putBoolean(RUNNING,timerRunning);
    }
}