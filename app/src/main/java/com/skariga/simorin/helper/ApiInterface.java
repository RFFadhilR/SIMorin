package com.skariga.simorin.helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("list-absen")
    Call<List<Absen>> getAbsens(
            @Query("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

    @POST("list-jurnal")
    Call<List<Jurnal>> getJurnals(
            @Query("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

}
