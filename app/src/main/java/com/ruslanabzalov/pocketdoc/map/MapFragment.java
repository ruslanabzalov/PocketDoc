package com.ruslanabzalov.pocketdoc.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.ruslanabzalov.pocketdoc.R;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocApi;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocService;

import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends SupportMapFragment {

    private static String TAG = "MapFragment";

    private List<Clinic> mClinicList = null;
    private int mTotalClinicsNumber = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.map_fragment_label);
        getMapAsync((GoogleMap googleMap) -> {
            final CameraUpdate moscow =
                    CameraUpdateFactory.newLatLngZoom(new LatLng(55.751244, 37.618423), 10);
            googleMap.animateCamera(moscow);
            getClinicsData();
        });
    }

    /**
     * Метод для получения данных о клиниках.
     */
    private void getClinicsData() {
        // Создание REST-клиента.
        DocDocApi docDocApi = DocDocService.getClient();

        // Обработка GET-запроса для получения списка клиник.
        Call<ClinicList> clinics =
                docDocApi.getClinics(DocDocService.AUTHORIZATION, 0, 500, 1);
        clinics.enqueue(new Callback<ClinicList>() {
            @Override
            public void onResponse(@NonNull Call<ClinicList> call,
                                   @NonNull Response<ClinicList> response) {
                mClinicList = response.body().getClinics();
                mTotalClinicsNumber = Integer.parseInt(response.body().getTotalClinicsNumber());
                String positiveToast = getString(R.string.positive_toast);
                String totalClinics = String.format(Locale.US, "Total clinics in Moscow: %d",
                        mTotalClinicsNumber);
                String clinics = String.format(Locale.US, "Clinics received: %d",
                        mClinicList.size());
                Toasty.success(getContext(), positiveToast, Toast.LENGTH_LONG).show();
                Toasty.info(getContext(), totalClinics, Toast.LENGTH_LONG).show();
                Toasty.info(getContext(), clinics, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<ClinicList> call, @NonNull Throwable t) {
                String negativeToast = getString(R.string.negative_toast);
                Toasty.error(getContext(), negativeToast, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
