package com.skariga.simorin.ortu;

import com.skariga.simorin.model.AbsenOrtu;

import java.util.List;

public interface LihatAbsenOrangTuaView {
    void onGetResult(List<AbsenOrtu> absenOrtus);
    void onErrorLoading(String message);
}
