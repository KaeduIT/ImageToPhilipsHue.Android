package co.it.kaedu.imagetophilipshue;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.widget.Button;
import android.widget.EditText;


public class WeatherForecastActivity extends FragmentActivity {

    private GoogleMap googleMap;
    private int mapType = GoogleMap.MAP_TYPE_NORMAL;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymap);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment =  (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();

        LatLng sfLatLng = new LatLng(37.7750, -122.4183);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(sfLatLng)
                .title("San Francisco")
                .snippet("Population: 776733")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng sLatLng = new LatLng(37.857236, -122.486916);
        googleMap.addMarker(new MarkerOptions()
                .position(sLatLng)
                .title("Sausalito")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));


        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


        LatLng cameraLatLng = sfLatLng;
        float cameraZoom = 10;

        if(savedInstanceState != null){
            mapType = savedInstanceState.getInt("map_type", GoogleMap.MAP_TYPE_NORMAL);

            double savedLat = savedInstanceState.getDouble("lat");
            double savedLng = savedInstanceState.getDouble("lng");
            cameraLatLng = new LatLng(savedLat, savedLng);

            cameraZoom = savedInstanceState.getFloat("zoom", 10);
        }

        googleMap.setMapType(mapType);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraLatLng, cameraZoom));



        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherforecast);

        // Getting reference to SupportMapFragment of the activity
        SupportMapFragment fragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.map, fragment).commit();

        // Getting Map for the SupportMapFragment
        googleMap = fragment.getMap();
        googleMap.setMyLocationEnabled(true);

        // settings
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userNamePref   = sharedPref.getString(SettingsFragment.KEY_PREF_USER_NAME, "");
        String deviceNamePref = sharedPref.getString(SettingsFragment.KEY_PREF_DEVICE_NAME, "");
        String ipAddressPref  = sharedPref.getString(SettingsFragment.KEY_PREF_BRIDGE_IP, "");

        if (Utils.hasHoneycomb()) {
            final ActionBar actionBar = getActionBar();
            // Hide title text and set home as up
            // actionBar.setDisplayShowTitleEnabled(false);
            BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(R.drawable.bootstrap);
            bd.setTileModeX(Shader.TileMode.REPEAT);
            bd.setTileModeY(Shader.TileMode.REPEAT);
            actionBar.setBackgroundDrawable(bd);
            actionBar.setDisplayHomeAsUpEnabled(true);
            // actionBar.hide();
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int id = item.getItemId();
        if (id == R.id.action_send_image) {
            Intent sesndImageIntent = new Intent(WeatherForecastActivity.this, MainActivity.class);
            WeatherForecastActivity.this.startActivity(sesndImageIntent);
            return true;
        } if (id == R.id.action_weather_forecast) {
            return true;
        } if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(WeatherForecastActivity.this, SettingsActivity.class);
            WeatherForecastActivity.this.startActivity(settingsIntent);
            return true;
        } else if (id == R.id.action_help) {
            //openSearch();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void startForecastClick(View view) {
           // call the weather API

    }

    public void stopForecastClick(View view) {

    }

}
