package com.example.shortvideo;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import static java.lang.Thread.sleep;


public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    GestureListener()
    {

    }
    private AlphaAnimation alphaAnimation;
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) //单击暂停
    {
        if (VideoActivity.vactivity.videoView.isPlaying()){
            VideoActivity.vactivity.videoView.pause();
        }else {
            VideoActivity.vactivity.videoView.start();
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) //双击666
    {
        Toast toast=Toast.makeText(VideoActivity.vactivity, "666", Toast.LENGTH_SHORT);
        //显示toast信息
        toast.show();
        //设置弹出图片
        VideoActivity.vactivity.imageView.setImageResource(R.drawable.love);
        VideoActivity.vactivity.imageView.setVisibility(View.VISIBLE);
        //设置弹出淡出效果
        VideoActivity.vactivity.imageView.setAlpha(1.0f);
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        VideoActivity.vactivity.imageView.setAnimation(alphaAnimation);
        alphaAnimation.start();
        VideoActivity.vactivity.imageView.setVisibility(View.INVISIBLE);

        //之前妄图采用lottie动画，但是VideoView和Lottie控件两个图层叠加会crash，参考其官方答疑区
        // https://github.com/airbnb/lottie-android/issues/1252
        //修改json文件后仍然没有能力让lottie控件淡入淡出与VideoView图层，因此放弃lottie，采用基本的imageview
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
