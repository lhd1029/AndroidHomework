package com.example.shortvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int TO_TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();
    }

    private JSONObject getData() {
        URL url;
        HttpsURLConnection httpsURLConnection = null;
        Log.d("debug_my","geturl");
        JSONObject jsonObject = null;
        try {
            url = new URL("https://beiyou.bytedance.com/api/invoke/video/invoke/video");
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpsURLConnection.setReadTimeout(TO_TIMEOUT);
            if (httpsURLConnection.getResponseCode() == 200)
            {
                InputStream is = httpsURLConnection.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = bf.readLine()) != null)
                {
                    System.out.println(line);
                    buffer.append(line);
                }
                bf.close();
                is.close();
                jsonObject = new JSONObject(String.valueOf(buffer));
            }
        } catch (MalformedURLException | ProtocolException e) {
            Log.d("debug_my","get url failed!");
        } catch (UnsupportedEncodingException e) {
            Log.d("debug_my","1");
        } catch (IOException e) {
            Log.d("debug_my","2");
        } catch (JSONException e) {
            Log.d("debug_my","3");
        }
        return jsonObject;
    }
}
