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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.model.AbsenOrtu;
import com.skariga.simorin.model.AbsenPerusahaan;
import com.skariga.simorin.perusahaan.ListAbsenPemPerusahaanActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LihatAbsenOrangTuaActivity extends AppCompatActivity implements OnMapReadyCallback, LihatAbsenOrangTuaView {

    ImageView kembali;
    TextView tv_nama, tv_perusahaan, tv_thadir, tv_talpha;
    RecyclerView recyclerView;
    GoogleMap map;

    List<AbsenOrtu> absenOrtus;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();

    LihatAbsenOrangTuaPresenter presenter;
    LihatAbsenOrangTuaAdapter adapter;
    LihatAbsenOrangTuaAdapter.ItemClickListerner itemClickListerner;

    public static String URL = "https://simorin.malangcreativeteam.biz.id/api/absensi-ortu";

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String mId = getIntent().getStringExtra("id");

        presenter = new LihatAbsenOrangTuaPresenter(this);
        presenter.getAbsensi(mId);

        itemClickListerner = ((view, position) -> {
            String tanggal = absenOrtus.get(position).getTanggal();
            String ket = absenOrtus.get(position).getKeterangan();
            double latitude = Double.parseDouble(absenOrtus.get(position).getLatitude());
            double longitude = Double.parseDouble(absenOrtus.get(position).getLongitude());

            Toast.makeText(this, tanggal + "\n" + ket, Toast.LENGTH_LONG).show();
            LatLng lokasi = new LatLng(latitude, longitude);

            arrayList.add(lokasi);

            for (int i = 0; i < arrayList.size(); i++) {
                map.addMarker(new MarkerOptions().position(arrayList.get(i)).title(tanggal));
                map.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
            }

        });

        getData(mId);

    }

    private void getData(String mId) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject object = jsonObject.getJSONObject("DATA");
                        tv_thadir.setText(object.getString("TOTAL_HADIR"));
                        tv_talpha.setText(object.getString("TOTAL_ALPHA"));
                        tv_nama.setText(object.getString("NAMA"));
                        tv_perusahaan.setText(object.getString("PERUSAHAAN"));
                    } catch (Exception e) {
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", mId);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(LihatAbsenOrangTuaActivity.this);
        queue.add(request);

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

    @Override
    public void onGetResult(List<AbsenOrtu> absenOrtus) {
        adapter = new LihatAbsenOrangTuaAdapter(this, absenOrtus, itemClickListerner);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        this.absenOrtus = absenOrtus;
    }

    @Override
    public void onErrorLoading(String message) {
        new SweetAlertDialog(LihatAbsenOrangTuaActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error...")
                .setContentText(message)
                .show();
    }
}