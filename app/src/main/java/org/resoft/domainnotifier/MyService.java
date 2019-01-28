package org.resoft.domainnotifier;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service implements GeneralCallbacks {
    private static final String CHANNEL_ID = "id"; //Channel ID
    private static final String CHANNEL_NAME = "domain"; //Channel Adı
    //private static final int NOTIFICATION_ID = 52; //Notification ID'si
    private NotificationManager manager;
    private Handler handler;
    private Api api;
    private Timer timer;
    private TimerTask timerTask;

    private static final String CHANNEL_ID2 = "id2"; //Channel ID
    private static final String CHANNEL_NAME2 = "domain2"; //Channel Adı
    //private static final int NOTIFICATION_ID2 = 522; //Notification ID'si
    private SessionManager session;

    private int period = 1000*5;
    private int shortPeriod = 1000*5;
    private int longPeriod = 1800000;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        api = new Api(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        handler = new Handler();

        period = shortPeriod;

        startTimerTask();
    }

    public void startTimerTask(){

        try {
            timer.cancel();
            timerTask.cancel();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<String> domains = session.getDomains();
                            for(int i = 0; i<domains.size(); i++){
                                String domain = domains.get(i);
                                api.get("http://165.227.145.16/php-whois/index.php?domain="+domain+"&state="+i, new HashMap<String, String>(), MyService.this);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask,0,period);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(getApplicationContext(), MyService.class));
        } else {
            startService(new Intent(getApplicationContext(), MyService.class));
        }
    }

    @Override
    public void VolleyResponse(JSONObject data) throws JSONException {
        if(data.getBoolean("status")){
            if(period == longPeriod) {
                period = shortPeriod;
                startTimerTask();
            }
            notificationMSG(data.getString("domain"),"boş", CHANNEL_ID, data.getInt("state"));
        }else{
            if(period == shortPeriod){
                period = longPeriod;
                startTimerTask();
            }
            notificationMSG(data.getString("domain"), "dolu", CHANNEL_ID2, data.getInt("state"));
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void notificationMSG(String domain, String msg, String CHANNEL_ID, int NOTIFICATION_ID){
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(domain)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true).setPriority(Notification.PRIORITY_MAX)
                .build();

        manager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void VolleyError() {

    }

    @Override
    public void ArrayListResponse(ArrayList<String> data) {

    }
}
