package com.example.shortvideo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetJson {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int TO_TIMEOUT = 5000;
    private static final String debug = "debug_my";

    public JSONArray getJson(){
        final JSONArray[] jsonArrayList = new JSONArray[1];
        Thread thread1 = new Thread(new Runnable() {//网络任务需要在子线程中完成
            @Override
            public void run() {
                jsonArrayList[0] = getData();
            }
        });//获取json文件
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
        return jsonArrayList[0];
    }

    private JSONArray getData() {
        URL url;
        HttpsURLConnection httpsURLConnection = null;
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
                String buffer = "";
                String line = "";
                while ((line = bf.readLine()) != null)
                {
                    buffer += line;

                }
                bf.close();
                is.close();
                Log.d(debug, "buffer:" + buffer);
                return new JSONArray(buffer);
            }
        } catch (MalformedURLException | ProtocolException e) {
            Log.d("debug_my","1");
        } catch (UnsupportedEncodingException e) {
            Log.d("debug_my","2");
        } catch (IOException e) {
            Log.d("debug_my","3");
        } catch (JSONException e) {
            Log.d("debug_my","4");
        }
        return null;
    }
}
