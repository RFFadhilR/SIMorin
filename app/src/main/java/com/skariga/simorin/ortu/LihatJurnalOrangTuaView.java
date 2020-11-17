package com.skariga.simorin.ortu;

import com.skariga.simorin.model.JurnalOrtu;

import java.util.List;

public interface LihatJurnalOrangTuaView {
    void onGetResult(List<JurnalOrtu> jurnalOrtus);
    void onErrorResult(String message);
}
