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

public class TrackerService extends Service implements LocationListener, MapsCallbackListener {

    private final Context context;
    private final IBinder binder = new LocalBinder();

    private MapsCallbackListener callback;

    boolean isGPSEnabled = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 3;

    protected LocationManager locationManager;

    @SuppressWarnings("unused")
    public TrackerService() {
        this.context = null;
    }

    public TrackerService(Context context) {
        this.context = context;
        getLocation();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setCallbacks(MapsCallbackListener callback) {
        this.callback = callback;
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
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
                return null;
            }

            this.canGetLocation = true;

            if (location == null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return null;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    public static void startWorkout() {
        // TODO: 11/6/2017
    }

    public static void continueWorkout() {
        // TODO: 11/6/2017
    }

    public static void pauseWorkout() {
        // TODO: 11/6/2017
    }

    public static void stopWorkout() {
        // TODO: 11/6/2017
    }

    public static int countCalories() {
        // TODO: 11/6/2017
        return -1;
    }

    @Override
    public void LocationChanged() {
        if (callback != null) {
            callback.LocationChanged();
        }
    }

    public class LocalBinder extends Binder {
        public TrackerService getService() {
            return TrackerService.this;
        }
    }
}
