package com.stufimedia.antriankutombol.tombolcetak;

/**
 * Created by Login on 2/20/2018.
 */

public class UDP_Client {
    /*
    private AsyncTask<Void, Void, Void> async_cient;
    public String Message, IP;


    @SuppressLint("NewApi")
    public void SendMessage() {
        async_cient = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatagramSocket ds = null;

                try {
                    ds = new DatagramSocket();
                    DatagramPacket dp;
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    dp = new DatagramPacket(Message.getBytes(), Message.length(), serverAddr, 50000);
                    ds.setBroadcast(true);
                    ds.send(dp);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        };

        if (Build.VERSION.SDK_INT >= 11)
            async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else async_cient.execute();
    }
    */
}
