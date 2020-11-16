package com.skariga.simorin.model;

import com.google.gson.annotations.SerializedName;

public class AbsenOrtu {

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

    public AbsenOrtu(int status, String keterangan, String tanggal, String latitude, String longitude) {
        this.status = status;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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
