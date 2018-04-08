package com.ruslanabzalov.pocketdoc.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.ruslanabzalov.pocketdoc.DocDocApi;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment extends SupportMapFragment {

    private static final String LOGIN = "partner.13849";
    private static final String PASSWORD = "BIQWlAdw";

    private List<Clinic> mClinicList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync((GoogleMap googleMap) -> {
            getClinicsData();
        });
    }

    /**
     * Метод для получения данных о клиниках.
     */
    private void getClinicsData() {
        String basicAuth ="Basic " +
                new String(Base64.encode((LOGIN + ":" + PASSWORD).getBytes(), Base64.NO_WRAP));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://" + LOGIN + ":" + PASSWORD
                        + "@back.docdoc.ru/api/rest/1.0.6/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DocDocApi docDocApi = retrofit.create(DocDocApi.class);
        Call<ClinicList> clinics = docDocApi.getClinics(basicAuth, 0, 500, 1);
        clinics.enqueue(new Callback<ClinicList>() {
            @Override
            public void onResponse(@NonNull Call<ClinicList> call,
                                   @NonNull Response<ClinicList> response) {
                String positiveResponse = "Данные о клиниках успешно загружены!";
                Toast.makeText(getContext(), positiveResponse, Toast.LENGTH_SHORT).show();
                mClinicList = Objects.requireNonNull(response.body()).getClinics();
            }

            @Override
            public void onFailure(@NonNull Call<ClinicList> call, @NonNull Throwable t) {
                String negativeResponse = "Ошибка получения данных от сервера!";
                Toast.makeText(getContext(), negativeResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
