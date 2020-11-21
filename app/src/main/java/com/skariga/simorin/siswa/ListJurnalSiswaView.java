package com.skariga.simorin.siswa;

import com.skariga.simorin.model.JurnalPerusahaan;

import java.util.List;

public interface ListJurnalSiswaView {
    void onGetResult(List<JurnalPerusahaan> jurnalPerusahaans);
    void onErrorResult(String message);
}
