package com.ruslanabzalov.pocketdoc.docdoc;

import com.ruslanabzalov.pocketdoc.doctors.DoctorsList;
import com.ruslanabzalov.pocketdoc.doctors.SpecialitiesList;
import com.ruslanabzalov.pocketdoc.doctors.StationsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Интерфейс, описывающий API сервиса DocDoc.
 */
public interface DocDocApi {

    /**
     * GET-запрос для получения списка станций метро в определённом городе.
     * @param authorization Авторизация.
     * @param cityId Идентификатор города.
     * @return Список станций метро в определённом городе.
     */
    @GET("metro/city/{id}")
    Call<StationsList> getStations(
            @Header("Authorization") String authorization, @Path("id") int cityId);

    /**
     * GET-запрос для получения списка всех специальностей врачей в конкретном городе.
     * @param authorization Авторизация.
     * @param cityId Идентификатор города.
     * @return Список всех специальностей врачей в определённом городе.
     */
    @GET("speciality/city/{id}")
    Call<SpecialitiesList> getSpecialities(
            @Header("Authorization") String authorization, @Path("id") int cityId);

    /**
     * GET-запрос для получения списка врачей по определённым параметрам.
     * @param authorization Авторизация.
     * @param start Начальный индекс запрашиваемых врачей.
     * @param count Количество запрашиваемых врачей.
     * @param city Идентификатор города.
     * @param speciality Идентификатор специальности.
     * @param station Идентификатор станции метро.
     * @param near Режим поиска.
     * @param order Тип сортировки.
     * @param children Флаг детских или взрослых врачей.
     * @param home Флаг вызова на дом или посещения в клинике.
     * @param withSlots Флаг запроса расписания.
     * @param slotsDays Количество дней, на которые нужно выводит расписание.
     * @return Список найденных врачей.
     */
    @GET("doctor/list/start/{start}/count/{count}/city/{city}/speciality/{speciality}/" +
            "stations/{stations}/near/{near}/order/{order}/deti/{deti}/na-dom/{na-dom}/" +
            "withSlots/{withSlots}/slotsDays/{slotsDays}")
    Call<DoctorsList> getDoctors(
            @Header("Authorization") String authorization, @Path("start") int start,
            @Path("count") int count, @Path("city") int city,
            @Path("speciality") int speciality, @Path("stations") int station,
            @Path("near") String near, @Path("order") String order,
            @Path("deti") int children, @Path("na-dom") int home,
            @Path("withSlots") int withSlots, @Path("slotsDays") int slotsDays
    );
}
