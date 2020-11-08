package com.skariga.simorin.auth;

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
import com.skariga.simorin.ortu.LihatAbsenOrangTuaActivity;
import com.skariga.simorin.ortu.LihatJurnalOrangTuaActivity;
import com.skariga.simorin.ortu.LihatKunjunganOrangTuaActivity;
import com.skariga.simorin.perusahaan.AccAbsenPemPerusahaanActivity;
import com.skariga.simorin.perusahaan.AccJurnalPemPerusahaanActivity;
import com.skariga.simorin.sekolah.EvaluasiKunjunganPemSekolahActivity;
import com.skariga.simorin.sekolah.RekapAbsenPemSekolahActivity;
import com.skariga.simorin.sekolah.RekapJurnalPemSekolahActivity;
import com.skariga.simorin.siswa.AbsenSiswaActivity;
import com.skariga.simorin.siswa.ListJurnalSiswaActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardActivity extends AppCompatActivity {

    TextView nama, psb, tv1, tv2, tv3;
    ImageView iv1, iv2, iv3, logout;
    RelativeLayout rl1, rl2, rl3;
    SessionManager sessionManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);
            setStatusBarGradiant(DashboardActivity.this);

            nama = findViewById(R.id.tv_nama);
            psb = findViewById(R.id.tv_psb);
            tv1 = findViewById(R.id.tv_1);
            tv2 = findViewById(R.id.tv_2);
            tv3 = findViewById(R.id.tv_3);
            iv1 = findViewById(R.id.iv_1);
            iv2 = findViewById(R.id.iv_2);
            iv3 = findViewById(R.id.iv_3);
            rl1 = findViewById(R.id.btn_1);
            rl2 = findViewById(R.id.btn_2);
            rl3 = findViewById(R.id.btn_3);
            logout = findViewById(R.id.iv_logout);

            sessionManager = new SessionManager(this);
            sessionManager.checkLogin();

            HashMap<String, String> user = sessionManager.getUserDetail();
            String mPer = user.get(SessionManager.PSB);
            String mRole = user.get(SessionManager.ROLE);
            String mNama = user.get(SessionManager.NAMA);
            String mId = user.get(SessionManager.ID);

            nama.setText(mNama);
            psb.setText(mPer);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Yeay...")
                            .setContentText("Anda berhasil Logout!")
                            .show();

                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            sessionManager.logout();
                        }
                    }, 0, 2000);
                }
            });

            if (mRole.equals("Siswa")) {
                rl3.setVisibility(View.GONE);

                tv1.setText("Absensi Siswa");
                tv2.setText("Jurnal Harian");

                iv1.setImageResource(R.drawable.absen);
                iv2.setImageResource(R.drawable.jurnal);

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardActivity.this);

                rl2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, ListJurnalSiswaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    Location location = task.getResult();
                                    if (location != null) {
                                        try {
                                            Geocoder geocoder = new Geocoder(DashboardActivity.this, Locale.getDefault());
                                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            Intent i = new Intent(DashboardActivity.this, AbsenSiswaActivity.class);
                                            i.putExtra("latitude", Double.toString((double) addresses.get(0).getLatitude()));
                                            i.putExtra("longitude", Double.toString((double) addresses.get(0).getLongitude()));
                                            startActivity(i);
                                            finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Error...")
                                                    .setContentText(e.toString())
                                                    .show();
                                        }
                                    } else {
                                        new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Maaf...")
                                                .setContentText("Mohon periksa kembali KONEKSI INTERNET serta LOKASI anda, " +
                                                        "pastikan kedua fitur tersebut menyala, " +
                                                        "dan setelah itu silahkan ada buka GOOGLE MAPS pastikan LOKASI anda terditeksi / terbaca, " +
                                                        "lalu silahkan anda buka kembali aplikasi ini.")
                                                .show();
                                    }
                                }
                            });
                        } else {
                            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                        }
                    }
                });
            } else if (mRole.equals("Pembimbing Sekolah")) {
                tv1.setText("Rekap Absensi");
                tv2.setText("Rekap Jurnal");
                tv3.setText("Evaluasi Kunjungan");

                iv1.setImageResource(R.drawable.rekapabsen);
                iv2.setImageResource(R.drawable.rekapjurnal);
                iv3.setImageResource(R.drawable.evaluasikunjungan);

                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, RekapAbsenPemSekolahActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                rl2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, RekapJurnalPemSekolahActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                rl3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, EvaluasiKunjunganPemSekolahActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            } else if (mRole.equals("Pembimbing Perusahaan")) {
                rl3.setVisibility(View.GONE);

                tv1.setText("Acc Absensi");
                tv2.setText("Acc Jurnal");

                iv1.setImageResource(R.drawable.accabsen);
                iv2.setImageResource(R.drawable.accjurnal);

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardActivity.this);

                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    Location location = task.getResult();
                                    if (location != null) {
                                        try {
                                            Geocoder geocoder = new Geocoder(DashboardActivity.this, Locale.getDefault());
                                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            Intent i = new Intent(DashboardActivity.this, AccAbsenPemPerusahaanActivity.class);
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
                            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                        }
                    }
                });

                rl2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, AccJurnalPemPerusahaanActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

            } else if (mRole.equals("Orang Tua")) {
                tv1.setText("Lihat Absensi");
                tv2.setText("Lihat Jurnal");
                tv3.setText("Lihat Kunjungan");

                iv1.setImageResource(R.drawable.lihatabsen);
                iv2.setImageResource(R.drawable.lihatjurnal);
                iv3.setImageResource(R.drawable.lihatkunjungan);

                rl2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, LihatJurnalOrangTuaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, LihatAbsenOrangTuaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                rl3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DashboardActivity.this, LihatKunjunganOrangTuaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            } else {
                new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Akses ditolak!")
                        .show();
            }
        } catch (Exception ex) {
            new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error...")
                    .setContentText(ex.getMessage())
                    .show();
        }
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