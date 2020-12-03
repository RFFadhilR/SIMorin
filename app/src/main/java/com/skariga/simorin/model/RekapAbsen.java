package com.skariga.simorin.model;

import com.google.gson.annotations.SerializedName;

public class RekapAbsen {
    @SerializedName("id_absen")
    private int id_absen;
    @SerializedName("nis")
    private int nis;
    @SerializedName("nama")
    private String nama;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("status")
    private int status;
    @SerializedName("total_jam")
    private int total_jam;

    public int getId_absen() {
        return id_absen;
    }

    public void setId_absen(int id_absen) {
        this.id_absen = id_absen;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_jam() {
        return total_jam;
    }

    public void setTotal_jam(int total_jam) {
        this.total_jam = total_jam;
    }
}
