package com.skariga.simorin.perusahaan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skariga.simorin.R;
import com.skyhope.showmoretextview.ShowMoreTextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AccJurnalPemPerusahaanActivity extends AppCompatActivity {

    ShowMoreTextView kegiatan;
    ImageView kembali;
    TextView lihat_detail;
    Button semua, dipilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(AccJurnalPemPerusahaanActivity.this);
        setContentView(R.layout.activity_acc_jurnal_pem_perusahaan);

        kegiatan = findViewById(R.id.kegiatan);
        kembali = findViewById(R.id.back);
        lihat_detail = findViewById(R.id.lihat_detail);
        semua = findViewById(R.id.setujui_semua);
        dipilih = findViewById(R.id.setujui_dipilih);

        semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(AccJurnalPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        dipilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(AccJurnalPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        lihat_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(AccJurnalPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccJurnalPemPerusahaanActivity.this, DashboardPemPerusahaanActivity.class);
                startActivity(i);
                finish();
            }
        });

        kegiatan.setShowingLine(2);
        kegiatan.addShowLessText("Lebih Dikit");
        kegiatan.addShowMoreText("Lebih Banyak");

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