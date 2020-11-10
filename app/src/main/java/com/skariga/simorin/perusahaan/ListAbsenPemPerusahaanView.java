package com.skariga.simorin.perusahaan;

import com.skariga.simorin.helper.Absen;

import java.util.List;

public interface ListAbsenPemPerusahaanView {
    void onGetResult(List<Absen> absens);
    void onErrorLoading(String message);
}
