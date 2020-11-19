package com.skariga.simorin.sekolah;

import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.RekapAbsen;

import java.util.List;

public interface RekapAbsenPemSekolahView {
    void onGetResultPer(List<Perusahaan> perusahaans);
    void onGetReseltRek(List<RekapAbsen> rekapAbsens);
    void onErrorResult(String message);
}
