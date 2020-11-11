package com.skariga.simorin.ortu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LihatAbsenOrangTuaActivity extends AppCompatActivity {

    TextView tv_lokasi;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(LihatAbsenOrangTuaActivity.this);
        setContentView(R.layout.activity_lihat_absen_orang_tua);

        tv_lokasi = findViewById(R.id.lihat_lokasi);
        kembali = findViewById(R.id.back);

        tv_lokasi.setOnClickListener(v -> new SweetAlertDialog(LihatAbsenOrangTuaActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(LihatAbsenOrangTuaActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
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