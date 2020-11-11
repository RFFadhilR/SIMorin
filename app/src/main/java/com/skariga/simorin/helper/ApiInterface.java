package com.skariga.simorin.helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("list-absen")
    Call<List<Absen>> getAbsens(
            @Field("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

    @FormUrlEncoded
    @POST("list-jurnal")
    Call<List<Jurnal>> getJurnals(
            @Field("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

}
