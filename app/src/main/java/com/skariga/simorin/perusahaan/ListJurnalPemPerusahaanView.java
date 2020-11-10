package com.skariga.simorin.perusahaan;

import com.skariga.simorin.helper.Jurnal;

import java.util.List;

public interface ListJurnalPemPerusahaanView {
    void onGetResults(List<Jurnal> jurnals);
    void onErrorLoading(String message);
}
