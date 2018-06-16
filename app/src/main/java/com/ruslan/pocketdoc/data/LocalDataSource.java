package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

interface LocalDataSource extends DataSource {

    void saveSpecialities(List<Speciality> specialities);

    void saveStations(List<Station> stations);

    // TODO: Добавить метод для получения списка сохранённых записей, а также метод для сохранения одной записи.

}
