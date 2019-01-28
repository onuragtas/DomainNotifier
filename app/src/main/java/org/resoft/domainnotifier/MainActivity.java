package org.resoft.domainnotifier;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements GeneralCallbacks {

    private static final String CHANNEL_ID = "id"; //Channel ID
    private static final String CHANNEL_NAME = "domain"; //Channel Adı
    private static final int NOTIFICATION_ID = 52; //Notification ID'si

    private static final String CHANNEL_ID2 = "id2"; //Channel ID
    private static final String CHANNEL_NAME2 = "domain2"; //Channel Adı
    private static final int NOTIFICATION_ID2 = 522; //Notification ID'si
    private NotificationManager manager;
    private TextView mTextMessage;
    private ListView listview;
    private ArrayList<String> listData;
    private Button buton;
    private ListAdapter adapter;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager session = new SessionManager(this);

        buton = (Button) findViewById(R.id.add);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.enableVibration(true);
        channel.setSound(null, null);
        manager.createNotificationChannel(channel);

        NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID2, CHANNEL_NAME2, NotificationManager.IMPORTANCE_LOW);
        manager.createNotificationChannel(channel2);

        listview = (ListView) findViewById(R.id.list);
        try {
            listData = session.getDomains();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new ListAdapter(MainActivity.this, listData);
        listview.setAdapter(adapter);

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new Dialogs().DialogAddNew(MainActivity.this,MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        startService(new Intent(MainActivity.this, MyService.class));
    }

    @Override
    public void VolleyResponse(JSONObject data) throws JSONException {

    }

    @Override
    public void VolleyError() {

    }

    @Override
    public void ArrayListResponse(ArrayList<String> data) {
        adapter.addAll(data);
    }
}
