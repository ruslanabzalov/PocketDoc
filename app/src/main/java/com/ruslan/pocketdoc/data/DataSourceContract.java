package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import io.reactivex.Observable;

public interface DataSourceContract {

    Observable<SpecialityList> getSpecialities();

    Observable<StationList> getStations();

    Observable<ClinicList> getClinics();
}
