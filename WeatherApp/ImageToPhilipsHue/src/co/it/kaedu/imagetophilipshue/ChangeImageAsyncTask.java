package co.it.kaedu.imagetophilipshue;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created with IntelliJ IDEA.
 * User: KaeduIT
 * Date: 9/5/13
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeImageAsyncTask extends AsyncTask<Intent, Integer, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private final WeakReference<ProgressBar> progressBarReference;
    private ContentResolver contentResolver = null;
    private Intent data = null;

    public ChangeImageAsyncTask(ImageView imageView, ProgressBar progressTest, ContentResolver cr) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        progressBarReference = new WeakReference<ProgressBar>(progressTest);
        //progressBar = progressTest;
        contentResolver = cr;
    }

    // param1
    @Override
    protected Bitmap doInBackground(Intent... params) {

        data = params[0];
        Uri selectedImageUri = data.getData();
        publishProgress(21);

        if (selectedImageUri != null) {
            publishProgress(50);
            if(selectedImageUri.getScheme().equals(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getScheme())
                    || selectedImageUri.getScheme().equals(MediaStore.Images.Media.INTERNAL_CONTENT_URI.getScheme())) {

                publishProgress(75);
                try {
                    publishProgress(100);
                    return Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(contentResolver,selectedImageUri),100,100,false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // bitmap url with http scheme
                // Bitmap imageBmp = null;
                // return decodeSampledBitmapFromResource(getResources(), data, 100, 100));
            }
        }
        return null;
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
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

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
