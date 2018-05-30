package portfolio.bagasnasution.googlemapapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by bagas on 5/28/2018.
 */

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button btn_action;
    private LocationManager mLocationManager;

    private String mpider = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_location);

        textView = (TextView) findViewById(R.id.text1);
        btn_action = (Button) findViewById(R.id.btn_action);

        btn_action.setOnClickListener(this);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mpider = mLocationManager.getBestProvider(new Criteria(), true);

        cekProviderLocation();

        Log.i(Config.TAG, "onCreate: sucsess, with provider " + mpider);
    }

    private void cekProviderLocation() {
        String provider = mLocationManager.getBestProvider(new Criteria(), true);

//        if (!mLocationManager.isProviderEnabled(provider)) {

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

//        }
    }

    @Override
    public void onClick(View view) {
        new TaiTask(this, mLocationManager)
                .execute();
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1
                );
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1
                );
            }
        } else {
            getCurrentLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mThread.start();
    }

    private void getCurrentLocation() {

        if (!mLocationManager.isProviderEnabled(mpider)) {
            Toast.makeText(this, "Provider disable", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_DENIED) {

            String provider = mLocationManager.getBestProvider(new Criteria(), true);

            Location location = mLocationManager.getLastKnownLocation(provider);

            List<String> providerList = mLocationManager.getAllProviders();

            Log.i(Config.TAG, "provider list: " + providerList.toString());

            if (location != null) {

                Log.i(Config.TAG, "getCurrentLocation: location not null");

                if (providerList != null) {

                    if (providerList.size() > 0) {

                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();

                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                        try {
                            List<Address> listAddress = geocoder.getFromLocation(latitude, longitude, 1);

                            if (listAddress != null) {
                                if (listAddress.size() > 0) {
                                    String strLocation = "";

                                    strLocation = listAddress.get(0).getAddressLine(0);

                                    textView.setText(strLocation);

                                    Log.d(Config.TAG, "Address: " + strLocation);
                                }
                            }

                        } catch (Exception e) {
                            Log.e(Config.TAG, "getCurrentLocation:  ", e);
                        }

                    }

                }
            } else {
                Log.w(Config.TAG, "getCurrentLocation: Location is null");
            }

        } else {
            Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_DENIED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        getCurrentLocation();

                    }
                }
                break;
        }
    }

    private Thread mThread = new Thread(new MyRunnable());

    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            boolean isLoop = true;

            try {
                Thread.sleep(1000);

                Log.i(Config.TAG, "provuder enable is " + mLocationManager.isProviderEnabled("gps"));

            } catch (Exception e) {
                Log.e(Config.TAG, "run: ", e);
                isLoop = false;
            } finally {
//                if (isLoop)
                run();
            }
        }
    }

    public class TaiTask extends AsyncTask<String, Integer, String> {

        Context nContext = null;
        LocationManager nLocationManager = null;
        String nProvider;

        public TaiTask(Context context, LocationManager locationManager) {
            this.nContext = context;
            this.nLocationManager = locationManager;
            nProvider = nLocationManager.getBestProvider(new Criteria(), true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                if (!nLocationManager.isProviderEnabled(nProvider)) {
                    return null;
                }

                if (ActivityCompat.checkSelfPermission(nContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_DENIED) {

                    String provider = nLocationManager.getBestProvider(new Criteria(), true);

                    Location location = nLocationManager.getLastKnownLocation(provider);

                    List<String> providerList = nLocationManager.getAllProviders();

                    Log.i(Config.TAG, "provider list: " + providerList.toString());

                    if (location != null) {

                        Log.i(Config.TAG, "getCurrentLocation: location not null");

                        if (providerList != null) {

                            if (providerList.size() > 0) {

                                double longitude = location.getLongitude();
                                double latitude = location.getLatitude();

                                Geocoder geocoder = new Geocoder(nContext, Locale.getDefault());
                                try {
                                    List<Address> listAddress = geocoder.getFromLocation(latitude, longitude, 1);
                                    if (listAddress != null) {
                                        if (listAddress.size() > 0) {
                                            String strLocation = "";

                                            strLocation = listAddress.get(0).getAddressLine(0);
                                            Log.d(Config.TAG, "Address: " + strLocation);

                                            return strLocation;
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e(Config.TAG, "getCurrentLocation:  ", e);
                                }
                            }
                        }
                    } else {
                        Log.w(Config.TAG, "getCurrentLocation: Location is null");
                    }
                }
            } catch (Exception e) {
                Log.e(Config.TAG, "doInBackground: ", e);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText("");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                textView.setText(s);
            } else {
                Toast.makeText(nContext, "Please turn on your GPS!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
