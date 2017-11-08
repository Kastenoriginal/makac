package sk.tuke.smart.makac.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import sk.tuke.smart.makac.utils.Constants;

public class ActionsReceiver extends BroadcastReceiver {

    private TrackerService trackerService = new TrackerService();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null) {
            return;
        }

        switch (intent.getAction()) {
            case Constants.COMMAND_START:
                trackerService.startWorkout(context);
                break;
            case Constants.COMMAND_STOP:
                trackerService.stopWorkout(context);
                break;
            case Constants.COMMAND_PAUSE:
                trackerService.pauseWorkout(context);
                break;
            case Constants.COMMAND_CONTINUE:
                trackerService.continueWorkout(context);
                break;
            case Constants.TICK:
                trackerService.onTick(intent);
                break;
            default:
        }
    }
}
