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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.RekapJurnal;
import com.skariga.simorin.model.Siswa;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RekapJurnalPemSekolahActivity extends AppCompatActivity implements RekapJurnalPemSekolahView {

    ImageView kembali;
    RelativeLayout perusahaan, siswa, ttd_jurnal, jurnal;
    RecyclerView rv_perusahaan, rv_siswa, rv_jurnal;
    Button semua, dipilih;

    List<Perusahaan> perusahaans;
    List<Siswa> siswas;
    List<RekapJurnal> rekapJurnals;

    RekapJurnalPemSekolahPresenter presenter;
    RekapJurnalPemSekolahAdapterp adapterp;
    RekapJurnalPemSekolahAdapterr adapterr;
    RekapJurnalPemSekolahAdapters adapters;
    RekapJurnalPemSekolahAdapters.ItemClicklistener itemClicklisteners;
    RekapJurnalPemSekolahAdapterr.ItemClickListener itemClickListenerr;
    RekapJurnalPemSekolahAdapterp.ItemClickListener itemClickListenerp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(RekapJurnalPemSekolahActivity.this);
        setContentView(R.layout.activity_rekap_jurnal_pem_sekolah);

        kembali = findViewById(R.id.back);
        perusahaan = findViewById(R.id.rl_perusahaan);
        siswa = findViewById(R.id.rl_siswa);
        ttd_jurnal = findViewById(R.id.rl_ttd_jurnal);
        rv_jurnal = findViewById(R.id.rv_jurnal);
        rv_perusahaan = findViewById(R.id.rv_perusahaan);
        rv_siswa = findViewById(R.id.rv_siswa);
        semua = findViewById(R.id.btn_semua);
        dipilih = findViewById(R.id.btn_dipilih);

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(RekapJurnalPemSekolahActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        semua.setOnClickListener(v -> new SweetAlertDialog(RekapJurnalPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        dipilih.setOnClickListener(v -> new SweetAlertDialog(RekapJurnalPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        String mId = getIntent().getStringExtra("id");

        rv_perusahaan.setLayoutManager(new LinearLayoutManager(this));
        rv_jurnal.setLayoutManager(new LinearLayoutManager(this));
        rv_siswa.setLayoutManager(new LinearLayoutManager(this));

        presenter = new RekapJurnalPemSekolahPresenter(this);
        presenter.getPerusahaan(mId);

        itemClickListenerp = ((view, position) -> {
            String id = Integer.toString(perusahaans.get(position).getId_perusahaan());
            presenter.getSiswa(id);
            perusahaan.setVisibility(View.GONE);
            siswa.setVisibility(View.VISIBLE);
        });

        itemClicklisteners = ((view, position) -> {
            String id = Integer.toString(siswas.get(position).getId_siswa());
            presenter.getRekapJurnal(id);
            siswa.setVisibility(View.GONE);
            ttd_jurnal.setVisibility(View.VISIBLE);
            rv_jurnal.setVisibility(View.VISIBLE);
        });

        itemClickListenerr = ((view, position) -> {
            String tanggal = rekapJurnals.get(position).getTanggal();
            String masuk = rekapJurnals.get(position).getWaktu_masuk();
            String pulang = rekapJurnals.get(position).getWaktu_pulang();

            Toast.makeText(this, tanggal + " / " + masuk + " - " + pulang, Toast.LENGTH_LONG).show();

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
    public void onGetResultPerusahaan(List<Perusahaan> perusahaans) {
        adapterp = new RekapJurnalPemSekolahAdapterp(this, perusahaans, itemClickListenerp);
        adapterp.notifyDataSetChanged();
        rv_perusahaan.setAdapter(adapterp);

        this.perusahaans = perusahaans;
    }

    @Override
    public void onGetResultSiswa(List<Siswa> siswas) {
        adapters = new RekapJurnalPemSekolahAdapters(this, siswas, itemClicklisteners);
        adapters.notifyDataSetChanged();
        rv_siswa.setAdapter(adapters);

        this.siswas = siswas;
    }

    @Override
    public void onGetResultRekap(List<RekapJurnal> jurnals) {
        adapterr = new RekapJurnalPemSekolahAdapterr(this, jurnals, itemClickListenerr);
        adapterr.notifyDataSetChanged();
        rv_jurnal.setAdapter(adapterr);

        rekapJurnals = jurnals;

    }

    @Override
    public void onErrorResult(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error...")
                .setContentText(message)
                .show();
    }
}