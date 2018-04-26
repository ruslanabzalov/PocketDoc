package com.ruslanabzalov.pocketdoc.docdoc;

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
}
