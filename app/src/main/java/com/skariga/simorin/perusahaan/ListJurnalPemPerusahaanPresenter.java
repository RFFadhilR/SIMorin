package com.skariga.simorin.perusahaan;

import androidx.annotation.NonNull;

import com.skariga.simorin.api.ApiClient;
import com.skariga.simorin.api.ApiInterface;
import com.skariga.simorin.model.JurnalPerusahaan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListJurnalPemPerusahaanPresenter {

    private ListJurnalPemPerusahaanView view;

    public ListJurnalPemPerusahaanPresenter(ListJurnalPemPerusahaanView view) {
        this.view = view;
    }

    void getDatas(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<JurnalPerusahaan>> call = apiInterface.getJurnals(id);
        call.enqueue(new Callback<List<JurnalPerusahaan>>() {
            @Override
            public void onResponse(@NonNull Call<List<JurnalPerusahaan>> call, @NonNull Response<List<JurnalPerusahaan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResults(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<JurnalPerusahaan>> call, @NonNull Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
