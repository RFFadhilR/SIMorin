package com.skariga.simorin.perusahaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.LoginActivity;
import com.skariga.simorin.auth.SessionManager;
import com.skariga.simorin.sekolah.DashboardPemSekolahActivity;
import com.skariga.simorin.siswa.AbsenSiswaActivity;
import com.skariga.simorin.siswa.DashboardSiswaActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardPemPerusahaanActivity extends AppCompatActivity {

    RelativeLayout absen, jurnal;
    TextView nama, perusahaan;
    ImageView logout;
    SessionManager sessionManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(DashboardPemPerusahaanActivity.this);
        setContentView(R.layout.activity_dashboard_pem_perusahaan);

        absen = findViewById(R.id.acc_absen);
        jurnal = findViewById(R.id.acc_jurnal);
        logout = findViewById(R.id.iv_logout);
        nama = findViewById(R.id.tv_nama);
        perusahaan = findViewById(R.id.tv_perusahaan);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardPemPerusahaanActivity.this);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mPer = user.get(sessionManager.PSB);
        String mNama = user.get(sessionManager.NAMA);

        perusahaan.setText(mPer);
        nama.setText(mNama);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DashboardPemPerusahaanActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Yeay...")
                        .setContentText("Anda berhasil Logout!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sessionManager.logout();
                            }
                        })
                        .show();
            }
        });

        absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DashboardPemPerusahaanActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(DashboardPemPerusahaanActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    Intent i = new Intent(DashboardPemPerusahaanActivity.this, AccAbsenPemPerusahaanActivity.class);
                                    i.putExtra("latitude", Double.toString((double) addresses.get(0).getLatitude()));
                                    i.putExtra("longitude", Double.toString((double) addresses.get(0).getLongitude()));
                                    startActivity(i);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(DashboardPemPerusahaanActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        jurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardPemPerusahaanActivity.this, AccJurnalPemPerusahaanActivity.class);
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
}