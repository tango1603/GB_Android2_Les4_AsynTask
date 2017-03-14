package com.example.ilya.gb_android2_les4_asyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Ilya on 12.03.2017.
 */

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;
    private ProgressBar mProgressBar;

    public ImageLoadTask(String url, ImageView imageView,ProgressBar progressBar) {
        this.url = url;
        this.imageView = imageView;
        this.mProgressBar = progressBar;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }
}
