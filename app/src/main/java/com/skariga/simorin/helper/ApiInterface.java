package com.skariga.simorin.helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("list-absen-perusahaan")
    Call<List<Absen>> getAbsens(
            @Field("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

    @FormUrlEncoded
    @POST("list-jurnal")
    Call<List<Jurnal>> getJurnals(
            @Field("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

}
