package com.skariga.simorin.perusahaan;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.helper.Absen;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListAbsenPemPerusahaanActivity extends FragmentActivity implements OnMapReadyCallback, ListAbsenPemPerusahaanView {

    Button semua, dipilih;
    TextView lihat_lokasi;
    ImageView kembali;
    GoogleMap map;
    RecyclerView data;

    ListAbsenPemPerusahaanPresenter presenter;
    ListAbsenPemPerusahaanAdapter adapter;
    ListAbsenPemPerusahaanAdapter.ItemClickListener itemClickListener;

    List<Absen> absen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(ListAbsenPemPerusahaanActivity.this);
        setContentView(R.layout.activity_list_absen_pem_perusahaan);

        semua = findViewById(R.id.setujui_semua);
        dipilih = findViewById(R.id.setujui_dipilih);
        lihat_lokasi = findViewById(R.id.lihat_lokasi);
        kembali = findViewById(R.id.back);
        data = findViewById(R.id.recycler_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(ListAbsenPemPerusahaanActivity.this);

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(ListAbsenPemPerusahaanActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        lihat_lokasi.setOnClickListener(v -> new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        semua.setOnClickListener(v -> new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        dipilih.setOnClickListener(v -> new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Maaf...")
                .setContentText("Fitur ini masih dalam pengembangan :)")
                .show());

        data.setLayoutManager(new LinearLayoutManager(this));

        String mId = getIntent().getStringExtra("id");

        presenter = new ListAbsenPemPerusahaanPresenter(this);
        presenter.getData(mId);

        itemClickListener = ((view, position) -> {
            String status = absen.get(position).getStatus();
            String nama = absen.get(position).getNama_siswa();
            String tanggal = absen.get(position).getTanggal();
            String waktu_masuk = absen.get(position).getWaktu_masuk();
            String waktu_pulang = absen.get(position).getWaktu_pulang();

            if (status.equals("MASUK")) {
                Toast.makeText(this, nama + "\n" + tanggal + " / " + waktu_masuk + "\n" + status, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, nama + "\n" + tanggal + " / " + waktu_pulang + "\n" + status, Toast.LENGTH_LONG).show();
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
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        double longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        LatLng lokasi = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(lokasi).title("Lokasi Saat ini"));
        map.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,12.0f));
        drawCircle(new LatLng(latitude, longitude));
    }

    private void drawCircle(LatLng point) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(1000);
        circleOptions.fillColor(R.color.light_blue);
        circleOptions.strokeColor(R.color.blue);
        circleOptions.strokeWidth(2);
        map.addCircle(circleOptions);
    }

    @Override
    public void onGetResult(List<Absen> absens) {
        adapter = new ListAbsenPemPerusahaanAdapter(this, absens, itemClickListener);
        adapter.notifyDataSetChanged();
        data.setAdapter(adapter);

        absen = absens;
    }

    @Override
    public void onErrorLoading(String message) {
        new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error...")
                .setContentText(message)
                .show();
    }
}