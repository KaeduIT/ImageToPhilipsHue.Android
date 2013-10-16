package co.it.kaedu.imagetophilipshue;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: KaeduIT
 * Date: 9/5/13
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendImageAsyncTask extends AsyncTask<Void, Integer, String> {

    private final WeakReference<ImageView> imageViewReference;
    private final WeakReference<ProgressBar> progressBarReference;
    private Context context = null;
    //private Intent data = null;

    public SendImageAsyncTask(ImageView imageView, ProgressBar progressTest, Context activityContext) {
        progressBarReference = new WeakReference<ProgressBar>(progressTest);
        imageViewReference = new WeakReference<ImageView>(imageView);
        context = activityContext;
    }

    // param1
    @Override
    protected String doInBackground(Void... params) {

        URL url = null;
        int [] pixels;
        String message = "Sending...";
        Bitmap imageViewBitmap = null;
        HttpURLConnection urlConnection = null;
        try {
            // settings
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            String userNamePref   = sharedPref.getString("KEY_PREF_USER_NAME", "a");
            //String deviceNamePref = sharedPref.getString("KEY_PREF_DEVICE_NAME", "b");
            String ipAddressPref  = sharedPref.getString("KEY_PREF_BRIDGE_IP", "c");

            // get pixels convert and send using web service API
            int lightid = 0;
            String apiUrl = "http://" + ipAddressPref + "/api";
            String commandUrl = apiUrl + "/" + userNamePref + "/lights/" + lightid + "/state";

            double quotient = 182.04166666666666666666666666667;
            imageViewBitmap = ((BitmapDrawable)imageViewReference.get().getDrawable()).getBitmap();

            int imageViewBitmapWidth  = imageViewBitmap.getWidth();
            int imageViewBitmapHeight = imageViewBitmap.getHeight();
            int bitmapSize = imageViewBitmapWidth * imageViewBitmapHeight;
            pixels = new int [bitmapSize];
            imageViewBitmap.getPixels(pixels, 0, imageViewBitmapWidth, 0, 0, imageViewBitmapWidth, imageViewBitmapHeight);

            int progressPercent = 1;
            int progressStep = (int)pixels.length/100;

            for (int i=1; i<pixels.length; i++) {
                float [] hsv = new float[3];
                Color.RGBToHSV(i >> 16, i >> 8, i, hsv);
                String strHSV = "'sat':" + (long)Math.ceil(hsv[1]*255) + ", 'bri':" + (long)Math.ceil(hsv[2]*255) + ", 'hue':" + (long)Math.ceil(hsv[0]*quotient);
                String data = "{'on':true, " + strHSV + ",'effect':'none','transitiontime':2}";

                lightid = i;
                commandUrl = apiUrl + "/" + userNamePref + "/lights/" + lightid + "/state";
                url = new URL(commandUrl);

                if(url != null) {

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("PUT");
                    urlConnection.setRequestProperty("Content-Type", "text/plain");

                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(data);
                    writer.close();

                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // OK
                        if(i%progressStep == 0)
                            publishProgress(progressPercent++);
                     } else {
                        // handle the error code...continue
                        continue;
                    }
                }
            }
            //bitmapOut.setPixels(pixelsOut, 0, imageViewBitmapWidth, 0, 0, imageViewBitmapWidth, imageViewBitmapHeight);
            //return bitmapOut;
        }catch (IllegalStateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (IllegalArgumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (ProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            urlConnection.disconnect();
            message = "Image Sent";
        }

        return message;
    }

    // param2
    @Override
    protected void onProgressUpdate(Integer... values) {
        if (progressBarReference != null) {
            final ProgressBar progressBar = progressBarReference.get();
            progressBar.setProgress(values[0]);
        }
    }

    // param3
    @Override
    protected void onPostExecute(String message) {
        if (isCancelled()) {
            message = "cancelled";
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        if (progressBarReference != null) {
            final ProgressBar progressBar = progressBarReference.get();
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setProgress(0);
        }
    }

    @Override
    protected void onPreExecute() {
        if (progressBarReference != null) {
            final ProgressBar progressBar = progressBarReference.get();
            progressBar.setVisibility(View.VISIBLE);
        }

    }
}
