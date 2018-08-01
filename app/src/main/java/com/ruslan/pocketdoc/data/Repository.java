package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Класс, описывающий репозиторий.
 */
public class Repository {

    @Inject
    RemoteDataSource mRemoteDataSource;

    @Inject
    LocalDataSource mLocalDataSource;

    public Repository() {
        App.getComponent().inject(this);
    }

    /**
     * Метод загрузки списка специальностей врачей с помощью Room или API
     * в зависимости от наличия данных в БД.
     * @param forceUpdate Флаг принудительного обновления списка специальностей врачей.
     * @return Flowable списка специальностей врачей.
     */
    public Flowable<List<Speciality>> getSpecialities(boolean forceUpdate) {
        if (forceUpdate) {
            return getAndSaveRemoteSpecialities();
        } else {
            return mLocalDataSource.getSpecialities()
                    // Почему-то выполняется 2 раза: сначала первое, потом второе.
                    .switchMap(specialities -> (specialities.size() == 0)
                            ? getAndSaveRemoteSpecialities()
                            : Flowable.just(specialities));
        }
    }

    /**
     * Метод загрузки списка специальностей врачей с помощью API и их сохранения в БД.
     * @return Flowable списка специальностей врачей.
     */
    private Flowable<List<Speciality>> getAndSaveRemoteSpecialities() {
        return mRemoteDataSource.getSpecialities()
                .doOnNext(mLocalDataSource::saveSpecialities);
    }

    /**
     * Метод загрузки списка станций метро с помощью Room или API
     * в зависимости от наличия данных в БД.
     * @param forceUpdate Флаг принудительного обновления списка станций метро.
     * @return Flowable списка станций метро.
     */
    public Flowable<List<Station>> getStations(boolean forceUpdate) {
        if (forceUpdate) {
            return getAndSaveRemoteStations();
        } else {
            return mLocalDataSource.getStations()
                    // Почему-то выполняется 2 раза: сначала первое, потом второе.
                    .switchMap(stations -> (stations.size() == 0)
                            ? getAndSaveRemoteStations()
                            : Flowable.just(stations));
        }
    }

    /**
     * Метод загрузки списка станций метро с помощью API и их сохранения в БД.
     * @return Flowable списка станций метро.
     */
    private Flowable<List<Station>> getAndSaveRemoteStations() {
        return mRemoteDataSource.getStations()
                .doOnNext(mLocalDataSource::saveStations);
    }

    /**
     * Метод загрузки списка врачей с помощью API.
     * @param specialityId Идентификатор специальности врачей.
     * @param stationId Идентификатор станции метро.
     * @return Flowable списка врачей.
     */
    public Flowable<List<Doctor>> getDoctors(String specialityId, String stationId) {
        return mRemoteDataSource.getDoctors(specialityId, stationId);
    }

    /**
     * Метод загрузки информации о конкретном враче.
     * @param doctorId Идентификатор врача.
     * @return Single информации о враче.
     */
    public Single<Doctor> getDoctorInfo(int doctorId) {
        return mRemoteDataSource.getDoctor(doctorId);
    }

    /**
     * Метод загрузки количества клиник в БД.
     * @return Single количества клиник.
     */
    public Single<Integer> getClinicsCount() {
        return mLocalDataSource.countClinics();
    }

    /**
     * Метод загрузки списка клиник с помощью API и их сохранения в БД.
     * @return Flowable списка клиник.
     */
    public Flowable<List<Clinic>> getClinicsFromApi() {
        return Flowable.concat(mRemoteDataSource.getClinics(0, 500),
                mRemoteDataSource.getClinics(500, 500))
                .doOnNext(mLocalDataSource::saveClinics);
    }

    /**
     * Метод загрузки списка клиник с помощью Room.
     * @return Flowable списка клиник.
     */
    public Flowable<List<Clinic>> getClinicsFromDb() {
        return mLocalDataSource.getClinics();
    }
}
