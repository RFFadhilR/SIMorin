package com.skariga.simorin.siswa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.skariga.simorin.R;
import com.skyhope.showmoretextview.ShowMoreTextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListJurnalSiswaActivity extends AppCompatActivity {

    ShowMoreTextView showMoreTextView;
    Button tambah, cari;
    TextView detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(ListJurnalSiswaActivity.this);
        setContentView(R.layout.activity_list_jurnal_siswa);

        showMoreTextView = findViewById(R.id.kegiatan);
        tambah = findViewById(R.id.btn_tambah);
        cari = findViewById(R.id.btn_cari);
        detail = findViewById(R.id.lihat_detail);

        tambah.setOnClickListener(view -> {
            Intent i = new Intent(ListJurnalSiswaActivity.this, JurnalKegiatanSiswaActivity.class);
            startActivity(i);
            finish();
        });

        cari.setOnClickListener(view -> new SweetAlertDialog(ListJurnalSiswaActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        detail.setOnClickListener(view -> new SweetAlertDialog(ListJurnalSiswaActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        showMoreTextView.setShowingLine(2);
        showMoreTextView.addShowLessText("Lebih Dikit");
        showMoreTextView.addShowMoreText("Lebih Banyak");

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