package com.skariga.simorin.auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skariga.simorin.R;
import com.skariga.simorin.ortu.LihatAbsenOrangTuaActivity;
import com.skariga.simorin.ortu.LihatJurnalOrangTuaActivity;
import com.skariga.simorin.ortu.LihatKunjunganOrangTuaActivity;
import com.skariga.simorin.perusahaan.ListAbsenPemPerusahaanActivity;
import com.skariga.simorin.perusahaan.ListJurnalPemPerusahaanActivity;
import com.skariga.simorin.sekolah.EvaluasiKunjunganPemSekolahActivity;
import com.skariga.simorin.sekolah.RekapAbsenPemSekolahActivity;
import com.skariga.simorin.sekolah.RekapJurnalPemSekolahActivity;
import com.skariga.simorin.siswa.AbsenSiswaActivity;
import com.skariga.simorin.siswa.JurnalKegiatanSiswaActivity;
import com.skariga.simorin.siswa.ListJurnalSiswaActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardActivity extends AppCompatActivity {

    TextView nama, psb, tv1, tv2, tv3;
    ImageView iv1, iv2, iv3, logout;
    RelativeLayout rl1, rl2, rl3;
    SessionManager sessionManager;
    FusedLocationProviderClient fusedLocationProviderClient;
//    String URL_FALIDASI = "https://simorin.malangcreativeteam.biz.id/api/get-jurnal-siswa";
    String URL_FALIDASI = "http://192.168.10.226/SimorinLaravel/public/api/get-jurnal-siswa";
    SweetAlertDialog sweetAlertDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
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

            sweetAlertDialog = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");

            logout.setOnClickListener(v -> new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Yeay...")
                    .setContentText("Anda berhasil Logout!")
                    .setConfirmClickListener(sweetAlertDialog -> sessionManager.logout())
                    .show());

            if (mRole.equals("Siswa")) {
                tv1.setText("Absensi Siswa");
                tv2.setText("Jurnal Harian");
                tv3.setText("Histori Jurnal Harian");

                iv1.setImageResource(R.drawable.absen);
                iv1.setTooltipText("Absensi Siswa");
                iv2.setImageResource(R.drawable.jurnal);
                iv2.setTooltipText("Jurnal Harian");
                iv3.setImageResource(R.drawable.lihatjurnal);
                iv3.setTooltipText("Histori Jurnal Harian");

                rl1.setOnClickListener(v -> {
                    if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardActivity.this);

                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
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
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        }, 44);
                    }
                });

                rl2.setOnClickListener(v -> {
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    sweetAlertDialog.show();
                    getData(mId, format.format(date));
                });

                rl3.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, ListJurnalSiswaActivity.class);
                    i.putExtra("id", mId);
                    startActivity(i);
                    finish();
                });

            } else if (mRole.equals("Pembimbing Sekolah")) {
                tv1.setText("Rekap Absensi");
                tv2.setText("Rekap Jurnal");
                tv3.setText("Evaluasi Kunjungan");

                iv1.setImageResource(R.drawable.rekapabsen);
                iv1.setTooltipText("Rekap Absensi");
                iv2.setImageResource(R.drawable.rekapjurnal);
                iv2.setTooltipText("Rekap Jurnal");
                iv3.setImageResource(R.drawable.evaluasikunjungan);
                iv3.setTooltipText("Evaluasi Kunjungan");

                rl1.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, RekapAbsenPemSekolahActivity.class);
                    i.putExtra("id", mId);
                    startActivity(i);
                    finish();
                });

                rl2.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, RekapJurnalPemSekolahActivity.class);
                    startActivity(i);
                    finish();
                });

                rl3.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, EvaluasiKunjunganPemSekolahActivity.class);
                    startActivity(i);
                    finish();
                });
            } else if (mRole.equals("Pembimbing Perusahaan")) {
                rl3.setVisibility(View.GONE);

                tv1.setText("Acc Absensi");
                tv2.setText("Acc Jurnal");

                iv1.setImageResource(R.drawable.accabsen);
                iv1.setTooltipText("Acc Absensi");
                iv2.setImageResource(R.drawable.accjurnal);
                iv2.setTooltipText("Acc Jurnal");

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardActivity.this);

                rl1.setOnClickListener(v -> {
                    if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                            Location location = task.getResult();
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(DashboardActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    Intent i = new Intent(DashboardActivity.this, ListAbsenPemPerusahaanActivity.class);

                                    i.putExtra("latitude", Double.toString(addresses.get(0).getLatitude()));
                                    i.putExtra("longitude", Double.toString(addresses.get(0).getLongitude()));
                                    i.putExtra("id", mId);

                                    startActivity(i);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        }, 44);
                    }
                });

                rl2.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, ListJurnalPemPerusahaanActivity.class);
                    i.putExtra("id", mId);
                    startActivity(i);
                    finish();
                });
            } else if (mRole.equals("Orang Tua")) {
                tv1.setText("Lihat Absensi");
                tv2.setText("Lihat Jurnal");
                tv3.setText("Lihat Kunjungan");

                iv1.setImageResource(R.drawable.lihatabsen);
                iv1.setTooltipText("Lihat Absensi");
                iv2.setImageResource(R.drawable.lihatjurnal);
                iv2.setTooltipText("Lihat Jurnal");
                iv3.setImageResource(R.drawable.lihatkunjungan);
                iv3.setTooltipText("Lihat Kunjungan");

                rl2.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, LihatJurnalOrangTuaActivity.class);
                    i.putExtra("id", mId);
                    startActivity(i);
                    finish();
                });

                rl1.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, LihatAbsenOrangTuaActivity.class);
                    i.putExtra("id", mId);
                    startActivity(i);
                    finish();
                });

                rl3.setOnClickListener(v -> {
                    Intent i = new Intent(DashboardActivity.this, LihatKunjunganOrangTuaActivity.class);
                    startActivity(i);
                    finish();
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

    private void getData(String id, String tanggal) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FALIDASI,
                response -> {
                    try {
                        sweetAlertDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("RESULT");
                        String message = jsonObject.getString("MESSAGE"); // "Anda sudah memasukkan jurnal kegiatan pada hari ini dan juga berhasil untuk absen pulang!"

                        if (result.equals("KOSONG")) {
                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Maaf...")
                                    .setContentText("Apakah anda sudah waktunya pulang kerja?")
                                    .setConfirmButton("Iyaa!", sweetAlertDialog -> {
                                        Intent i = new Intent(DashboardActivity.this, JurnalKegiatanSiswaActivity.class);
                                        i.putExtra("id", id);
                                        startActivity(i);
                                        finish();
                                    })
                                    .setCancelText("Belum!")
                                    .show();
                        } else {
                            new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Maaf...")
                                    .setContentText(message)
                                    .show();
                        }
                    } catch (Exception ex) {
                        new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setContentText(ex.toString())
                                .show();
                    }
                },
                error -> {
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText(error.toString())
                            .show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_siswa", id);
                params.put("tanggal", tanggal);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(stringRequest);
    }

}