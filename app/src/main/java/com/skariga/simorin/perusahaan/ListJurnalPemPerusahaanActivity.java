package com.skariga.simorin.perusahaan;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.model.JurnalPerusahaan;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListJurnalPemPerusahaanActivity extends AppCompatActivity implements ListJurnalPemPerusahaanView {

    ShowMoreTextView kegiatan;
    ImageView kembali;
    Button semua, dipilih;
    RecyclerView recyclerView;
    CheckBox checkBox;
    SweetAlertDialog sweetAlertDialog;

    List<JurnalPerusahaan> jurnalPerusahaan;

    ListJurnalPemPerusahaanAdapter adapter;
    ListJurnalPemPerusahaanPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(ListJurnalPemPerusahaanActivity.this);
        setContentView(R.layout.activity_list_jurnal_pem_perusahaan);

        kegiatan = findViewById(R.id.kegiatan);
        kembali = findViewById(R.id.back);
        semua = findViewById(R.id.ttd_semua);
        dipilih = findViewById(R.id.ttd_dipilih);
        recyclerView = findViewById(R.id.recycler_view);
        checkBox = findViewById(R.id.checkBox1);

        sweetAlertDialog = new SweetAlertDialog(ListJurnalPemPerusahaanActivity.this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");
        sweetAlertDialog.show();

        semua.setOnClickListener(v -> {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < adapter.getAllData().size(); i++) {
                stringBuilder.append(adapter.getAllData().get(i).getId_jurnal());
                stringBuilder.append(", ");
            }

            Toast.makeText(this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
        });

        dipilih.setOnClickListener(v -> {
            if (adapter.getSelected().size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < adapter.getSelected().size(); i++) {
                    stringBuilder.append(adapter.getSelected().get(i).getId_jurnal());
                    stringBuilder.append(", ");
                }
                Toast.makeText(this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "KOSONG!", Toast.LENGTH_SHORT).show();
            }
        });

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(ListJurnalPemPerusahaanActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String mId = getIntent().getStringExtra("id");

        presenter = new ListJurnalPemPerusahaanPresenter(this);
        presenter.getDatas(mId);
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
    public void onGetResults(List<JurnalPerusahaan> jurnalPerusahaans) {
        adapter = new ListJurnalPemPerusahaanAdapter(this, jurnalPerusahaans);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        jurnalPerusahaan = jurnalPerusahaans;
        sweetAlertDialog.dismiss();
    }

    @Override
    public void onErrorLoading(String message) {
        new SweetAlertDialog(ListJurnalPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error...")
                .setContentText(message)
                .setConfirmClickListener(sweetAlertDialog -> {
                    Intent i = new Intent(this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                })
                .show();
    }
}