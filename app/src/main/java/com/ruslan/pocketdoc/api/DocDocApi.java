package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Интерфейс, описывающий API сервиса DocDoc.
 */
public interface DocDocApi {

    /**
     * Метод получения списка специальностей врачей в Москве.
     * @return Список специальностей врачей в Москве.
     */
    @GET("speciality/city/1")
    Flowable<SpecialityList> getSpecialities();

    /**
     * Метод получения списка станций метро в Москве.
     * @return Список станций метро в Москве.
     */
    @GET("metro/city/1")
    Flowable<StationList> getStations();

    /**
     * Метод получения списка врачей в Москве по идентификаторам специальности и станции метро.
     * @param specialityId Идентификатор специальности.
     * @param stationId Идентификатор станции метро.
     * @return Список врачей определённой специальности около определённой станции метро.
     */
    @GET("doctor/list/start/0/count/500/city/1/speciality/{specialityId}/" +
            "stations/{stationId}/near/strict/order/-rating/deti/0/na-dom/0/withSlots/0")
    Flowable<DoctorList> getDoctors(
            @Path("specialityId") String specialityId, @Path("stationId") String stationId
    );

    /**
     * Метод получения общего количества клиник и диагностических в Москве.
     * @return Общее количество клиник и диагностических центров в Москве.
     */
    @GET("clinic/count/city/1/type/1,2")
    Flowable<Integer> getClinicsNumber();

    /**
     * Метод получения списка клиник и диагностических центров в Москве.
     * @return Список клиник и диагностических центров в Москве.
     */
    @GET("clinic/list/start/0/count/500/city/1/type/1,2")
    Flowable<ClinicList> getClinics();
}
