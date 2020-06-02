package com.example.shortvideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    public VideoView videoView; //此处作了修改，将videoview设为public，用于GestureListener类调用
    public ImageView imageView; //此处作了修改，新增爱心图标
    private SeekBar seekBar;
    private int sec;
    private boolean isStart;
    private int orientation;
    private boolean paused=false;
    public static VideoActivity vactivity;//存储本activity，用于GestureListener类调用
    private GestureDetector gestureDetector;

    public VideoActivity() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vactivity = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制固定屏幕方向

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //布局videoview
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(url);
        imageView = findViewById(R.id.imageView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);

        gestureDetector = new GestureDetector(this, new GestureListener());//启动手势监听

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event); //启动手势监听
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}
