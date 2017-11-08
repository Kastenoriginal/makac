package sk.tuke.smart.makac.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import sk.tuke.smart.makac.helpers.SportActivities;
import sk.tuke.smart.makac.utils.Constants;
import sk.tuke.smart.makac.utils.WorkoutStats;

public class TrackerService extends Service implements LocationListener, MapsCallbackListener, StopwatchCallbackListener {

    private final Context context;
    private final IBinder binder = new LocalBinder();

    private MapsCallbackListener mapsCallback;
    private StopwatchCallbackListener stopwatchCallback;

    private boolean canGetLocation = false;

    private Location location;
    private double latitude;
    private double longitude;

    protected LocationManager locationManager;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private WorkoutStats workoutStats;
    private List<Location> positionList = new ArrayList<>();

    public TrackerService() {
        this.context = null;
    }

    public TrackerService(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setMapsCallbacks(MapsCallbackListener callback) {
        this.mapsCallback = callback;
    }

    public void setStopwatchCallbacks(StopwatchCallbackListener callback) {
        this.stopwatchCallback = callback;
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationChanged();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public Location getLocation() {
        if (context == null) {
            return null;
        }

        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (locationManager == null) {
            return null;
        }

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (!isGPSEnabled) {
            return null;
        }

        this.canGetLocation = true;

        if (location == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MIN_TIME_BW_UPDATES, Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        }


        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(TrackerService.this);
        }
    }

    public void startWorkout(Context context) {
        // TODO: 11/6/2017
        Toast.makeText(context.getApplicationContext(), "Received start workout broadcast", Toast.LENGTH_SHORT).show();

        workoutStats = new WorkoutStats.Builder().duration(0).distance(0).pace(0).calories(0).build();
        startTicking(context);
    }

    public void continueWorkout(Context context) {
        // TODO: 11/6/2017
        Toast.makeText(context.getApplicationContext(), "Received continue workout broadcast", Toast.LENGTH_SHORT).show();
    }

    public void pauseWorkout(Context context) {
        // TODO: 11/6/2017
        Toast.makeText(context.getApplicationContext(), "Received pause workout broadcast", Toast.LENGTH_SHORT).show();
        stopTicking();
    }

    public void stopWorkout(Context context) {
        // TODO: 11/6/2017
        Toast.makeText(context.getApplicationContext(), "Received stop workout broadcast", Toast.LENGTH_SHORT).show();
        stopTicking();
    }

    public int countCalories() {
        // TODO: 11/6/2017
        return -1;
    }

    @Override
    public void LocationChanged() {
        if (mapsCallback != null) {
            mapsCallback.LocationChanged();
        }
    }

    private void startTicking(final Context context) {
        final Intent intent = new Intent();
        intent.putExtra(Constants.TAG_DURATION, workoutStats.getDuration());
        intent.putExtra(Constants.TAG_DISTANCE, workoutStats.getDistance());
        intent.putExtra(Constants.TAG_PACE, workoutStats.getPace());
        intent.putExtra(Constants.TAG_CALORIES, workoutStats.getCalories());
        intent.putExtra(Constants.TAG_STATE, Constants.STATE_RUNNING);
        intent.putExtra(Constants.TAG_ACTIVITY_TYPE, Constants.RUNNING);

        Runnable tickRunnable = new Runnable() {
            @Override
            public void run() {
                intent.setAction(Constants.TICK);
                context.sendBroadcast(intent);
            }
        };

        executor.scheduleAtFixedRate(tickRunnable, 0, 1, TimeUnit.SECONDS);
    }

    private void stopTicking() {
        executor.shutdown();
    }

    @Override
    public void onTick(Intent intent) {
        // TODO: 11/8/2017 update WorkoutStats model through setters
        workoutStats.setDuration(workoutStats.getDuration() + 1);
//        workoutStats.setDistance(5);
//        workoutStats.setPace(5);
//
//        int activityType = intent.getIntExtra(Constants.TAG_STATE, -1);
//        double calories = SportActivities.countCalories(activityType, 80, new ArrayList<Float>(), 5);
//
//        workoutStats.setCalories(calories);

        if (stopwatchCallback != null) {
            stopwatchCallback.onTick(intent);
        }
    }

    public class LocalBinder extends Binder {
        public TrackerService getService() {
            return TrackerService.this;
        }
    }
}
