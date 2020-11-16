package com.skariga.simorin.perusahaan;

import com.skariga.simorin.model.JurnalPerusahaan;

import java.util.List;

public interface ListJurnalPemPerusahaanView {
    void onGetResults(List<JurnalPerusahaan> jurnalPerusahaans);
    void onErrorLoading(String message);
}
