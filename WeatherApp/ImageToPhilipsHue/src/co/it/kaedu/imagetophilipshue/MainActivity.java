package co.it.kaedu.imagetophilipshue;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.graphics.Color;

import java.io.*;
import java.net.*;
import java.util.Random;


public class MainActivity extends FragmentActivity {

    private static final int LOAD_IMAGE = 1;
    private final Context context = this;
    private Button button;

    void handleChangeImagefromIntent(Uri selectedImageUri) {

        //android.net.Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (selectedImageUri != null) {
            // Update UI to reflect image being shared
            ImageView imageView;
            imageView = (ImageView) findViewById(R.id.imageview_send_image);

            if(selectedImageUri.getScheme().equals(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getScheme())
            || selectedImageUri.getScheme().equals(MediaStore.Images.Media.INTERNAL_CONTENT_URI.getScheme())) {

                Bitmap imageBmp = null;
                try {
                    imageBmp = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri), 100, 100, false);
                    imageView.setImageBitmap(imageBmp);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } else {
                // selectedImagePath = Environment.getExternalStorageDirectory().getPath() + File.separator + getRealPathFromURI(selectedImageUri);
                // selectedImagePath = new File(selectedImageUri.toString());
                // File selectedImagePath = new File(getRealPathFromURI(selectedImageUri));
                // context.getCacheDir().getPath()
                // Bitmap imageBmp = BitmapFactory.decodeStream(imageIs);
            }
         }
    }

    public void changeImageClick(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"), LOAD_IMAGE);
	}

    public void sendImagetoBridgeClick(View view) {

        ImageView imageView = (ImageView) findViewById(R.id.imageview_send_image);
        ProgressBar progressTest = (ProgressBar) findViewById(R.id.progressTest);
        SendImageAsyncTask task = new SendImageAsyncTask(imageView,progressTest,context);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
        }
        else {
            task.execute();
        }

	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(context, R.xml.settings_preferences, false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == LOAD_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //Uri selectedImageUri = data.getData();
                //handleChangeImagefromIntent(selectedImageUri);

                ImageView imageView = (ImageView) findViewById(R.id.imageview_send_image);
                ProgressBar progressTest = (ProgressBar) findViewById(R.id.progressTest);
                ChangeImageAsyncTask task = new ChangeImageAsyncTask(imageView,progressTest,getContentResolver());
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
                    task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR, data);
                }
                else {
                    task.execute(data);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //MenuItem item = menu.findItem(R.id.menu_item_share);
        //shareActionProvider = (ShareActionProvider) item.getActionProvider();

        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		int id = item.getItemId();
        if (id == R.id.action_send_image) {
            // main
            return true;
        } if (id == R.id.action_weather_forecast) {
            Intent weatherIntent = new Intent(MainActivity.this, WeatherForecastActivity.class);
            MainActivity.this.startActivity(weatherIntent);
            return true;
        } if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(settingsIntent);
            return true;
	    } else if (id == R.id.action_help) {
	    	//openSearch();
	    	return true;
	    } else {
	    	return super.onOptionsItemSelected(item);
	    }
	}


}
