package org.resoft.domainnotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class autostart extends BroadcastReceiver
{
    public void onReceive(Context context, Intent arg1)
    {
        Intent intent = new Intent(context,MyService.class);
        context.startService(intent);
        Log.i("Autostart", "started");
    }
}