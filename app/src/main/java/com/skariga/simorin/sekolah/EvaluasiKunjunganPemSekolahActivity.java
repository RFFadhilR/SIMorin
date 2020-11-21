package com.skariga.simorin.sekolah;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EvaluasiKunjunganPemSekolahActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText division, tanggal;
    Button btn_siswa, pembimbing;
    Spinner sp_perusahaan, sp_siswa;
    ImageView kembali;
    Date date;
    SimpleDateFormat format;
    LinearLayout ll_masalah, ll_lksp;
    RadioGroup rg_jurnal, rg_lksp, rg_apd, rg_kinerja, rg_rambut, rg_penampilan;

    ArrayList<String> perusahaan = new ArrayList<>();
    ArrayList<String> siswa = new ArrayList<>();
    ArrayAdapter<String> perusahaanAdapter;
    ArrayAdapter<String> siswaAdapter;

    RequestQueue requestQueue;

    public static String URL_PERUSAHAAN = "https://simorin.malangcreativeteam.biz.id/api/perusahaan";
    public static String URL_SISWA = "https://simorin.malangcreativeteam.biz.id/api/siswa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(EvaluasiKunjunganPemSekolahActivity.this);
        setContentView(R.layout.activity_evaluasi_kunjungan_pem_sekolah);

        division = findViewById(R.id.et_devision);
        tanggal = findViewById(R.id.et_tanggal);
        btn_siswa = findViewById(R.id.btn_ttd_siswa);
        pembimbing = findViewById(R.id.btn_ttd_pembimbing);
        kembali = findViewById(R.id.back);
        sp_perusahaan = findViewById(R.id.perusahaan);
        sp_siswa = findViewById(R.id.siswa);
        ll_masalah = findViewById(R.id.permasalahan);
        ll_lksp = findViewById(R.id.ly_lksp);
        rg_apd = findViewById(R.id.rg_apd);
        rg_jurnal = findViewById(R.id.rg_jurnal);
        rg_lksp = findViewById(R.id.rg_lksp);
        rg_kinerja = findViewById(R.id.rg_kinerja);
        rg_rambut = findViewById(R.id.rg_rambut);
        rg_penampilan = findViewById(R.id.rg_penampilan);

        requestQueue = Volley.newRequestQueue(this);

        date = new Date();
        format = new SimpleDateFormat("E/dd/MMMM/yyyy");

        tanggal.setText(format.format(date));

        btn_siswa.setOnClickListener(v -> new SweetAlertDialog(EvaluasiKunjunganPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        pembimbing.setOnClickListener(v -> new SweetAlertDialog(EvaluasiKunjunganPemSekolahActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(EvaluasiKunjunganPemSekolahActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_PERUSAHAAN, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("PERUSAHAAN");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String nama_perusahaan = jsonObject.getString("NAMA_PERUSAHAAN");
                            perusahaan.add(nama_perusahaan);
                            perusahaanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, perusahaan);
                            perusahaanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_perusahaan.setAdapter(perusahaanAdapter);
                        }
                    } catch (Exception e) {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(e.toString())
                                .show();
                    }
                }, error -> {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error...")
                    .setContentText(error.toString())
                    .show();
        });
        requestQueue.add(jsonObjectRequest);
        sp_perusahaan.setOnItemClickListener(this);

        rg_apd.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.a_kurang) {
                ll_masalah.setVisibility(View.VISIBLE);
            } else {
                ll_masalah.setVisibility(View.GONE);
            }
        });

        rg_penampilan.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.p_kurang) {
                ll_masalah.setVisibility(View.VISIBLE);
            } else {
                ll_masalah.setVisibility(View.GONE);
            }
        });

        rg_rambut.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.r_kurang) {
                ll_masalah.setVisibility(View.VISIBLE);
            } else {
                ll_masalah.setVisibility(View.GONE);
            }
        });

        rg_kinerja.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.k_kurang) {
                ll_masalah.setVisibility(View.VISIBLE);
            } else {
                ll_masalah.setVisibility(View.GONE);
            }
        });

        rg_lksp.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.l_kurang) {
                ll_masalah.setVisibility(View.VISIBLE);
            } else {
                ll_masalah.setVisibility(View.GONE);
            }
        });

        rg_jurnal.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.j_kurang) {
                ll_masalah.setVisibility(View.VISIBLE);
            } else {
                ll_masalah.setVisibility(View.GONE);
            }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.perusahaan) {
            siswaAdapter.clear();
            String selectedPers = parent.getSelectedItem().toString();
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_SISWA, null,
                    response -> {
                        try {
                            JSONArray jsonArray = response.getJSONArray("SISWA");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("NAMA");
                                siswa.add(nama);
                                siswaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, siswa);
                                siswaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_siswa.setAdapter(siswaAdapter);
                                ll_lksp.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(e.toString())
                                    .show();
                        }
                    },
                    error -> {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(error.toString())
                                .show();
                    });
            requestQueue.add(jsonObjectRequest);
        }
    }
}