package com.example.shortvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GetJson gj = new GetJson();
        JSONArray jsonArray = gj.getJson();
//        try {
//            Log.d("json", jsonArray.getJSONObject(0).getString("nickname"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();  // 从json数组中提取元素

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject temp = jsonArray.getJSONObject(i);

                String feedurl = temp.getString("feedurl");
                String nickname = temp.getString("nickname");
                String description = temp.getString("description");
                String likecount = temp.getString("likecount");
                String avatar = temp.getString("avatar");

                VideoInfo video = new VideoInfo(feedurl, nickname, description, likecount, avatar);

                videoInfos.add(video);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ViewPager2 vp = findViewById(R.id.vp);
        vp.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(videoInfos, this);
        vp.setAdapter(viewPagerAdapter);

    }
}



