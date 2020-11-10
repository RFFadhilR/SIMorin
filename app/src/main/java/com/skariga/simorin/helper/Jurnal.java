package com.skariga.simorin.helper;

import com.google.gson.annotations.SerializedName;

public class Jurnal {
    @SerializedName("id_jurnal")
    private int id_jurnal;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("waktu_masuk")
    private String waktu_masuk;
    @SerializedName("waktu_pulang")
    private String waktu_pulang;
    @SerializedName("kegiatan")
    private String kegiatan;
    @SerializedName("prosedur")
    private String prosedur;
    @SerializedName("spek")
    private String spek;

    public int getId_jurnal() {
        return id_jurnal;
    }

    public void setId_jurnal(int id_jurnal) {
        this.id_jurnal = id_jurnal;
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

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getProsedur() {
        return prosedur;
    }

    public void setProsedur(String prosedur) {
        this.prosedur = prosedur;
    }

    public String getSpek() {
        return spek;
    }

    public void setSpek(String spek) {
        this.spek = spek;
    }
}
