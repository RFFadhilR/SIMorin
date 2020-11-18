package com.skariga.simorin.model;

import com.google.gson.annotations.SerializedName;

public class Perusahaan {
    @SerializedName("id_perusahaan")
    private int id_perusahaan;
    @SerializedName("perusahaan")
    private String perusahan;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("total_siswa")
    private int total_siswa;

    public int getId_perusahaan() {
        return id_perusahaan;
    }

    public void setId_perusahaan(int id_perusahaan) {
        this.id_perusahaan = id_perusahaan;
    }

    public String getPerusahan() {
        return perusahan;
    }

    public void setPerusahan(String perusahan) {
        this.perusahan = perusahan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getTotal_siswa() {
        return total_siswa;
    }

    public void setTotal_siswa(int total_siswa) {
        this.total_siswa = total_siswa;
    }
}
