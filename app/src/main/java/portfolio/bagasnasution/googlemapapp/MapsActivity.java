package portfolio.bagasnasution.googlemapapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, OnCameraIdleListener, OnMapClickListener, OnMapLongClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getPermission();
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        1
                );
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        1
                );
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng jegarta = new LatLng(-6.175302, 106.827139);
        mMap.addMarker(new MarkerOptions().position(jegarta).title("Monumen Nasional"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jegarta));

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.175302, 106.827139), 12));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_DENIED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);

//                        mMap.getMyLocation()
                    }
                }
                break;
        }
    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(null);

        mMap.addMarker(markerOptions);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

}
