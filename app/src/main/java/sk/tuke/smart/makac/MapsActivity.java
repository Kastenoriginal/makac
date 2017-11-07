package sk.tuke.smart.makac;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import butterknife.ButterKnife;
import sk.tuke.smart.makac.services.MapsCallbackListener;
import sk.tuke.smart.makac.services.TrackerService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsCallbackListener {

    private TrackerService trackerService;
    private boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps_workoutmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TrackerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            trackerService.setCallbacks(null);
            unbindService(serviceConnection);
            bound = false;
        }
    }

    @Override
    protected void onDestroy() {
        trackerService.stopUsingGPS();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        trackerService = new TrackerService(this);
        Location location = getLocation();
        if (location != null) {
            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        } else {
            Toast.makeText(this, "Location is null", Toast.LENGTH_SHORT).show();
        }
    }

    private Location getLocation() {
        return trackerService.canGetLocation() ? trackerService.getLocation() : null;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            TrackerService.LocalBinder binder = (TrackerService.LocalBinder) service;
            trackerService = binder.getService();
            bound = true;
            trackerService.setCallbacks(MapsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    @Override
    public void LocationChanged() {
        Location location = getLocation();
        if (location != null) {
            Toast.makeText(this, "New location: New Latitude: " + location.getLatitude() + " New Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Location is null after changed", Toast.LENGTH_SHORT).show();
        }
    }
}
