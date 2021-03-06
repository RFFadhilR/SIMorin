package com.skariga.simorin.auth;

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
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skariga.simorin.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_user, et_pass;
    String username, password;
    SessionManager sessionManager;
    public static String URL_LOGIN = "https://simorin.malangcreativeteam.biz.id/api/login";
//    public static String URL_LOGIN = "http://192.168.10.26/SimorinLaravel/public/api/login";
    SweetAlertDialog sweetAlertDialogGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(LoginActivity.this);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        et_user = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_password);

        sessionManager = new SessionManager(getApplicationContext());

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_user.getText().toString().trim();
                password = et_pass.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Maaf...")
                            .setContentText("Username dan Password tidak boleh kosong!")
                            .show();
                } else {
                    sweetAlertDialogGlobal = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    sweetAlertDialogGlobal.setTitleText("Loading...");
                    sweetAlertDialogGlobal.show();
                    Login(username, password);
                }
            }

            private void Login(final String user, final String pass) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("RESULT");

                                if (success.equals("OK")) {
                                    JSONObject object = jsonObject.getJSONObject("USER");

                                    final String id = object.getString("ID").trim();
                                    final String nama = object.getString("NAMA").trim();
                                    final String role = object.getString("ROLE").trim();
                                    final String psb = object.getString("PSB").trim();

                                    if (role.equals("Siswa")
                                            || role.equals("Pembimbing Sekolah")
                                            || role.equals("Pembimbing Perusahaan")
                                            || role.equals("Orang Tua")) {
                                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Yeay...")
                                                .setContentText("Anda Berhasil Login!")
                                                .setConfirmClickListener(sweetAlertDialog -> {
                                                    sweetAlertDialog.dismiss();
                                                    sessionManager.createSession(id, nama, psb, role);
                                                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                })
                                                .show();
                                    } else {
                                        sweetAlertDialogGlobal.dismiss();

                                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Maaf...")
                                                .setContentText("Akses ditolak!")
                                                .show();
                                    }
                                } else {
                                    String message = jsonObject.getString("MESSAGE");

                                    sweetAlertDialogGlobal.dismiss();
                                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Gagal...")
                                            .setContentText(message)
                                            .show();
                                }
                            } catch (Exception e) {
                                sweetAlertDialogGlobal.dismiss();
                                e.printStackTrace();
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error...")
                                        .setContentText(e.toString())
                                        .show();
                            }
                        },
                        error -> {
                            sweetAlertDialogGlobal.dismiss();
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error...")
                                    .setContentText(error.toString())
                                    .show();
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", user);
                        params.put("password", pass);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);

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
}