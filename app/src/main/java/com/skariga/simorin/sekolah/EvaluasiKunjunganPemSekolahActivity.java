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
import android.widget.EditText;
import android.widget.ImageView;

import com.skariga.simorin.R;
import com.skariga.simorin.siswa.JurnalKegiatanSiswaActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EvaluasiKunjunganPemSekolahActivity extends AppCompatActivity {

    EditText perusahaan, division, tanggal, nis, nama;
    Button siswa, pembimbing;
    ImageView kembali;
    Date date;
    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(EvaluasiKunjunganPemSekolahActivity.this);
        setContentView(R.layout.activity_evaluasi_kunjungan_pem_sekolah);

        perusahaan = findViewById(R.id.et_perusahaan);
        division = findViewById(R.id.et_devision);
        tanggal = findViewById(R.id.et_tanggal);
        nis = findViewById(R.id.et_nis);
        nama = findViewById(R.id.et_nama);
        siswa = findViewById(R.id.btn_ttd_siswa);
        pembimbing = findViewById(R.id.btn_ttd_pembimbing);
        kembali = findViewById(R.id.back);

        date = new Date();
        format = new SimpleDateFormat("E/dd/MMMM/yyyy");

        perusahaan.setText("PT. INTERNUSA CIPTA SOLUSI PERDANA");
        division.setText("TIMUR");
        tanggal.setText(format.format(date));
        nis.setText("19828");
        nama.setText("Risqullah Fani FadhilRif'at");

        siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(EvaluasiKunjunganPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        pembimbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(EvaluasiKunjunganPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EvaluasiKunjunganPemSekolahActivity.this, DashboardPemSekolahActivity.class);
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