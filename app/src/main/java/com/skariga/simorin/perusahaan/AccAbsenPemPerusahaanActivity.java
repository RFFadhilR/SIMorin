package com.skariga.simorin.perusahaan;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skariga.simorin.R;
import com.skariga.simorin.siswa.AbsenSiswaActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AccAbsenPemPerusahaanActivity extends FragmentActivity implements OnMapReadyCallback {

    Button semua, dipilih;
    TextView lihat_lokasi;
    ImageView kembali;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(AccAbsenPemPerusahaanActivity.this);
        setContentView(R.layout.activity_acc_absen_pem_perusahaan);

        semua = findViewById(R.id.setujui_semua);
        dipilih = findViewById(R.id.setujui_dipilih);
        lihat_lokasi = findViewById(R.id.lihat_lokasi);
        kembali = findViewById(R.id.back);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(AccAbsenPemPerusahaanActivity.this);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccAbsenPemPerusahaanActivity.this, DashboardPemPerusahaanActivity.class);
                startActivity(i);
                finish();
            }
        });

        lihat_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(AccAbsenPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(AccAbsenPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        dipilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(AccAbsenPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
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
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,12.0f));
        drawCircle(new LatLng(latitude, longitude));
    }

    private void drawCircle(LatLng point) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(1000);
        circleOptions.fillColor(R.color.light_blue);
        circleOptions.strokeColor(R.color.blue);
        circleOptions.strokeWidth(2);
        map.addCircle(circleOptions);
    }
}