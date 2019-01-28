package org.resoft.domainnotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneStatReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

        } else {

        }

        Intent i = new Intent(context,MyService.class);
        context.startService(i);
        Log.i("Autostart", "started");

    }

}