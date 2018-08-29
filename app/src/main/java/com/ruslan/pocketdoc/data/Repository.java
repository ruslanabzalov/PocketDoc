package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.api.CreateRecordRequest;
import com.ruslan.pocketdoc.api.CreateRecordResponse;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.ArrayList;
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
                    // TODO: Работает некорректно. Переделать!
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
                    // TODO: Работает некорректно. Переделать!
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
        return Flowable.zip(mRemoteDataSource.getClinics(0, 500),
                mRemoteDataSource.getClinics(500, 500), (firstClinics, secondClinics) -> {
            List<Clinic> allClinics = new ArrayList<>(firstClinics);
            allClinics.addAll(secondClinics);
            return allClinics;
        })
                .doOnNext(mLocalDataSource::saveClinics);
    }

    /**
     * Метод получения списка всех мед. учреждений из БД.
     * @return Список всех мед. учреждений.
     */
    public Flowable<List<Clinic>> getAllClinicsFromDb() {
        return mLocalDataSource.getAllClinics();
    }

    /**
     * Метод получения списка только клиник.
     * @return Список только клиник.
     */
    public Flowable<List<Clinic>> getOnlyClinicsFromDb(String isDiagnostic) {
        return mLocalDataSource.getOnlyClinics(isDiagnostic);
    }

    /**
     * Метод получения списка только диагностических центров.
     * @return Список диагностических центров.
     */
    public Flowable<List<Clinic>> getOnlyDiagnosticsFromDb(String isDiagnostic) {
        return mLocalDataSource.getOnlyDiagnostics(isDiagnostic);
    }

    /**
     * Метод получения информации о клинике из БД по идентификатору.
     * @param id Идентификатор клиники.
     * @return Подробная информация о клинике.
     */
    public Single<Clinic> getClinicByIdFromDb(int id) {
        return mLocalDataSource.getClinicById(id);
    }

    /**
     * Метод формирования заявки.
     * @param recordRequest Тело запроса, для формирования заявки.
     * @return Ответ на запрос.
     */
    public Single<CreateRecordResponse> createRecord(CreateRecordRequest recordRequest) {
        return mRemoteDataSource.createRecord(recordRequest);
    }
}
