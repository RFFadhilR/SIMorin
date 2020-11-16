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
    private String status;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("total_hadir")
    private String total_hadir;
    @SerializedName("total_alpha")
    private String total_alpha;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("akses")
    private String akses;
    @SerializedName("kelas")
    private String kelas;

    public AbsenPerusahaan(int id_absen, int id_siswa, String nama_siswa, String tanggal, String waktu_masuk, String waktu_pulang, String status, String latitude, String longitude, String total_hadir, String total_alpha, String keterangan, String akses, String kelas) {
        this.id_absen = id_absen;
        this.id_siswa = id_siswa;
        this.nama_siswa = nama_siswa;
        this.tanggal = tanggal;
        this.waktu_masuk = waktu_masuk;
        this.waktu_pulang = waktu_pulang;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.total_hadir = total_hadir;
        this.total_alpha = total_alpha;
        this.keterangan = keterangan;
        this.akses = akses;
        this.kelas = kelas;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getTotal_hadir() {
        return total_hadir;
    }

    public void setTotal_hadir(String total_hadir) {
        this.total_hadir = total_hadir;
    }

    public String getTotal_alpha() {
        return total_alpha;
    }

    public void setTotal_alpha(String total_alpha) {
        this.total_alpha = total_alpha;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAkses() {
        return akses;
    }

    public void setAkses(String akses) {
        this.akses = akses;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}
