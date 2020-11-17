package com.skariga.simorin.model;

import com.google.gson.annotations.SerializedName;

public class AbsenOrtu {

    @SerializedName("id_absen")
    private int id_absen;
    @SerializedName("id_siswa")
    private int id_siswa;
    @SerializedName("nama_siswa")
    private String nama_siswa;
    @SerializedName("perusahaan")
    private String perusahaan;
    @SerializedName("status")
    private int status;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
