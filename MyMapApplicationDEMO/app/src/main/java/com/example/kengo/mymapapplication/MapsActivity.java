package com.example.kengo.mymapapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SeekBar sb;
    private TextView tv;
    private Button button;
    private long time=27000000;
    private double pos[][]={{43.068625,141.350801},
            {43.068532,141.377386},
            {43.054654,141.413608},
            {43.046855,141.438642},
            {43.038788,141.47247},
            {43.016435,141.486889},
            {42.978736,141.56319},
            {42.921809,141.573453},
            {42.90244,141.574521},
            {42.882729,141.586812},
            {42.86749,141.604807},
            {42.851867,141.623062},
            {42.82844,141.651748},
            {42.808271,141.675186},
            {42.787599,141.681397}
    };
    private String station[] ={"札幌","恵庭","白石",
            "平和","新札幌","上野幌","北広島","島松",
            "恵み野","恵庭","サッポロビール庭園","長都",
            "千歳","南千歳","新千歳空港"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        button = (Button) findViewById(R.id.button);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tv = (TextView) findViewById(R.id.tv);

        sb = (SeekBar)findViewById(R.id.SeekBar1);
        sb.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        //tv.setText("設定値:"+sb.getProgress());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(time+progress*1000);
                        String hour =String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                        String minutes = String.format("%02d", calendar.get(Calendar.MINUTE));
                        String second = String.format("%02d", calendar.get(Calendar.SECOND));
                        /*String printtime=(calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
                                calendar.get(Calendar.MINUTE) + ":" +
                                calendar.get(Calendar.SECOND);*/
                        String printtime = (hour+":"+minutes+":"+second);
                        tv.setText(printtime);
                        int num=0;
                        for(int i=0;i<15;i++) {
                            if (progress <= (i + 1) * 240) {
                                num = i;
                                break;
                            }
                        }
                        LatLng position = new LatLng(pos[num][0],pos[num][1]);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる

                    }
                }
        );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンがクリックされた時に呼び出されます
                PackageManager pm = getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage("com.Company.jphacks");
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("error","アプリが存在しません");
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sapporo = new LatLng(43.068625,141.350801);
        mMap.setMinZoomPreference(10);
        //LatLng position2 = new LatLng(40, 141);
        for(int i=0;i<15;i++){
            LatLng position2 = new LatLng(pos[i][0],pos[i][1]);
            mMap.addMarker(new MarkerOptions().position(position2).title(station[i]));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sapporo));

    }

}
