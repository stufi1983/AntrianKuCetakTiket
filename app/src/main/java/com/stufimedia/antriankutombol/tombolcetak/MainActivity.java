package com.stufimedia.antriankutombol.tombolcetak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;


public class MainActivity extends ActionBarActivity {

//    ImageView logo;
    ImageButton imgbtn1,imgbtn2,imgbtn3,imgbtn4;
    TextView layanan1,layanan2,layanan3,layanan4;
    int textHeight= 32;
    SharedPreferences pref;
    String serverIP="127.0.0.1";
    String pesan = "";

    public static final String EXTRA_MESSAGE = "com.stufimedia.antriankuclient.antriankuclient.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);



        pref = getApplicationContext().getSharedPreferences("AntriankuClient", 0); // 0 - for private mode
        String[] daftar = pref.getString("PrefixList", "Loket:Kasir:Teller:Service:loket:").split(":");
        serverIP = pref.getString("IP", "127.0.0.1");

        String title = pref.getString("Title", "Cetak Tiket");
//        TextView judul = (TextView) findViewById(R.id.textView);
//        judul.setText(title);
        setTitle(title);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

//        logo = (ImageView) findViewById(R.id.imageView);
        imgbtn1 = (ImageButton) findViewById(R.id.imageButton1);
        layanan1 = (TextView) findViewById(R.id.tvLayanan1);
        imgbtn2 = (ImageButton) findViewById(R.id.imageButton2);
        layanan2 = (TextView) findViewById(R.id.tvLayanan2);
        imgbtn3 = (ImageButton) findViewById(R.id.imageButton3);
        layanan3 = (TextView) findViewById(R.id.tvLayanan3);
        imgbtn4 = (ImageButton) findViewById(R.id.imageButton4);
        layanan4 = (TextView) findViewById(R.id.tvLayanan4);

//        logo.setLayoutParams(new RelativeLayout.LayoutParams(height / 7, height / 7));

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
        if(daftar.length>=1) layanan1.setText(daftar[0]);
        if(daftar.length>=2) layanan2.setText(daftar[1]);
        if(daftar.length>=3) layanan3.setText(daftar[2]);
        if(daftar.length>=4) layanan4.setText(daftar[3]);


        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIP = pref.getString("IP", "127.0.0.1");
                sendMessage("P00" + NetworkUtils.getIPAddress(true), serverIP);
            }
        });
        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIP = pref.getString("IP", "127.0.0.1");
                sendMessage("P10" + NetworkUtils.getIPAddress(true), serverIP);
            }
        });
        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIP = pref.getString("IP", "127.0.0.1");
                sendMessage("P20" + NetworkUtils.getIPAddress(true), serverIP);
            }
        });
        imgbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIP = pref.getString("IP", "127.0.0.1");
                sendMessage("P30" + NetworkUtils.getIPAddress(true), serverIP);
            }
        });

//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.queue512);
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
    private void sendMessage(final String message, final String remoteIP) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {

            String stringData, response;

            @Override
            public void run() {

                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket(51000);
                    InetAddress serverAddr = InetAddress.getByName(remoteIP);
                    DatagramPacket dp;

                    dp = new DatagramPacket(message.getBytes(), message.length(), serverAddr, 50000);
                    ds.send(dp);

                    byte[] lMsg = new byte[1000];
                    dp = new DatagramPacket(lMsg, lMsg.length);
                    ds.setSoTimeout(1000);
                    try {
                        ds.receive(dp);
                        stringData = new String(lMsg, 0, dp.getLength());
                    } catch (SocketTimeoutException te) {
                        response = "Gagal menghubungi AntrianKu";
                        pesan = response;
                        ds.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //String s = mTextViewReplyFromServer.getText().toString();
                        if (stringData != null) {
                            if (stringData.trim().length() != 0) {
                                //if (stringData.charAt(0) != ':') return;

                                stringData = stringData.substring(1, stringData.length());
                                pesan = stringData;

                                String[] arraySpinner = stringData.split(":");

                                Toast t = Toast.makeText(MainActivity.this, "Tiket dicetak", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        } else {
//                            Context context = getBaseContext();
//                            Toast t = Toast.makeText(context, "Koneksi gagal, pastikan IP AntrianKu benar", Toast.LENGTH_LONG);
//                            t.show();
                        }
                    }
                });
            }

        });

        thread.start();
    }

}
