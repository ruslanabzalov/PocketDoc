package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

interface LocalDataSourceContract extends DataSourceContract {

    void saveSpecialities(List<Speciality> specialities);

    void saveStations(List<Station> stations);

    void saveClinics(List<Clinic> clinics);
}
