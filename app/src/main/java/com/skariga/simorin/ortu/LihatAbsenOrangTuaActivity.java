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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.model.AbsenOrtu;
import com.skariga.simorin.model.AbsenPerusahaan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LihatAbsenOrangTuaActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView kembali;
    TextView tv_nama, tv_perusahaan, tv_thadir, tv_talpha;
    RecyclerView recyclerView;
    GoogleMap map;

    List<AbsenOrtu> absenOrtus;

    LihatAbsenOrangTuaAdapter.ItemClickListerner itemClickListerner;

    private static final String URL_ABSEN = "https://simorin.malangcreativeteam.biz.id/api/lihat-absen-ortu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(LihatAbsenOrangTuaActivity.this);
        setContentView(R.layout.activity_lihat_absen_orang_tua);

        recyclerView = findViewById(R.id.recycler_view);
        tv_nama = findViewById(R.id.tv_nama);
        tv_perusahaan = findViewById(R.id.tv_perusahaan);
        tv_talpha = findViewById(R.id.tv_alpha);
        tv_thadir = findViewById(R.id.tv_hadir);
        kembali = findViewById(R.id.back);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(LihatAbsenOrangTuaActivity.this);

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(LihatAbsenOrangTuaActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        absenOrtus = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemClickListerner = ((view, position) -> {

        });

        String mId = getIntent().getStringExtra("id");

        getAbsen(mId);
    }

    private void getAbsen(String mId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ABSEN,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("RESULT");

                        if (success.equals("OK")) {
                            String total_alpha = jsonObject.getString("TOTAL_ALPHA");
                            String total_hadir = jsonObject.getString("TOTAL_HADIR");

                            tv_thadir.setText(total_hadir);
                            tv_talpha.setText(total_alpha);

                            JSONArray array = jsonObject.getJSONArray("ABSENSI");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);

                                String nama_siswa = obj.getString("nama_siswa");
                                String perusahaan = obj.getString("perusahaan");

                                tv_nama.setText(nama_siswa);
                                tv_perusahaan.setText(perusahaan);

                                absenOrtus.add(new AbsenOrtu(
                                        obj.getInt("status"),
                                        obj.getString("keterangan"),
                                        obj.getString("tanggal"),
                                        obj.getString("latitude"),
                                        obj.getString("longitude")
                                ));

                            }

                            LihatAbsenOrangTuaAdapter adapter = new LihatAbsenOrangTuaAdapter(LihatAbsenOrangTuaActivity.this, absenOrtus, itemClickListerner);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (Exception e) {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(e.toString())
                                .show();
                    }
                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", mId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LihatAbsenOrangTuaActivity.this);
        requestQueue.add(stringRequest);
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
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}