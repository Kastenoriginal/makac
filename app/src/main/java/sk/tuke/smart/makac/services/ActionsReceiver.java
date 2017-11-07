package sk.tuke.smart.makac.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ActionsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: 11/7/2017  get broadcast from intent and do something with it
        Toast.makeText(context.getApplicationContext(), "Received broadcast: " + intent, Toast.LENGTH_LONG).show();
    }
}
