package com.skariga.simorin.sekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.LoginActivity;
import com.skariga.simorin.auth.SessionManager;
import com.skariga.simorin.ortu.DashboardOrangTuaActivity;
import com.skariga.simorin.siswa.DashboardSiswaActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardPemSekolahActivity extends AppCompatActivity {

    RelativeLayout absen, jurnal, kunjungan;
    ImageView logout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(DashboardPemSekolahActivity.this);
        setContentView(R.layout.activity_dashboard_pem_sekolah);

        absen = findViewById(R.id.rekap_absen);
        jurnal = findViewById(R.id.rekap_jurnal);
        kunjungan = findViewById(R.id.evaluasi);
        logout = findViewById(R.id.iv_logout);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin("Pembimbing Sekolah");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DashboardPemSekolahActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Yeay...")
                        .setContentText("Anda berhasil Logout!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(DashboardPemSekolahActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .show();
            }
        });

        absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardPemSekolahActivity.this, RekapAbsenPemSekolahActivity.class);
                startActivity(i);
                finish();
            }
        });

        jurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardPemSekolahActivity.this, RekapJurnalPemSekolahActivity.class);
                startActivity(i);
                finish();
            }
        });

        kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardPemSekolahActivity.this, EvaluasiKunjunganPemSekolahActivity.class);
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