package com.skariga.simorin.perusahaan;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skariga.simorin.R;
import com.skariga.simorin.auth.DashboardActivity;
import com.skariga.simorin.auth.LoginActivity;
import com.skariga.simorin.model.AbsenPerusahaan;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListAbsenPemPerusahaanActivity extends FragmentActivity implements OnMapReadyCallback, ListAbsenPemPerusahaanView {

    Button setujui_semua, setujui_dipilih, tolak_semua, tolak_dipilih;
    ImageView kembali;
    GoogleMap map;
    RecyclerView data;
    SweetAlertDialog sweetAlertDialog;

    List<AbsenPerusahaan> absenPerusahaan;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();

    ListAbsenPemPerusahaanPresenter presenter;
    ListAbsenPemPerusahaanAdapter adapter;
    ListAbsenPemPerusahaanAdapter.ItemClickListener itemClickListener;

    String mId;
//    String URL_SEJUTU_SEMUA = "http://192.168.1.5/SimorinLaravel/public/api/setujui-semua-absen";
//    String URL_TOLAK_SEMUA = "http://192.168.1.5/SimorinLaravel/public/api/tolak-semua-absen";
//    String URL_SEJUTU_DIPILIH = "http://192.168.1.5/SimorinLaravel/public/api/setujui-dipilih-absen";
//    String URL_TOLAK_DIPILIH = "http://192.168.1.5/SimorinLaravel/public/api/tolak-dipilih-absen";
    String URL_SEJUTU_SEMUA = "https://simorin.malangcreativeteam.biz.id/api/setujui-semua-absen";
    String URL_TOLAK_SEMUA = "https://simorin.malangcreativeteam.biz.id/api/tolak-semua-absen";
    String URL_SEJUTU_DIPILIH = "https://simorin.malangcreativeteam.biz.id/api/setujui-dipilih-absen";
    String URL_TOLAK_DIPILIH = "https://simorin.malangcreativeteam.biz.id/api/tolak-dipilih-absen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(ListAbsenPemPerusahaanActivity.this);
        setContentView(R.layout.activity_list_absen_pem_perusahaan);

        kembali = findViewById(R.id.back);
        data = findViewById(R.id.recycler_view);
        setujui_dipilih = findViewById(R.id.btn_sdipilih);
        setujui_semua = findViewById(R.id.btn_ssemua);
        tolak_dipilih = findViewById(R.id.btn_tdipilih);
        tolak_semua = findViewById(R.id.btn_tsemua);

        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");
        sweetAlertDialog.show();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(ListAbsenPemPerusahaanActivity.this);

        setujui_semua.setOnClickListener(v -> {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < adapter.getAllData().size(); i++) {
                stringBuilder.append(adapter.getAllData().get(i).getId_absen());
                stringBuilder.append(", ");
            }

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Maaf...")
                    .setContentText("Apakah anda yakin untuk menyetujui semua absen? (Semua absen ber-status pending / background berwarna putih)")
                    .setConfirmButton("Iyaa", sweetA -> {
                        setujuiSemua(stringBuilder.toString().trim());
                        sweetAlertDialog.show();
                    })
                    .setCancelText("Tidak")
                    .show();

//            Toast.makeText(this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
        });

        tolak_semua.setOnClickListener(v -> {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < adapter.getAllData().size(); i++) {
                stringBuilder.append(adapter.getAllData().get(i).getId_absen());
                stringBuilder.append(", ");
            }

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Maaf...")
                    .setContentText("Apakah anda yakin untuk menolak semua absen? (Semua absen ber-status pending / background berwarna putih)")
                    .setConfirmButton("Iyaa", sweetA -> {
                        tolakSemua(stringBuilder.toString().trim());
                        sweetAlertDialog.show();
                    })
                    .setCancelText("Tidak")
                    .show();

//            Toast.makeText(this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
        });

        setujui_dipilih.setOnClickListener(v -> {
            if (adapter.getSelected().size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < adapter.getSelected().size(); i++) {
                    stringBuilder.append(adapter.getSelected().get(i).getId_absen());
                    stringBuilder.append(", ");
                }

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Apakah anda yakin untuk menyetujui absen yang sudah anda pilih?")
                        .setConfirmButton("Iyaa", sweetA -> {
                            setujuiDipilih(stringBuilder.toString().trim());
                            sweetAlertDialog.show();
                        })
                        .setCancelText("Tidak")
                        .show();

//                Toast.makeText(this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
            } else {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Anda belum memilih absen!")
                        .show();
//                Toast.makeText(this, "KOSONG!", Toast.LENGTH_SHORT).show();
            }
        });

        tolak_dipilih.setOnClickListener(v -> {
            if (adapter.getSelected().size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < adapter.getSelected().size(); i++) {
                    stringBuilder.append(adapter.getSelected().get(i).getId_absen());
                    stringBuilder.append(", ");
                }

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Apakah anda yakin untuk menolak absen yang sudah anda pilih?")
                        .setConfirmButton("Iyaa", sweetA -> {
                            tolakDipilih(stringBuilder.toString().trim());
                            sweetAlertDialog.show();
                        })
                        .setCancelText("Tidak")
                        .show();

//                Toast.makeText(this, stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
            } else {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Anda belum memilih absen!")
                        .show();
//                Toast.makeText(this, "KOSONG!", Toast.LENGTH_SHORT).show();
            }
        });

        kembali.setOnClickListener(v -> {
            Intent i = new Intent(ListAbsenPemPerusahaanActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        });

        data.setLayoutManager(new LinearLayoutManager(this));

        mId = getIntent().getStringExtra("id");

        presenter = new ListAbsenPemPerusahaanPresenter(this);
        presenter.getData(mId);

        itemClickListener = ((view, position) -> {
            String ket = absenPerusahaan.get(position).getKeterangan();
            String nama = absenPerusahaan.get(position).getNama_siswa();
            String tanggal = absenPerusahaan.get(position).getTanggal();
            String waktu_masuk = absenPerusahaan.get(position).getWaktu_masuk();
            String waktu_pulang = absenPerusahaan.get(position).getWaktu_pulang();
            double latss = Double.parseDouble(absenPerusahaan.get(position).getLatitude());
            double longss = Double.parseDouble(absenPerusahaan.get(position).getLongitude());
            LatLng lokss = new LatLng(latss, longss);

            if (ket.equals("MASUK")) {
                Toast.makeText(this, nama + "\n" + tanggal + " / " + waktu_masuk + "\n" + ket, Toast.LENGTH_SHORT).show();
                arrayList.add(lokss);

                for (int i = 0; i < arrayList.size(); i++) {
                    map.addMarker(new MarkerOptions().position(arrayList.get(i)).title(tanggal));
                    map.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(i), 18.0f));
                }
            } else {
                Toast.makeText(this, nama + "\n" + tanggal + " / " + waktu_pulang + "\n" + ket, Toast.LENGTH_SHORT).show();
                arrayList.add(lokss);

                for (int i = 0; i < arrayList.size(); i++) {
                    map.addMarker(new MarkerOptions().position(arrayList.get(i)).title(tanggal));
                    map.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(i), 18.0f));
                }
            }
        });

    }

    private void setujuiSemua(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEJUTU_SEMUA,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("RESULT");
                        String message = jsonObject.getString("MESSAGE");

                        if (success.equals("OK")) {
                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Yeay...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        } else {

                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Maaf...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        }
                    } catch (Exception e) {
                        sweetAlertDialog.dismiss();
                        new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(e.toString())
                                .show();
                    }
                },
                error -> {
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText(error.toString())
                            .show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_absen", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListAbsenPemPerusahaanActivity.this);
        requestQueue.add(stringRequest);
    }

    private void tolakSemua(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TOLAK_SEMUA,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("RESULT");
                        String message = jsonObject.getString("MESSAGE");

                        if (success.equals("OK")) {

                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Yeay...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        } else {

                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Maaf...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        }
                    } catch (Exception e) {
                        sweetAlertDialog.dismiss();
                        new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(e.toString())
                                .show();
                    }
                },
                error -> {
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText(error.toString())
                            .show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_absen", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListAbsenPemPerusahaanActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setujuiDipilih(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEJUTU_DIPILIH,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("RESULT");
                        String message = jsonObject.getString("MESSAGE");

                        if (success.equals("OK")) {

                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Yeay...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        } else {

                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Maaf...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        }
                    } catch (Exception e) {
                        sweetAlertDialog.dismiss();
                        new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(e.toString())
                                .show();
                    }
                },
                error -> {
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText(error.toString())
                            .show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_absen", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListAbsenPemPerusahaanActivity.this);
        requestQueue.add(stringRequest);
    }

    private void tolakDipilih(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TOLAK_DIPILIH,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("RESULT");
                        String message = jsonObject.getString("MESSAGE");

                        if (success.equals("OK")) {

                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Yeay...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        } else {

                            sweetAlertDialog.dismiss();
                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Maaf...")
                                    .setContentText(message)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        presenter.getData(mId);
                                        finish();
                                        startActivity(getIntent());
                                    })
                                    .show();
                        }
                    } catch (Exception e) {
                        sweetAlertDialog.dismiss();
                        new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error...")
                                .setContentText(e.toString())
                                .show();
                    }
                },
                error -> {
                    sweetAlertDialog.dismiss();
                    new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error...")
                            .setContentText(error.toString())
                            .show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_absen", id);
                System.out.println(id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListAbsenPemPerusahaanActivity.this);
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
        double latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        double longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        LatLng lokasi = new LatLng(latitude, longitude);
//        map.addMarker(new MarkerOptions().position(lokasi).title("Lokasi Saat ini"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 13.0f));
        drawCircle(lokasi);
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
    public void onGetResult(List<AbsenPerusahaan> absenPerusahaans) {
        adapter = new ListAbsenPemPerusahaanAdapter(this, absenPerusahaans, itemClickListener);
        adapter.notifyDataSetChanged();
        data.setAdapter(adapter);

        absenPerusahaan = absenPerusahaans;
        sweetAlertDialog.dismiss();
    }

    @Override
    public void onErrorLoading(String message) {
        new SweetAlertDialog(ListAbsenPemPerusahaanActivity.this, SweetAlertDialog.ERROR_TYPE)
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