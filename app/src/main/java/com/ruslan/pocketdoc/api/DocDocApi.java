package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import retrofit2.Call;
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
    Call<SpecialityList> getSpecialities();

    /**
     * Метод получения списка станций метро в Москве.
     * @return Список станций метро в Москве.
     */
    @GET("metro/city/1")
    Call<StationList> getStations();

    /**
     * Метод получения списка врачей в Москве по определённым параметрам.
     * @param start
     * @param count
     * @param specId
     * @param stationId
     * @param near
     * @param order
     * @param children
     * @param home
     * @return Список врачей.
     */
    @GET("doctor/list/start/{start}/count/{count}/city/1/speciality/{specId}/" +
            "stations/{stationId}/near/{near}/order/{order}/deti/{deti}/na-dom/{na-dom}/" +
            "withSlots/0")
    Call<DoctorList> getDoctors(
            @Path("start") int start, @Path("count") int count, @Path("specId") String specId,
            @Path("stationId") String stationId, @Path("near") String near,
            @Path("order") String order, @Path("deti") int children, @Path("na-dom") int home
    );

    /**
     * Метод получения информации о враче.
     * @param doctorId
     * @param withSlots
     * @param slotsDays
     * @return
     */
    @GET("doctor/{doctorId}/city/{cityId}/withSlots/{withSlots}/slotsDays{slotsDays}")
    Call<Doctor> getDoctor(
            @Path("doctorId") int doctorId, @Path("withSlots") int withSlots,
            @Path("slotsDays") int slotsDays
    );

    /**
     * Метод получения общего количества клиник и диагностических в Москве.
     * @return Общее количество клиник и диагностических центров в Москве.
     */
    @GET("clinic/count/city/1/type/1,2")
    Call<Integer> getClinicsNumber();

    /**
     * Метод получения списка клиник и диагностических центров в Москве.
     * @return Список клиник и диагностических центров в Москве.
     */
    @GET("clinic/list/start/0/count/500/city/1/type/1,2")
    Call<ClinicList> getClinics();
}
