package com.skariga.simorin.siswa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.model.JurnalPerusahaan;
import com.skyhope.showmoretextview.ShowMoreTextView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListJurnalSiswaActivity extends AppCompatActivity implements ListJurnalSiswaView {

    Button cari;
    ImageView kembali;
    RecyclerView recyclerView;
    SweetAlertDialog sweetAlertDialog;
    AlertDialog.Builder alertDialog;
    EditText et_mulai, et_sampai;

    String mId;
    List<JurnalPerusahaan> jurnalPerusahaans;

    ListJurnalSiswaPresenter presenter;
    ListJurnalSiswaAdapter adapter;
    ListJurnalSiswaAdapter.ItemClickListener itemClickListener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(ListJurnalSiswaActivity.this);
        setContentView(R.layout.activity_list_jurnal_siswa);

        cari = findViewById(R.id.btn_cari);
        kembali = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycler_view);
        et_mulai = findViewById(R.id.et_mulai);
        et_sampai = findViewById(R.id.et_sampai);

        mId = getIntent().getStringExtra("id");
        presenter = new ListJurnalSiswaPresenter(this);

        et_mulai.setOnClickListener(v -> {
            final Calendar today = Calendar.getInstance();
            int ynow = today.get(Calendar.YEAR);
            int mnow = today.get(Calendar.MONTH);
            int dnow = today.get(Calendar.DATE);

            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener) (view, year, month, dayOfMonth) -> {
                today.set(year, month, dayOfMonth);
                et_mulai.setText(simpleDateFormat.format(today.getTime()));
            }, ynow, mnow, dnow);
            datePickerDialog.setTitle("Pilih tanggal awal...");
            datePickerDialog.show();
        });

        et_sampai.setOnClickListener(v -> {
            final Calendar today = Calendar.getInstance();
            int ynow = today.get(Calendar.YEAR);
            int mnow = today.get(Calendar.MONTH);
            int dnow = today.get(Calendar.DATE);

            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener) (view, year, month, dayOfMonth) -> {
                today.set(year, month, dayOfMonth);
                et_sampai.setText(simpleDateFormat.format(today.getTime()));
            }, ynow, mnow, dnow);
            datePickerDialog.setTitle("Pilih tanggal akhir...");
            datePickerDialog.show();
        });

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        cari.setOnClickListener(view -> {
            String mulai = et_mulai.getText().toString().trim();
            String sampai = et_sampai.getText().toString().trim();

            sweetAlertDialog.setTitleText("Loading...").show();

            presenter.getSearch(mId, mulai, sampai);

        });

        sweetAlertDialog = new SweetAlertDialog(ListJurnalSiswaActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading...").show();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.getJurnalSiswa(mId);

        itemClickListener = ((view, position) -> {
            String kegiatan = jurnalPerusahaans.get(position).getKegiatan();
            String prosedur = jurnalPerusahaans.get(position).getProsedur();
            String spek = jurnalPerusahaans.get(position).getSpek();

            alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Kegiatan Kerja (Pekerjaan)\n" + kegiatan + "\n" + " "
                    + "\nProsedur Pengerjaan Trouble Shooting\n" + prosedur + "\n" + " "
                    + "\nSpesifikasi Bahan dan Peralatan Kerja\n" + spek);
            alertDialog.show();

//            new SweetAlertDialog(this)
//                    .setContentText("Kegiatan Kerja (Pekerjaan)\n" + kegiatan
//                            + "\n Prosedur Pengerjaan Trouble Shooting \n" + prosedur
//                            + "\n Spesifikasi Bahan dan Peralatan Kerja \n" + spek)
//                    .show();
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
    public void onGetResult(List<JurnalPerusahaan> jurnalPerusahaans) {
        adapter = new ListJurnalSiswaAdapter(this, jurnalPerusahaans, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        this.jurnalPerusahaans = jurnalPerusahaans;
        sweetAlertDialog.dismiss();
    }

    @Override
    public void onErrorResult(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error...")
                .setContentText(message)
                .setConfirmClickListener(sweetAlertDialog -> {
                    Intent i = new Intent(ListJurnalSiswaActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                })
                .show();
    }
}