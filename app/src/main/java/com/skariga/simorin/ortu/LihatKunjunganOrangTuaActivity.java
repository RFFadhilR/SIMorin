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

public class LihatKunjunganOrangTuaActivity extends AppCompatActivity {

    ImageView kembali;
    TextView lihat_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(LihatKunjunganOrangTuaActivity.this);
        setContentView(R.layout.activity_lihat_kunjungan_orang_tua);

        kembali = findViewById(R.id.back);
        lihat_detail = findViewById(R.id.lihat_detail);

        lihat_detail.setOnClickListener(v -> new SweetAlertDialog(LihatKunjunganOrangTuaActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(LihatKunjunganOrangTuaActivity.this, DashboardActivity.class);
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