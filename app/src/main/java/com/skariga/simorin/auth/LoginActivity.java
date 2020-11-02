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

import com.skariga.simorin.ortu.DashboardOrangTuaActivity;
import com.skariga.simorin.perusahaan.DashboardPemPerusahaanActivity;
import com.skariga.simorin.sekolah.DashboardPemSekolahActivity;
import com.skariga.simorin.siswa.DashboardSiswaActivity;
import com.skariga.simorin.R;
import com.skyhope.showmoretextview.ShowMoreTextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_user, et_pass;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(LoginActivity.this);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        et_user = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_user.getText().toString().trim();
                if (username.isEmpty()){
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Maaf...")
                            .setContentText("Username dan Password tidak boleh kosong!")
                            .show();
                } else if (username.equals("orang tua")){
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Yeay...")
                            .setContentText("Anda Berhasil Login")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(LoginActivity.this, DashboardOrangTuaActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .show();
                } else if (username.equals("perusahaan")) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Yeay...")
                            .setContentText("Anda Berhasil Login")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(LoginActivity.this, DashboardPemPerusahaanActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .show();
                } else if (username.equals("sekolah")) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Yeay...")
                            .setContentText("Anda Berhasil Login")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(LoginActivity.this, DashboardPemSekolahActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .show();
                } else if (username.equals("siswa")) {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Yeay...")
                            .setContentText("Anda Berhasil Login")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(LoginActivity.this, DashboardSiswaActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .show();
                } else {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Username dan Password yang anda masukkan salah!")
                            .show();
                }
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