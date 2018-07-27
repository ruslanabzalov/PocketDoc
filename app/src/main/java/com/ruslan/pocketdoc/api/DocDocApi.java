package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorInfo;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.doctors.slots.SlotList;
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
     * Метод получения списка специальностей врачей.
     * @return Список специальностей врачей.
     */
    @GET("speciality/city/1")
    Flowable<SpecialityList> getSpecialities();

    /**
     * Метод получения списка станций метро.
     * @return Список станций метро.
     */
    @GET("metro/city/1")
    Flowable<StationList> getStations();

    /**
     * Метод получения списка врачей, выбранных по определённым параметрам.
     * @param specialityId Идентификатор специальности.
     * @param stationId Идентификатор станции метро.
     * @return Список врачей.
     */
    @GET("doctor/list/start/0/count/500/city/1/speciality/{specialityId}/stations/{stationId}" +
            "/near/strict/order/-rating/deti/0/na-dom/0/withSlots/1/slotsDays/1")
    Flowable<DoctorList> getDoctors(
            @Path("specialityId") String specialityId, @Path("stationId") String stationId);

    /**
     * Метод получения информации о враче.
     * @param doctorId Идентификатор врача.
     * @return Информация о враче.
     */
    @GET("doctor/{doctorId}/city/1/withSlots/1/slotsDays/1")
    Flowable<DoctorInfo> getDoctor(@Path("doctorId") int doctorId);

    /**
     * Метод получения списка клиник и диагностических центров.
     * @param start Начальный индекс.
     * @param count Количество медицинских центров.
     * @return Список клиник и диагностических центров.
     */
    @GET("clinic/list/start/{start}/count/{count}/city/1/type/1,2")
    Flowable<ClinicList> getClinics(@Path("start") int start, @Path("count") int count);

    /**
     * Метод получения информации о клинике.
     * @param clinicId Идентификатор клиники.
     * @return Информация о клинике.
     */
    @GET("clinic/{clinicId}")
    Flowable<Clinic> getClinicInfo(@Path("clinicId") int clinicId);

    /**
     * Метод получения расписания врача в конкретной клинике.
     * @param doctorId Идентификатор врача.
     * @param clinicId Идентификатор клиники.
     * @param startDate Начальная дата расписания.
     * @param finishDate Конечная дата расписания.
     * @return Расписание врача.
     */
    @GET("slot/list/doctor/{doctorId}/clinic/{clinicId}/from/{startDate}/to/{finishDate}")
    Flowable<SlotList> getSlots(
            @Path("doctorId") int doctorId, @Path("clinicId") int clinicId,
            @Path("startDate") String startDate, @Path("finishDate") String finishDate);
}
