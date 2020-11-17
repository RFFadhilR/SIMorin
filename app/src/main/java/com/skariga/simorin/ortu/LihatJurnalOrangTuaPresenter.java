package com.skariga.simorin.ortu;

import androidx.annotation.NonNull;

import com.skariga.simorin.api.ApiClient;
import com.skariga.simorin.api.ApiInterface;
import com.skariga.simorin.model.JurnalOrtu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatJurnalOrangTuaPresenter {

    private LihatJurnalOrangTuaView view;

    public LihatJurnalOrangTuaPresenter(LihatJurnalOrangTuaView view) {
        this.view = view;
    }

    void getJurnal(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<JurnalOrtu>> call = apiInterface.getJurnal(id);
        call.enqueue(new Callback<List<JurnalOrtu>>() {
            @Override
            public void onResponse(@NonNull Call<List<JurnalOrtu>> call, @NonNull Response<List<JurnalOrtu>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<JurnalOrtu>> call, @NonNull Throwable t) {
                view.onErrorResult(t.getLocalizedMessage());
            }
        });
    }
}
