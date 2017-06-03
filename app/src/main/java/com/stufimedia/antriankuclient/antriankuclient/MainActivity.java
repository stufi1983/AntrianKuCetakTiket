package com.stufimedia.antriankuclient.antriankuclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    ImageView logo;
    ImageButton imgbtn1,imgbtn2,imgbtn3,imgbtn4;
    TextView layanan1,layanan2,layanan3,layanan4;
    int textHeight= 48;

    public static final String EXTRA_MESSAGE = "com.stufimedia.antriankuclient.antriankuclient.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        logo = (ImageView) findViewById(R.id.imageView);
        imgbtn1 = (ImageButton) findViewById(R.id.imageButton1);
        layanan1 = (TextView) findViewById(R.id.tvLayanan1);
        imgbtn2 = (ImageButton) findViewById(R.id.imageButton2);
        layanan2 = (TextView) findViewById(R.id.tvLayanan2);
        imgbtn3 = (ImageButton) findViewById(R.id.imageButton3);
        layanan3 = (TextView) findViewById(R.id.tvLayanan3);
        imgbtn4 = (ImageButton) findViewById(R.id.imageButton4);
        layanan4 = (TextView) findViewById(R.id.tvLayanan4);

        logo.setLayoutParams(new RelativeLayout.LayoutParams(height / 7, height / 7));

        LinearLayout batas = (LinearLayout) findViewById(R.id.batas);
        batas.setLayoutParams(new LinearLayout.LayoutParams(height / 14, height / 14));

        imgbtn1.setBackgroundResource(R.drawable.buttonclick);
        imgbtn1.setLayoutParams(new LinearLayout.LayoutParams(height / 7, height / 7));
        imgbtn2.setBackgroundResource(R.drawable.buttonclick);
        imgbtn2.setLayoutParams(new LinearLayout.LayoutParams(height/7, height/7));
        imgbtn3.setBackgroundResource(R.drawable.buttonclick);
        imgbtn3.setLayoutParams(new LinearLayout.LayoutParams(height/7, height/7));
        imgbtn4.setBackgroundResource(R.drawable.buttonclick);
        imgbtn4.setLayoutParams(new LinearLayout.LayoutParams(height/7, height/7));

        layanan1.setTextSize(textHeight);
        layanan2.setTextSize(textHeight);
        layanan3.setTextSize(textHeight);
        layanan4.setTextSize(textHeight);


        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imgbtn1.setBackgroundResource(R.drawable.click);
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.queue512);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Setting.class);
            //EditText editText = (EditText) findViewById(R.id.editText);
            //String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, "Setting");
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
