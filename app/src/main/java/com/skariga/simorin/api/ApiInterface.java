package com.skariga.simorin.api;

import com.skariga.simorin.model.AbsenOrtu;
import com.skariga.simorin.model.AbsenPerusahaan;
import com.skariga.simorin.model.JurnalOrtu;
import com.skariga.simorin.model.JurnalPerusahaan;
import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.RekapAbsen;
import com.skariga.simorin.model.RekapJurnal;
import com.skariga.simorin.model.Siswa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("list-absen-perusahaan")
    Call<List<AbsenPerusahaan>> getAbsens(
            @Field("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

    @FormUrlEncoded
    @POST("list-jurnal")
    Call<List<JurnalPerusahaan>> getJurnals(
            @Field("id_pembimbing_perusahaan") String id_pembimbing_perusahaan
    );

    @FormUrlEncoded
    @POST("list-absen-ortu")
    Call<List<AbsenOrtu>> getAbsensi(
            @Field("id_orangtua") String id_orangtua
    );

    @FormUrlEncoded
    @POST("list-jurnal-ortu")
    Call<List<JurnalOrtu>> getJurnal(
            @Field("id_orangtua") String id_orangtua
    );

    @FormUrlEncoded
    @POST("list-aperusahaan")
    Call<List<Perusahaan>> getPers(
            @Field("id_pemsekolah") String id_pemsekolah
    );

    @FormUrlEncoded
    @POST("list-arekap")
    Call<List<RekapAbsen>> getReks(
            @Field("id_perusahaan") String id_perusahaan
    );

    @FormUrlEncoded
    @POST("list-siswa")
    Call<List<Siswa>> getSiswa(
            @Field("id_perusahaan") String id_perusahaan
    );

    @FormUrlEncoded
    @POST("list-jrekap")
    Call<List<RekapJurnal>> getRekj(
            @Field("id_siswa") String id_siswa
    );
}
