package com.skariga.simorin.ortu;

import androidx.annotation.NonNull;

import com.skariga.simorin.api.ApiClient;
import com.skariga.simorin.api.ApiInterface;
import com.skariga.simorin.model.AbsenOrtu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatAbsenOrangTuaPresenter {

    private LihatAbsenOrangTuaView view;

    public LihatAbsenOrangTuaPresenter(LihatAbsenOrangTuaView view) {
        this.view = view;
    }

    void getAbsensi(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<AbsenOrtu>> call = apiInterface.getAbsensi(id);
        call.enqueue(new Callback<List<AbsenOrtu>>() {
            @Override
            public void onResponse(@NonNull Call<List<AbsenOrtu>> call, @NonNull Response<List<AbsenOrtu>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AbsenOrtu>> call, @NonNull Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
