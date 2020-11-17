package com.skariga.simorin.ortu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.skariga.simorin.model.JurnalOrtu;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LihatJurnalOrangTuaActivity extends AppCompatActivity implements LihatJurnalOrangTuaView {

    ShowMoreTextView kegiatan;
    ImageView kembali;
    RecyclerView recyclerView;

    List<JurnalOrtu> jurnalOrtus;

    LihatJurnalOrangTuaAdapter adapter;
    LihatJurnalOrangTuaPresenter presenter;
    LihatJurnalOrangTuaAdapter.ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(LihatJurnalOrangTuaActivity.this);
        setContentView(R.layout.activity_lihat_jurnal_orang_tua);

        kegiatan = findViewById(R.id.kegiatan);
        kembali = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycler_view);

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(LihatJurnalOrangTuaActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String mId = getIntent().getStringExtra("id");

        presenter = new LihatJurnalOrangTuaPresenter(this);
        presenter.getJurnal(mId);

        itemClickListener = ((view, position) -> {
            String tanggal = jurnalOrtus.get(position).getTanggal();
            String wmasuk = jurnalOrtus.get(position).getWaktu_masuk();
            String wpulang = jurnalOrtus.get(position).getWaktu_pulang();
            String kegiatan = jurnalOrtus.get(position).getKegiatan();
            String prosedur = jurnalOrtus.get(position).getProsedur();
            String spek = jurnalOrtus.get(position).getSpek();

            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(tanggal + " / " + wmasuk + " - " + wpulang)
                    .setContentText("Kegiatan Kerja (Perkerjaan)\n" + kegiatan
                            + "\nProsedur Pengerjaan Trouble Shooting\n" + prosedur
                            + "\nSpesifikasi Bahan dan Peralatan Kerja\n" + spek)
                    .show();

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

    @Override
    public void onGetResult(List<JurnalOrtu> jurnalOrtus) {
        adapter = new LihatJurnalOrangTuaAdapter(this, jurnalOrtus, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        this.jurnalOrtus = jurnalOrtus;
    }

    @Override
    public void onErrorResult(String message) {
        new SweetAlertDialog(LihatJurnalOrangTuaActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error...")
                .setContentText(message)
                .show();
    }
}