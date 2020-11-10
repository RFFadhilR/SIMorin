package com.skariga.simorin.perusahaan;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.helper.Jurnal;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListJurnalPemPerusahaanActivity extends AppCompatActivity implements ListJurnalPemPerusahaanView{

    ShowMoreTextView kegiatan;
    ImageView kembali;
    Button semua, dipilih;
    RecyclerView recyclerView;

    List<Jurnal> jurnals;

    ListJurnalPemPerusahaanAdapter adapter;
    ListJurnalPemPerusahaanPresenter presenter;
    ListJurnalPemPerusahaanAdapter.ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(ListJurnalPemPerusahaanActivity.this);
        setContentView(R.layout.activity_list_jurnal_pem_perusahaan);

        kegiatan = findViewById(R.id.kegiatan);
        kembali = findViewById(R.id.back);
        semua = findViewById(R.id.setujui_semua);
        dipilih = findViewById(R.id.setujui_dipilih);
        recyclerView = findViewById(R.id.recycler_view);

        semua.setOnClickListener(v -> new SweetAlertDialog(ListJurnalPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        dipilih.setOnClickListener(v -> new SweetAlertDialog(ListJurnalPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(ListJurnalPemPerusahaanActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        recyclerView.setAdapter(adapter);

        String mId = getIntent().getStringExtra("id");

        presenter = new ListJurnalPemPerusahaanPresenter(this);
        presenter.getDatas(mId);

        itemClickListener = ((view, position) -> {
            String kegiatan = jurnals.get(position).getKegiatan();
            String prosedur = jurnals.get(position).getProsedur();
            String spek = jurnals.get(position).getSpek();

            new SweetAlertDialog(this)
                    .setContentText("Kegiatan Kerja (Perkerjaan) \n" + kegiatan +
                            "\nProsedur Pengerjaan Trouble Shooting\n" + prosedur +
                            "\nSpesifikasi Bahan dan Peralatan Kerja\n" + spek)
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
    public void onGetResults(List<Jurnal> jurnals) {

    }

    @Override
    public void onErrorLoading(String message) {

    }
}