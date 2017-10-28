package com.example.kengo.myandroidapp;


import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int count = 0;
    private TextView tv;
    private Handler handler = new Handler();
    private Timer mytimer = new Timer();
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.WHITE);
        ll.setOrientation(LinearLayout.VERTICAL);
        setContentView(ll);

        tv = new TextView(this);
        tv.setBackgroundColor(Color.rgb(224, 224, 224));
        tv.setTextColor(Color.GREEN);
        ll.addView(tv);
    }

    public void onResume() {
        super.onResume();
        mytimer.schedule(new MyTimer(), 0, 5000);
    }

    public void onPause() {
        super.onPause();
        //mytimer.cancel();
    }

    private class MyThread extends Thread {
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
                    //tv.setText("count=" + count+5);
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location myLocate = locationManager.getLastKnownLocation("gps");
                    //MapControllerの取得
                    if (myLocate != null) {
                        //現在地情報取得成功
                        //緯度の取得
                        double latitude = (myLocate.getLatitude());
                        //経度の取得
                        double longitude = (myLocate.getLongitude());
                        long currentTimeMillis = System.currentTimeMillis();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(currentTimeMillis);
                        Log.v("Test",
                                (calendar.get(Calendar.HOUR_OF_DAY))+9 + ":" +
                                        calendar.get(Calendar.MINUTE) + ":" +
                                        calendar.get(Calendar.SECOND) + ":" +
                                        calendar.get(Calendar.MILLISECOND));
                        Log.d("test", Double.toString(latitude));
                        Log.d("test", Double.toString(longitude));
                        //LatLng nowPlace = new LatLng(latitude, longitude);
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(nowPlace));
                        String centerText =
                                "latitude "+latitude + "\n"
                                        + "longitude " + longitude;

                        tv.setText(centerText);

                    } else {
                        //現在地情報取得失敗時の処理
                        Toast.makeText(MainActivity.this, "現在地取得エラー", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private class MyTimer extends TimerTask {
        public void run() {
            MyThread mythread = new MyThread();
            mythread.start();
        }
    }
}