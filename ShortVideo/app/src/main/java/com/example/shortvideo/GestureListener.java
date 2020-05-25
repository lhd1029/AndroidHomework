package com.example.shortvideo;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    GestureListener()
    {

    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (VideoActivity.vactivity.videoView.isPlaying()){
            VideoActivity.vactivity.videoView.pause();
        }else {
            VideoActivity.vactivity.videoView.start();
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast toast=Toast.makeText(VideoActivity.vactivity, "666", Toast.LENGTH_SHORT);
        //显示toast信息
        toast.show();
        VideoActivity.vactivity.imageView.setImageResource(R.drawable.love);//
        VideoActivity.vactivity.imageView.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
