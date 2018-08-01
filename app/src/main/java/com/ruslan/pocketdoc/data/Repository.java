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
     * Метод получения списка специальностей врачей с помощью Room или API
     * в зависимости от наличия данных в БД.
     * @param forceUpdate Флаг принудительного обновления списка специальностей врачей.
     * @return Список специальностей.
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
     * Метод получения списка специальностей врачей с помощью API и их сохранения в БД.
     * @return Список специальностей.
     */
    private Flowable<List<Speciality>> getAndSaveRemoteSpecialities() {
        return mRemoteDataSource.getSpecialities()
                .doOnNext(mLocalDataSource::saveSpecialities);
    }

    /**
     * Метод получения списка станций метро с помощью Room или API
     * в зависимости от наличия данных в БД.
     * @param forceUpdate Флаг принудительного обновления списка станций метро.
     * @return Список станций метро.
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
     * Метод получения списка станций метро с помощью API и их сохранения в БД.
     * @return Список станций метро.
     */
    private Flowable<List<Station>> getAndSaveRemoteStations() {
        return mRemoteDataSource.getStations()
                .doOnNext(mLocalDataSource::saveStations);
    }

    /**
     * Метод получения списка врачей с помощью API.
     * @param specialityId Идентификатор специальности врачей.
     * @param stationId Идентификатор станции метро.
     * @return Список врачей.
     */
    public Flowable<List<Doctor>> getDoctors(String specialityId, String stationId) {
        return mRemoteDataSource.getDoctors(specialityId, stationId);
    }

    /**
     * Метод получения подробной информации о враче.
     * @param doctorId Идентификатор врача.
     * @return Экземпляр врача.
     */
    public Single<Doctor> getDoctorInfo(int doctorId) {
        return mRemoteDataSource.getDoctor(doctorId);
    }

    /**
     * Метод получения количества клиник в БД.
     * @return Количество клиник.
     */
    public Single<Integer> getClinicsCount() {
        return mLocalDataSource.countClinics();
    }

    /**
     * Метод получения списка клиник с помощью API и их сохранения в БД.
     * @return Список клиник.
     */
    public Flowable<List<Clinic>> getClinicsFromApi() {
        return Flowable.concat(mRemoteDataSource.getClinics(0, 500),
                mRemoteDataSource.getClinics(500, 500))
                .doOnNext(mLocalDataSource::saveClinics);
    }

    /**
     * Метод получения списка клиник с из БД.
     * @return Список клиник.
     */
    public Flowable<List<Clinic>> getClinicsFromDb() {
        return mLocalDataSource.getClinics();
    }
}
