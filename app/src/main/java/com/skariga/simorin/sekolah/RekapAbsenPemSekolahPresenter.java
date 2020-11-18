package com.skariga.simorin.sekolah;

import androidx.annotation.NonNull;

import com.skariga.simorin.api.ApiClient;
import com.skariga.simorin.api.ApiInterface;
import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.model.Rekap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapAbsenPemSekolahPresenter {
    private RekapAbsenPemSekolahView view;

    public RekapAbsenPemSekolahPresenter(RekapAbsenPemSekolahView view) {
        this.view = view;
    }

    void getPerusahaan(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Perusahaan>> call = apiInterface.getPers(id);
        call.enqueue(new Callback<List<Perusahaan>>() {
            @Override
            public void onResponse(@NonNull Call<List<Perusahaan>> call, @NonNull Response<List<Perusahaan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResultPer(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Perusahaan>> call, @NonNull Throwable t) {
                view.onErrorResult(t.getLocalizedMessage());
            }
        });
    }

    void getRekap(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Rekap>> call = apiInterface.getReks(id);
        call.enqueue(new Callback<List<Rekap>>() {
            @Override
            public void onResponse(@NonNull Call<List<Rekap>> call, @NonNull Response<List<Rekap>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetReseltRek(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Rekap>> call, @NonNull Throwable t) {
                view.onErrorResult(t.getLocalizedMessage());
            }
        });
    }
}
