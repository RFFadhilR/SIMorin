package com.skariga.simorin.siswa;

import androidx.fragment.app.FragmentActivity;

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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import com.skariga.simorin.auth.SessionManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AbsenSiswaActivity extends FragmentActivity implements OnMapReadyCallback {
    private static String URL_ABSEN = "https://simorin.malangcreativeteam.biz.id/api/absen_siswa";
    Button btn_kembali;
    TextView tv_status, tv_tanggal;
    GoogleMap map;
    Date date;
    SessionManager sessionManager;
    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(AbsenSiswaActivity.this);
        setContentView(R.layout.activity_absen_siswa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(AbsenSiswaActivity.this);

        date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy / HH:mm");

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading...")
                .show();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mId = user.get(SessionManager.ID);

        tv_tanggal = findViewById(R.id.tv_tanggal);
        btn_kembali = findViewById(R.id.btn_kembali);
        tv_status = findViewById(R.id.tv_status);

        tv_tanggal.setText(formatter.format(date));

        double latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        double longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));

        String lat = Double.toString(latitude).trim();
        String longs = Double.toString(longitude).trim();

        Absen(mId, date.toString(), lat, longs);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AbsenSiswaActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
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
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 18.0f));
    }

    private void Absen(final String id, final String tanggal, final String lat, final String longs) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ABSEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("RESULT");

                            if (result.equals("OK")) {
                                String message = jsonObject.getString("MESSAGE");

                                sweetAlertDialog.dismiss();
                                new SweetAlertDialog(AbsenSiswaActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Yaey...")
                                        .setContentText(message)
                                        .show();
                            } else if (result.equals("DONE")) {
                                sweetAlertDialog.dismiss();

                                JSONObject data = jsonObject.getJSONObject("DATA");

                                if (data.getString("status").equals("0")) {
                                    tv_status.setText("Pending");
                                } else if (data.getString("status").equals("1")) {
                                    tv_status.setText("Diterima");
                                } else {
                                    tv_status.setText("Ditolak");
                                }

                                tv_tanggal.setText(data.getString("waktu_masuk"));
                            } else {
                                String message = jsonObject.getString("MESSAGE");

                                sweetAlertDialog.dismiss();
                                new SweetAlertDialog(AbsenSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Gagal...")
                                        .setContentText(message)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent i = new Intent(AbsenSiswaActivity.this, DashboardSiswaActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        } catch (Exception ex) {
                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(AbsenSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(ex.toString())
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sweetAlertDialog.dismiss();
                        new SweetAlertDialog(AbsenSiswaActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(error.toString())
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("date", tanggal);
                params.put("latitude", lat);
                params.put("longitude", longs);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}