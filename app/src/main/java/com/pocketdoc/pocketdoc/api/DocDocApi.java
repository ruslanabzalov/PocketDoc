package com.pocketdoc.pocketdoc.api;

import com.pocketdoc.pocketdoc.data.CityList;
import com.pocketdoc.pocketdoc.data.DocList;
import com.pocketdoc.pocketdoc.data.SpecsList;
import com.pocketdoc.pocketdoc.data.StationsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Интерфейс, описывающий API сервиса DocDoc.
 */
public interface DocDocApi {

    /**
     * GET-запрос для получения списка городов.
     * @param authorization Авторизация.
     * @return Список городов.
     */
    @GET("city")
    Call<CityList> getCities(@Header("Authorization") String authorization);

    /**
     * GET-запрос для получения списка станций метро в определённом городе.
     * @param authorization Авторизация.
     * @param cityId Идентификатор города.
     * @return Список станций метро.
     */
    @GET("metro/city/{id}")
    Call<StationsList> getStations(
            @Header("Authorization") String authorization, @Path("id") int cityId
    );

    /**
     * GET-запрос для получения списка всех специальностей врачей в конкретном городе.
     * @param authorization Авторизация.
     * @param cityId Идентификатор города.
     * @return Список всех специальностей.
     */
    @GET("speciality/city/{id}")
    Call<SpecsList> getSpecs(
            @Header("Authorization") String authorization, @Path("id") int cityId
    );

    /**
     * GET-запрос для получения списка врачей по определённым параметрам.
     * @param authorization Авторизация.
     * @param start Начальный индекс запрашиваемых врачей.
     * @param count Количество запрашиваемых врачей.
     * @param cityId Идентификатор города.
     * @param specId Идентификатор специальности.
     * @param stationId Идентификатор станции метро.
     * @param near Режим поиска.
     * @param order Тип сортировки.
     * @param children Флаг детских или взрослых врачей.
     * @param home Флаг вызова на дом или посещения в клинике.
     * @param withSlots Флаг запроса расписания.
     * @param slotsDays Количество дней, на которые нужно выводит расписание.
     * @return Список врачей.
     */
    @GET("doctor/list/start/{start}/count/{count}/city/{cityId}/speciality/{specId}/" +
            "stations/{stationId}/near/{near}/order/{order}/deti/{deti}/na-dom/{na-dom}/" +
            "withSlots/{withSlots}/slotsDays/{slotsDays}")
    Call<DocList> getDocs(
            @Header("Authorization") String authorization, @Path("start") int start,
            @Path("count") int count, @Path("cityId") int cityId,
            @Path("specId") String specId, @Path("stationId") String stationId,
            @Path("near") String near, @Path("order") String order,
            @Path("deti") int children, @Path("na-dom") int home,
            @Path("withSlots") int withSlots, @Path("slotsDays") int slotsDays
    );
}
