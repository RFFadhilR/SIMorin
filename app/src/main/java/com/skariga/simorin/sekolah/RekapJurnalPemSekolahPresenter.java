package com.skariga.simorin.sekolah;

import androidx.annotation.NonNull;

import com.skariga.simorin.api.ApiClient;
import com.skariga.simorin.api.ApiInterface;
import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.RekapJurnal;
import com.skariga.simorin.model.Siswa;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapJurnalPemSekolahPresenter {

    private RekapJurnalPemSekolahView view;

    public RekapJurnalPemSekolahPresenter(RekapJurnalPemSekolahView view) {
        this.view = view;
    }

    void getPerusahaan(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Perusahaan>> call = apiInterface.getPers(id);
        call.enqueue(new Callback<List<Perusahaan>>() {
            @Override
            public void onResponse(@NonNull Call<List<Perusahaan>> call, @NonNull Response<List<Perusahaan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResultPerusahaan(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Perusahaan>> call, @NonNull Throwable t) {
                view.onErrorResult(t.getLocalizedMessage());
            }
        });
    }

    void getSiswa(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Siswa>> call = apiInterface.getSiswa(id);
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResultSiswa(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                view.onErrorResult(t.getLocalizedMessage());
            }
        });
    }

    void getRekapJurnal(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<RekapJurnal>> call = apiInterface.getRekj(id);
        call.enqueue(new Callback<List<RekapJurnal>>() {
            @Override
            public void onResponse(Call<List<RekapJurnal>> call, Response<List<RekapJurnal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResultRekap(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<RekapJurnal>> call, Throwable t) {
                view.onErrorResult(t.getLocalizedMessage());
            }
        });
    }

}
