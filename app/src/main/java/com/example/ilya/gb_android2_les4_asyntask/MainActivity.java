package com.example.ilya.gb_android2_les4_asyntask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.example.ilya.gb_android2_les4_asyntask.R.id.imageView;

public class MainActivity extends AppCompatActivity {

    public static final String FILE_NAME = "filename";
    private static String URL = "http://www.zwalls.ru/pic/201502/2560x1600/zwalls.ru-48974.jpg";
    Button mButtonStart;
    ImageView mImageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonStart = (Button) findViewById(R.id.btnStart);
        mImageView = (ImageView) findViewById(imageView);
        TextView tv = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra(FILE_NAME);
        if (!TextUtils.isEmpty(fileName)) {
            tv.setText(fileName);
            mImageView.setImageURI(Uri.parse("file://mnt/sdcard/Download/pic.jpg"));
        }
    }

    public void onButtonStart(View v) {
        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);
        progressBar.setVisibility(ProgressBar.VISIBLE);


    }


    public void onButton(View v) {
        new ImageLoadTask(URL, mImageView, progressBar).execute();
    }
}
