package co.it.kaedu.imagetophilipshue;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: KaeduIT
 * Date: 9/1/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send_image) {
            Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
            SettingsActivity.this.startActivity(mainIntent);
            return true;
        } if (id == R.id.action_weather_forecast) {
            Intent weatherIntent = new Intent(SettingsActivity.this, WeatherForecastActivity.class);
            SettingsActivity.this.startActivity(weatherIntent);
            return true;
        } if (id == R.id.action_settings) {
            // settings
            return true;
        } else if (id == R.id.action_help) {
            //openSearch();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
