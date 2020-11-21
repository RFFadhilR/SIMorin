package com.skariga.simorin.siswa;

import com.skariga.simorin.api.ApiClient;
import com.skariga.simorin.api.ApiInterface;
import com.skariga.simorin.model.JurnalPerusahaan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListJurnalSiswaPresenter {

    private ListJurnalSiswaView view;

    public ListJurnalSiswaPresenter(ListJurnalSiswaView view) {
        this.view = view;
    }

    void getJurnalSiswa(String id) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<JurnalPerusahaan>> call = apiInterface.getJuranlSiswa(id);
        call.enqueue(new Callback<List<JurnalPerusahaan>>() {
            @Override
            public void onResponse(Call<List<JurnalPerusahaan>> call, Response<List<JurnalPerusahaan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JurnalPerusahaan>> call, Throwable t) {
                view.onErrorResult(t.getLocalizedMessage());
            }
        });
    }
}
