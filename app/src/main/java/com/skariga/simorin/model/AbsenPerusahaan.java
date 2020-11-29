package com.skariga.simorin.model;

import com.google.gson.annotations.SerializedName;

public class AbsenPerusahaan {

    @SerializedName("id_absen")
    private int id_absen;
    @SerializedName("id_siswa")
    private int id_siswa;
    @SerializedName("nama_siswa")
    private String nama_siswa;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("waktu_masuk")
    private String waktu_masuk;
    @SerializedName("waktu_pulang")
    private String waktu_pulang;
    @SerializedName("status")
    private int status;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("keterangan")
    private String keterangan;
    private boolean isChecked = false;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId_absen() {
        return id_absen;
    }

    public void setId_absen(int id_absen) {
        this.id_absen = id_absen;
    }

    public int getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(int id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getNama_siswa() {
        return nama_siswa;
    }

    public void setNama_siswa(String nama_siswa) {
        this.nama_siswa = nama_siswa;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu_masuk() {
        return waktu_masuk;
    }

    public void setWaktu_masuk(String waktu_masuk) {
        this.waktu_masuk = waktu_masuk;
    }

    public String getWaktu_pulang() {
        return waktu_pulang;
    }

    public void setWaktu_pulang(String waktu_pulang) {
        this.waktu_pulang = waktu_pulang;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

}
