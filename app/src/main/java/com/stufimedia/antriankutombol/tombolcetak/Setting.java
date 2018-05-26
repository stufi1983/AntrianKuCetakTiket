package com.stufimedia.antriankutombol.tombolcetak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Setting extends Activity {

    Thread udpConnect;
    AlertDialog ad;
    SharedPreferences pref;
    EditText editText;
    String thisIP = "";
    String pesan,judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        editText = (EditText) findViewById(R.id.editText);

        //Read from pref
        pref = getApplicationContext().getSharedPreferences("AntriankuClient", 0); // 0 - for private mode
        if (pref.contains("IP")) {
            editText.setText(pref.getString("IP", ""));
        }
        String[] arraySpinner = pref.getString("PrefixList", "Loket:Loket:Loket:Loket:loket:").split(":");
        thisIP = NetworkUtils.getIPAddress(true);

        judul = pref.getString("Title", "Cetak Tiket");
        EditText judulText = (EditText)findViewById(R.id.editText3);
        judulText.setText(judul);
    }

    public void TestClick(View view) {
        String ip = editText.getText().toString();

        //Save to pref
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("IP", ip); // Storing string
        editor.commit();

        sendMessage("T00" + thisIP, ip);


//        //UDP Client erstellen
//        UDP_Client Client = new UDP_Client();
//        Client.Message = "T00";
//        Client.IP = "172.16.228.237";
//        //Send message
//        Client.SendMessage();
//
//        ad = new AlertDialog.Builder(this).create();
//        ad.setMessage("Connecting...");
//        ad.show();

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
                    ds.setSoTimeout(5000);
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
                                if (stringData.charAt(0) != ':') return;

                                stringData = stringData.substring(1, stringData.length());
                                pesan = stringData;

                                String[] arraySpinner = stringData.split(":");

                                Toast t = Toast.makeText(Setting.this, "Koneksi Berhasil silahkan klik simpan", Toast.LENGTH_LONG);
                                t.show();
                                Button btn = (Button) findViewById(R.id.button);
                                btn.setText("Simpan");
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        EditText judulText = (EditText)findViewById(R.id.editText3);
                                        SharedPreferences sp = getSharedPreferences("AntriankuClient", MODE_PRIVATE);
                                        SharedPreferences.Editor spe = sp.edit();
                                        spe.putString("IP", editText.getText().toString());
                                        spe.putString("PrefixList", pesan);
                                        spe.putString("Title", judulText.getText().toString());
                                        spe.apply();
                                        Context context = getBaseContext();
                                        Toast t = Toast.makeText(context, "Setting Berhasil Disimpan", Toast.LENGTH_LONG);
                                        t.show();
                                        finish();
                                    }
                                });
//                                Log.d("AC", editText.getText().toString() + "&" + pesan);
                            }
                        } else {
                            Context context = getBaseContext();
                            Toast t = Toast.makeText(context, "Koneksi gagal, pastikan IP AntrianKu benar", Toast.LENGTH_LONG);
                            t.show();
                        }
                    }
                });
            }

        });

        thread.start();
    }
}
