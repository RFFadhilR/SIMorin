package com.skariga.simorin.sekolah;

import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.RekapJurnal;
import com.skariga.simorin.model.Siswa;

import java.util.List;

public interface RekapJurnalPemSekolahView {
    void onGetResultPerusahaan(List<Perusahaan> perusahaans);
    void onGetResultSiswa(List<Siswa> siswas);
    void onGetResultRekap(List<RekapJurnal> jurnals);
    void onErrorResult(String message);
}
