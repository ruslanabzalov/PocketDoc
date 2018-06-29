package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import io.reactivex.Observable;
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
    Observable<SpecialityList> getSpecialities();

    /**
     * Метод получения списка станций метро в Москве.
     * @return Список станций метро в Москве.
     */
    @GET("metro/city/1")
    Observable<StationList> getStations();

    /**
     * Метод получения списка врачей в Москве по определённым параметрам.
     * @param specialityId
     * @param stationId
     * @return Список врачей.
     */
    @GET("doctor/list/start/0/count/500/city/1/speciality/{specialityId}/" +
            "stations/{stationId}/near/strict/order/rating/deti/0/na-dom/0/withSlots/0")
    Observable<DoctorList> getDoctors(
            @Path("specialityId") String specialityId, @Path("stationId") String stationId
    );

    /**
     * Метод получения общего количества клиник и диагностических в Москве.
     * @return Общее количество клиник и диагностических центров в Москве.
     */
    @GET("clinic/count/city/1/type/1,2")
    Observable<Integer> getClinicsNumber();

    /**
     * Метод получения списка клиник и диагностических центров в Москве.
     * @return Список клиник и диагностических центров в Москве.
     */
    @GET("clinic/list/start/0/count/500/city/1/type/1,2")
    Observable<ClinicList> getClinics();
}
