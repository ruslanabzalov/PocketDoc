package com.ruslanabzalov.pocketdoc;

import com.ruslanabzalov.pocketdoc.doctors.DoctorList;
import com.ruslanabzalov.pocketdoc.doctors.MetroList;
import com.ruslanabzalov.pocketdoc.doctors.SpecialityList;
import com.ruslanabzalov.pocketdoc.map.Clinic;
import com.ruslanabzalov.pocketdoc.map.ClinicList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DocDocApi {

    /**
     * GET-запрос для получения списка станций метро в определённом городе.
     * @param authorization Авторизация.
     * @param cityId Идентификатор города.
     * @return Список станций метро в определённом городе.
     */
    @GET("metro/city/{id}")
    Call<MetroList> getStations(
            @Header("Authorization") String authorization, @Path("id") int cityId);

    /**
     * GET-запрос для получения списка клиник в определённом городе.
     * @param authorization Авторизация.
     * @param startIndex Начальный индекс списка клиник.
     * @param count Количество клиник.
     * @param cityId Идентификатор города.
     * @return Список клиник определённого города.
     */
    @GET("clinic/list/start/{start}/count/{count}/city/{id}/type/1,2,3")
    Call<ClinicList> getClinics(
            @Header("Authorization") String authorization, @Path("start") int startIndex,
            @Path("count") int count, @Path("id") int cityId);

    /**
     * GET-запрос для получения подробной информации о клинике по её идентификатору.
     * @param authorization Авторизация.
     * @param clinicId Идентификатор клиники.
     * @return Подробная информация о конкретной клинике.
     */
    @GET("clinic/{id}")
    Call<Clinic> getClinicInfo(
            @Header("Authorization") String authorization, @Path("id") int clinicId);

    /**
     * GET-запрос для получения списка всех специальностей врачей в конкретном городе.
     * @param authorization Авторизация.
     * @param cityId Идентификатор города.
     * @return Список всех специальностей врачей в определённом городе.
     */
    @GET("speciality/city/{id}")
    Call<SpecialityList> getSpecialities(
            @Header("Authorization") String authorization, @Path("id") int cityId);

    /**
     * GET-запрос для получения списка врачей, отобранных по определённым критериям.
     * @param authorization Авторизация.
     * @param startIndex Начальный индекс списка врачей.
     * @param count Количество врачей.
     * @param cityId Идентификатор города.
     * @param specialityId Идентификатор специальности.
     * @param stationId Идентификатор станции метро.
     * @param near Удалённость от станции метро.
     * @param order Тип сортировки.
     * @param children Дети или взрослые.
     * @param home На дом или нет.
     * @param withSlots Отображать расписание.
     * @param slotsDays Количество дней в отображаемом расписании.
     * @return Список врачей, выбранных по определённым критериям.
     */
    @GET("doctor/list/start/{start}/count/{count}/city/{city}/speciality/{speciality}/" +
            "stations/{stations}/near/{near}/order/{order}/deti/{children}/na-dom/{home}/" +
            "withSlots/{withSlots}/slotsDays/{slotsDays}")
    Call<DoctorList> getDoctors(
            @Header("Authorization") String authorization, @Path("start") int startIndex,
            @Path("count") int count, @Path("city") int cityId,
            @Path("speciality") int specialityId, @Path("stations") String stationId,
            @Path("near") String near, @Path("order") String order,
            @Path("children") String children, @Path("home") String home,
            @Path("withSlots") byte withSlots, @Path("slotsDays") byte slotsDays);
}
