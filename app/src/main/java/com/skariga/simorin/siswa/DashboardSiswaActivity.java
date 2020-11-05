package com.skariga.simorin.siswa;

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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.LoginActivity;
import com.skariga.simorin.auth.SessionManager;

import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardSiswaActivity extends AppCompatActivity {

    RelativeLayout btn_absen, btn_jurnal;
    FusedLocationProviderClient fusedLocationProviderClient;
    ImageView logout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(DashboardSiswaActivity.this);
        setContentView(R.layout.activity_dashboard_siswa);

        btn_absen = findViewById(R.id.absen);
        btn_jurnal = findViewById(R.id.jurnal);
        logout = findViewById(R.id.iv_logout);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardSiswaActivity.this);

        btn_jurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardSiswaActivity.this, JurnalKegiatanSiswaActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DashboardSiswaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(DashboardSiswaActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    Intent i = new Intent(DashboardSiswaActivity.this, AbsenSiswaActivity.class);
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
                    ActivityCompat.requestPermissions(DashboardSiswaActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DashboardSiswaActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Yeay...")
                        .setContentText("Anda berhasil Logout!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(DashboardSiswaActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
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
}