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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.skariga.simorin.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RekapAbsenPemSekolahActivity extends AppCompatActivity {

    RelativeLayout perusahaan, absen;
    ImageView kembali;
    LinearLayout l1, l2, l3, l4, l5, l6, l7, l8;
    Button export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(RekapAbsenPemSekolahActivity.this);
        setContentView(R.layout.activity_rekap_absen_pem_sekolah);

        perusahaan = findViewById(R.id.rl_perusahaan);
        absen = findViewById(R.id.rl_absen);
        kembali = findViewById(R.id.back);
        export = findViewById(R.id.btn_export);
        l1 = findViewById(R.id.perusahaan2);
        l2 = findViewById(R.id.perusahaan3);
        l3 = findViewById(R.id.perusahaan4);
        l4 = findViewById(R.id.perusahaan5);
        l5 = findViewById(R.id.perusahaan6);
        l6 = findViewById(R.id.perusahaan7);
        l7 = findViewById(R.id.perusahaan8);
        l8 = findViewById(R.id.perusahaan9);

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(RekapAbsenPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RekapAbsenPemSekolahActivity.this, DashboardPemSekolahActivity.class);
                startActivity(i);
                finish();
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
            }
        });

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
            }
        });

        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
            }
        });

        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
            }
        });

        l8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perusahaan.setVisibility(View.GONE);
                absen.setVisibility(View.VISIBLE);
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