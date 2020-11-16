package com.skariga.simorin.perusahaan;

import com.skariga.simorin.model.AbsenPerusahaan;

import java.util.List;

public interface ListAbsenPemPerusahaanView {
    void onGetResult(List<AbsenPerusahaan> absenPerusahaans);
    void onErrorLoading(String message);
}
