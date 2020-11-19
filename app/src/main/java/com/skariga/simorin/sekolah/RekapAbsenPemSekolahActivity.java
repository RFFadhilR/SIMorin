package com.skariga.simorin.sekolah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.RekapAbsen;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RekapAbsenPemSekolahActivity extends AppCompatActivity implements RekapAbsenPemSekolahView{

    RelativeLayout perusahaan, absen;
    RecyclerView rv_persuahaan, rv_absen;
    ImageView kembali;
    Button export;

    List<Perusahaan> perusahaans;
    List<RekapAbsen> rekapAbsens;

    RekapAbsenPemSekolahAdapter adapter;
    RekapAbsenPemSekolahAdapters adapters;
    RekapAbsenPemSekolahPresenter presenter;
    RekapAbsenPemSekolahAdapter.ItemClickListener itemClickListener;
    RekapAbsenPemSekolahAdapters.ItemClickListener itemClickListeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(RekapAbsenPemSekolahActivity.this);
        setContentView(R.layout.activity_rekap_absen_pem_sekolah);

        perusahaan = findViewById(R.id.rl_perusahaan);
        absen = findViewById(R.id.rl_absen);
        rv_persuahaan = findViewById(R.id.rv_perusahaan);
        rv_absen = findViewById(R.id.rv_absen);
        kembali = findViewById(R.id.back);
        export = findViewById(R.id.btn_export);

        export.setOnClickListener(v -> new SweetAlertDialog(RekapAbsenPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(RekapAbsenPemSekolahActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        rv_persuahaan.setLayoutManager(new LinearLayoutManager(this));
        rv_absen.setLayoutManager(new LinearLayoutManager(this));

        String mId = getIntent().getStringExtra("id");

        presenter = new RekapAbsenPemSekolahPresenter(this);
        presenter.getPerusahaan(mId);

        itemClickListener = ((view, position) -> {
            String id_perusahaan = Integer.toString(perusahaans.get(position).getId_perusahaan());
            perusahaan.setVisibility(View.GONE);
            absen.setVisibility(View.VISIBLE);
            presenter.getRekap(id_perusahaan);
        });

        itemClickListeners = ((view, position) -> {
            String nis = Integer.toString(rekapAbsens.get(position).getNis());
            String nama = rekapAbsens.get(position).getNama();
            Toast.makeText(this, nis + nama, Toast.LENGTH_LONG).show();
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
    public void onGetResultPer(List<Perusahaan> perusahaans) {
        adapter = new RekapAbsenPemSekolahAdapter(this, perusahaans, itemClickListener);
        adapter.notifyDataSetChanged();
        rv_persuahaan.setAdapter(adapter);

        this.perusahaans = perusahaans;
    }

    @Override
    public void onGetReseltRek(List<RekapAbsen> rekapAbsens) {
        adapters = new RekapAbsenPemSekolahAdapters(this, rekapAbsens, itemClickListeners);
        adapters.notifyDataSetChanged();
        rv_absen.setAdapter(adapters);

        this.rekapAbsens = rekapAbsens;
    }

    @Override
    public void onErrorResult(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error...")
                .setContentText(message)
                .show();
    }
}