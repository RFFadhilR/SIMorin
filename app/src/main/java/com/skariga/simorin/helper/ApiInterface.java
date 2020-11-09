package com.skariga.simorin.helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("list-absen")
    Call<List<Absen>> getAbsens();

}
