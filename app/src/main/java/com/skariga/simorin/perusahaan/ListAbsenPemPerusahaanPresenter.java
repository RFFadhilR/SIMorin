package com.skariga.simorin.perusahaan;

import androidx.annotation.NonNull;

import com.skariga.simorin.helper.Absen;
import com.skariga.simorin.helper.ApiClient;
import com.skariga.simorin.helper.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAbsenPemPerusahaanPresenter {

    private ListAbsenPemPerusahaanView view;

    public ListAbsenPemPerusahaanPresenter(ListAbsenPemPerusahaanView view) {
        this.view = view;
    }

    void getData() {
        view.ShowLoading();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Absen>> call = apiInterface.getAbsens();
        call.enqueue(new Callback<List<Absen>>() {
            @Override
            public void onResponse(@NonNull Call<List<Absen>> call, @NonNull Response<List<Absen>> response) {
                view.HideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Absen>> call, @NonNull Throwable t) {
                view.HideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
