package org.antem.acookingegg;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;

import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    static CountDownTimer countDownTimer = null;
    TextView tvTime;

    SeekBar sb;
    int minutes = 0;
    boolean run = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        sb = findViewById(R.id.mSeekBar);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        sb.setMax(60);



        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (countDownTimer == null) {
                    if (progress >= 0) {
                        minutes = progress;
                        String s = String.valueOf("Set timer for " + minutes+ " Minute's");
                        tvTime.setText(s);

                    }
                }
            }




            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String s="Press to start "+minutes+"minute's timer!";
              tvTime.setText(s);
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDown(minutes);
            }
        });


    }

    private void startCountDown(int minutes) {
            MediaPlayer song = MediaPlayer.create(MainActivity.this, R.raw.moo);
        if (!run&&minutes>=1) {
            run = true;

            countDownTimer = new CountDownTimer(minutes * 1000 * 60, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    NumberFormat f = new DecimalFormat("00");
                    long hour = (millisUntilFinished / 3600000) % 24;
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;

                    setTvTime(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    tvTime.animate().rotationYBy(360f).start();

                }

                @Override
                public void onFinish() {
                    run = false;
                    song.start();

                }
            }.start();


        } else {
            if (countDownTimer != null) {

                song.start();
                run = false;
                countDownTimer.cancel();
                countDownTimer = null;
                String t = "Press to start from Start!";
                tvTime.setText(t);

            }
        }
    }

    private void setTvTime(String time) {
        tvTime.setText(time);
    }

}

