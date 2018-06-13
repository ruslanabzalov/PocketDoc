package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

public interface DataSource {

    interface OnLoadFinishedListener<T> {

        void onSuccess(List<T> items);

        void onFailure(Throwable throwable);
    }

    void getSpecialities(OnLoadFinishedListener<Speciality> listener);

    void getStations(OnLoadFinishedListener<Station> listener);

    // TODO: Добавить методы для получения и сохранения списка клиник.
}
