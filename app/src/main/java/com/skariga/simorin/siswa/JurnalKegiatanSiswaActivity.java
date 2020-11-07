package com.skariga.simorin.siswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.skariga.simorin.R;
import com.skariga.simorin.perusahaan.DashboardPemPerusahaanActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class JurnalKegiatanSiswaActivity extends AppCompatActivity {
    ImageView back;
    Date date;
    Button btn_submit;
    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal_kegiatan_siswa);

        back = findViewById(R.id.back);
        btn_submit = findViewById(R.id.submit);

        date = new Date();
        format = new SimpleDateFormat("E, dd MMMM yyyy");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(JurnalKegiatanSiswaActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf...")
                        .setContentText("Fitur ini masih dalam pengembangan :)")
                        .show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JurnalKegiatanSiswaActivity.this, DashboardSiswaActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}