package com.example.nh.webservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
Button get,show;
TextView textView;
URL url;
InputStream inputStream;
String link="https://jsonplaceholder.typicode.com/users";
HttpURLConnection urlConnection;
String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get = findViewById(R.id.getdata);
        show = findViewById(R.id.showdata);
        textView = findViewById(R.id.txt);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            url = new URL(link);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setConnectTimeout(15000);
                            urlConnection.setReadTimeout(15000);
                            urlConnection.setRequestMethod("GET");
                            inputStream = urlConnection.getInputStream();
                            int responseCode = urlConnection.getResponseCode();
                            int c = 0;
                            StringBuffer buffer = new StringBuffer();
                            if (responseCode == HttpsURLConnection.HTTP_OK) {
                                while ((c = inputStream.read()) != -1) {
                                    buffer.append((char) c);
                                }
                            }
                            result = buffer.toString();
                            inputStream.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            urlConnection.disconnect();
                        }

                    }

                }).start();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(result);
            }
        });
    }
}
