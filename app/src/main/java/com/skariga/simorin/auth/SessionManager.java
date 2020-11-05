package com.skariga.simorin.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.skariga.simorin.ortu.DashboardOrangTuaActivity;
import com.skariga.simorin.perusahaan.DashboardPemPerusahaanActivity;
import com.skariga.simorin.sekolah.DashboardPemSekolahActivity;
import com.skariga.simorin.siswa.DashboardSiswaActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String ID = "ID";
    public static final String NAMA = "NAMA";
    public static final String PSB = "PSB";
    public static final String ROLE = "ROLE";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String nama, String psb, String role) {
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(NAMA, nama);
        editor.putString(PSB, psb);
        editor.putString(ROLE, role);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((DashboardActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(PSB, sharedPreferences.getString(PSB, null));
        user.put(ROLE, sharedPreferences.getString(ROLE, null));
        return user;
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((DashboardActivity) context).finish();
    }

}
