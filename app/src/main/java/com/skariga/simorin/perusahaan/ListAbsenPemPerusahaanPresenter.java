package com.skariga.simorin.perusahaan;

import androidx.annotation.NonNull;

import com.skariga.simorin.model.AbsenPerusahaan;
import com.skariga.simorin.api.ApiClient;
import com.skariga.simorin.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAbsenPemPerusahaanPresenter {

    private ListAbsenPemPerusahaanView view;

    public ListAbsenPemPerusahaanPresenter(ListAbsenPemPerusahaanView view) {
        this.view = view;
    }

    void getData(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<AbsenPerusahaan>> call = apiInterface.getAbsens(id);
        call.enqueue(new Callback<List<AbsenPerusahaan>>() {
            @Override
            public void onResponse(@NonNull Call<List<AbsenPerusahaan>> call, @NonNull Response<List<AbsenPerusahaan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AbsenPerusahaan>> call, @NonNull Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
