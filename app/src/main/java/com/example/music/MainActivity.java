package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private Button mButtonPlay;
    private Button mButtonPause;
    private Button mButtonNext;
    private Button mButtonPrevious;
    private MediaPlayer mMediaPlayer;
    private Boolean mResumePlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonPlay = findViewById(R.id.button_play);
        mButtonPause = findViewById(R.id.button_pause);
        mButtonNext = findViewById(R.id.button_next);
        mButtonPrevious = findViewById(R.id.button_previous);

        mButtonPlay.setOnClickListener(this);
        mButtonPause.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mResumePlay = false;
        if (mMediaPlayer != null) {
            mMediaPlayer = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play:
                if (mResumePlay) {
                    resumePlay();
                } else {
                    preparePlay("song");
                }
                mButtonPlay.setVisibility(View.GONE);
                mButtonPause.setVisibility(View.VISIBLE);
                break;
            case R.id.button_pause:
                mResumePlay = true;
                pausePlay();
                mButtonPause.setVisibility(View.GONE);
                mButtonPlay.setVisibility(View.VISIBLE);
                break;
            case R.id.button_next:
                nextPlay("song1");
                mButtonPlay.setVisibility(View.GONE);
                mButtonPause.setVisibility(View.VISIBLE);
                break;
            case R.id.button_previous:
                previousPlay("song2");
                mButtonPlay.setVisibility(View.GONE);
                mButtonPause.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void preparePlay(String nameSong) {
        Log.d(TAG, "preparePlay " + nameSong);
        int songID = this.getResources().getIdentifier(nameSong, "raw", this.getPackageName());
        mMediaPlayer = MediaPlayer.create(this, songID);
        startPlay();
    }

    private void startPlay() {
        Log.d(TAG, "startPlay");
        if (mMediaPlayer == null) {
            Log.d(TAG, "startPlay - MediaPlayer is null");
            return;
        }
        mMediaPlayer.start();
    }

    private void pausePlay() {
        Log.d(TAG, "pausePlay");
        if (mMediaPlayer == null) {
            Log.d(TAG, "pausePlay - MediaPlayer is null");
            return;
        }
        mMediaPlayer.pause();
    }

    private void resumePlay() {
        Log.d(TAG, "resumePlay");
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.start();
    }

    private void nextPlay(String nameSong) {
        Log.d(TAG, "nextPlay");
        if (mMediaPlayer == null) {
            preparePlay(nameSong);
            return;
        }
        mMediaPlayer.stop();
        mMediaPlayer.release();
        preparePlay("song1");
    }

    private void previousPlay(String nameSong) {
        Log.d(TAG, "previousPlay");
        if (mMediaPlayer == null) {
            preparePlay(nameSong);
            return;
        }
        mMediaPlayer.stop();
        mMediaPlayer.release();
        preparePlay("song2");
    }
}
