package com.skariga.simorin.siswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.auth.SessionManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class JurnalKegiatanSiswaActivity extends AppCompatActivity {
    ImageView back;
    Date date;
    Button btn_submit;
    SimpleDateFormat format;
    EditText kegiatan_kerja, prosedur_pengerjaan, spesifikasi_bahan;
//    String URL_INPUTJURNAL = "https://simorin.malangcreativeteam.biz.id/api/input_jurnal";
    String URL_INPUTJURNAL = "http://192.168.10.226/SimorinLaravel/public/api/input_jurnal";
    SweetAlertDialog sweetAlertDialog, alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal_kegiatan_siswa);

        back = findViewById(R.id.back);
        btn_submit = findViewById(R.id.submit);
        kegiatan_kerja = findViewById(R.id.et_kerja);
        prosedur_pengerjaan = findViewById(R.id.et_prosedur);
        spesifikasi_bahan = findViewById(R.id.et_bahan);

        date = new Date();
        format = new SimpleDateFormat("E, dd MMMM yyyy");

        sweetAlertDialog = new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");

        final String mId = getIntent().getStringExtra("id");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweetAlertDialog.show();

                date = new Date();
                SimpleDateFormat time = new SimpleDateFormat("HH:mm");

                String waktu_pulang = time.format(date);
                String keg_kerja = kegiatan_kerja.getText().toString().trim();
                String pro_peng = prosedur_pengerjaan.getText().toString().trim();
                String spek_bahan = spesifikasi_bahan.getText().toString().trim();

                if (keg_kerja.isEmpty()) {
                    sweetAlertDialog.dismiss();

                    new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText("Kegiatan kerja harus diisi")
                            .show();
                } else if (pro_peng.isEmpty()) {
                    sweetAlertDialog.dismiss();

                    new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText("Prosedur pengerjaan harus diisi")
                            .show();
                } else if (spek_bahan.isEmpty()) {
                    sweetAlertDialog.dismiss();

                    new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal...")
                            .setContentText("Spesifikasi bahan harus diisi")
                            .show();
                } else {
                    InputJurnal(mId, keg_kerja, pro_peng, spek_bahan, waktu_pulang);
                }

            }

            private void InputJurnal(final String id, final String kegiatan_kerja, final String prosedur_pengerjaan, final String spesifikasi_bahan, final String waktu_pulang) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INPUTJURNAL,
                        response -> {
                            try {
                                sweetAlertDialog.dismiss();

                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("RESULT");
                                String message = jsonObject.getString("MESSAGE");

                                if (result.equals("OK")) {
                                    new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Yaey...")
                                            .setContentText(message)
                                            .setConfirmClickListener(sweetAlertDialog -> {
                                                Intent i = new Intent(JurnalKegiatanSiswaActivity.this, DashboardActivity.class);
                                                startActivity(i);
                                                finish();
                                            })
                                            .show();
                                } else {
                                    new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Gagal...")
                                            .setContentText(message)
                                            .show();
                                }
                            } catch (Exception ex) {
                                sweetAlertDialog.dismiss();

                                new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error")
                                        .setContentText(ex.toString())
                                        .show();
                            }
                        },
                        error -> {
                            sweetAlertDialog.dismiss();

                            new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(error.toString())
                                    .show();
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", id);
                        params.put("kegiatan_kerja", kegiatan_kerja);
                        params.put("prosedur_pengerjaan", prosedur_pengerjaan);
                        params.put("spesifikasi_bahan", spesifikasi_bahan);
                        params.put("waktu_pulang", waktu_pulang);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(JurnalKegiatanSiswaActivity.this);
                requestQueue.add(stringRequest);
            }
        });

        back.setOnClickListener(v -> {
            Intent i = new Intent(JurnalKegiatanSiswaActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });
    }
}