package com.skariga.simorin.siswa;

import androidx.fragment.app.FragmentActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skariga.simorin.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbsenSiswaActivity extends FragmentActivity implements OnMapReadyCallback {

    Button btn_kembali;
    TextView tv_status, tv_tanggal;
    GoogleMap map;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(AbsenSiswaActivity.this);
        setContentView(R.layout.activity_absen_siswa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(AbsenSiswaActivity.this);

        date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy / HH:mm");

        tv_tanggal = findViewById(R.id.tv_tanggal);
        btn_kembali = findViewById(R.id.btn_kembali);

        tv_tanggal.setText(formatter.format(date));

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AbsenSiswaActivity.this, DashboardSiswaActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        double longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        LatLng lokasi = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(lokasi).title("Lokasi Saat ini"));
        map.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,18.0f));
    }
}