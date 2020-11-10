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
import android.widget.TextView;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skyhope.showmoretextview.ShowMoreTextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LihatJurnalOrangTuaActivity extends AppCompatActivity {

    ShowMoreTextView kegiatan;
    ImageView kembali;
    TextView liha_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(LihatJurnalOrangTuaActivity.this);
        setContentView(R.layout.activity_lihat_jurnal_orang_tua);

        kegiatan = findViewById(R.id.kegiatan);
        kembali = findViewById(R.id.back);
        liha_detail = findViewById(R.id.lihat_detail);

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(LihatJurnalOrangTuaActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        liha_detail.setOnClickListener(v -> new SweetAlertDialog(LihatJurnalOrangTuaActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

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