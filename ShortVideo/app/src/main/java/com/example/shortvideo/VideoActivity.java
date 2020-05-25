package com.example.shortvideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    private VideoView videoView;
    private SeekBar seekBar;
    private int sec;
    private boolean isStart;
    private int orientation;
    private boolean paused=false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        orientation = getResources().getConfiguration().orientation;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制固定屏幕方向
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_video);
            videoView = findViewById(R.id.videoView);
            videoView.setVideoPath(url);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            videoView.setLayoutParams(layoutParams);

            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (videoView.isPlaying()){
                        videoView.pause();
                    }else {
                        videoView.start();
                    }
                    return false;
                }
            });
//        }
//        else {
//        }

//        MediaController mc = new MediaController(VideoActivity.this);
//        videoView.setMediaController(mc);

        if (savedInstanceState != null){
            Log.d("saved", "success");

            int sec1 = (int) savedInstanceState.getLong("time");
            Log.d("saved", String.valueOf(sec1));
            videoView.seekTo(sec1);
            boolean isStart1 = savedInstanceState.getBoolean("play");
            if (isStart1 == true) {
                videoView.start();
            }
        }
        else//修改！视频点开后自动播放
        {
            videoView.start();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("time", sec);
        outState.putBoolean("play", isStart);
        Log.d("save", String.valueOf(sec));
        Log.d("save", String.valueOf(isStart));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView = findViewById(R.id.videoView);
        sec = videoView.getCurrentPosition();
        isStart = videoView.isPlaying();
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}
