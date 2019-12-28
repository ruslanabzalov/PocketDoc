package abzalov.ruslan.pocketdoc.data;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.api.CreateRecordRequestSchedule;
import abzalov.ruslan.pocketdoc.api.CreateRecordResponse;
import abzalov.ruslan.pocketdoc.api.DocDocApi;
import abzalov.ruslan.pocketdoc.data.clinics.Clinic;
import abzalov.ruslan.pocketdoc.data.clinics.ClinicList;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;
import abzalov.ruslan.pocketdoc.data.doctors.DoctorList;
import abzalov.ruslan.pocketdoc.data.specialities.Speciality;
import abzalov.ruslan.pocketdoc.data.specialities.SpecialityList;
import abzalov.ruslan.pocketdoc.data.stations.Station;
import abzalov.ruslan.pocketdoc.data.stations.StationList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RemoteDataSource implements RemoteDataSourceContract {

    @Inject
    DocDocApi mApi;

    public RemoteDataSource() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<List<Speciality>> getSpecialities() {
        return mApi.getSpecialities()
                .map(SpecialityList::getSpecialities);
    }

    @Override
    public Flowable<List<Station>> getStations() {
        return mApi.getStations()
                .map(StationList::getStations);
    }

    @Override
    public Flowable<List<Doctor>> getDoctors(String specialityId, String stationId) {
        return mApi.getDoctors(specialityId, stationId)
                .map(DoctorList::getDoctors);
    }

    @Override
    public Single<Doctor> getDoctor(int doctorId) {
        return mApi.getDoctor(doctorId)
                .map(doctorInfo -> doctorInfo.getDoctor().get(0));
    }

    @Override
    public Flowable<List<Clinic>> getClinics(int start, int count) {
        return mApi.getClinics(start, count)
                .map(ClinicList::getClinics);
    }

    @Override
    public Single<CreateRecordResponse> createRecord(CreateRecordRequestSchedule recordRequest) {
        return mApi.createRecord(recordRequest);
    }
}
