package com.skariga.simorin.ortu;

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
import com.skariga.simorin.perusahaan.DashboardPemPerusahaanActivity;
import com.skariga.simorin.siswa.DashboardSiswaActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardOrangTuaActivity extends AppCompatActivity {

    RelativeLayout absen, kunjungan, jurnal;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(DashboardOrangTuaActivity.this);
        setContentView(R.layout.activity_dashboard_orang_tua);

        absen = findViewById(R.id.lihat_absen);
        kunjungan = findViewById(R.id.lihat_kunjungan);
        logout = findViewById(R.id.iv_logout);
        jurnal = findViewById(R.id.lihat_jurnal);

        jurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardOrangTuaActivity.this, LihatJurnalOrangTuaActivity.class);
                startActivity(i);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DashboardOrangTuaActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Yeay...")
                        .setContentText("Anda berhasil Logout!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(DashboardOrangTuaActivity.this, LoginActivity.class);
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
                Intent i = new Intent(DashboardOrangTuaActivity.this, LihatAbsenOrangTuaActivity.class);
                startActivity(i);
                finish();
            }
        });

        kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardOrangTuaActivity.this, LihatKunjunganOrangTuaActivity.class);
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