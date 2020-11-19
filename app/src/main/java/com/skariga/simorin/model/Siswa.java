package com.skariga.simorin.model;

import com.google.gson.annotations.SerializedName;

public class Siswa {
    @SerializedName("id_siswa")
    private int id_siswa;
    @SerializedName("nis")
    private int nis;
    @SerializedName("nama")
    private String nama;

    public int getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(int id_siswa) {
        this.id_siswa = id_siswa;
    }

    public int getNis() {
        return nis;
    }

    public void setNis(int nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
