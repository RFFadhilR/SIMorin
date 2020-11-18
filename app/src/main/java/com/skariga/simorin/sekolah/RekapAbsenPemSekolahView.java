package com.skariga.simorin.sekolah;

import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.Rekap;

import java.util.List;

public interface RekapAbsenPemSekolahView {
    void onGetResultPer(List<Perusahaan> perusahaans);
    void onGetReseltRek(List<Rekap> rekaps);
    void onErrorResult(String message);
}
