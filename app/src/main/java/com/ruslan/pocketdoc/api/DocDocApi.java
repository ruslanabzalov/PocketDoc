package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.CitiesList;
import com.ruslan.pocketdoc.data.DoctorsList;
import com.ruslan.pocketdoc.data.SpecialitiesList;
import com.ruslan.pocketdoc.data.StationsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DocDocApi {

    @GET("city")
    Call<CitiesList> getCities(@Header("Authorization") String authorization);

    @GET("metro/city/{id}")
    Call<StationsList> getStations(
            @Header("Authorization") String authorization, @Path("id") int cityId
    );

    @GET("speciality/city/{id}")
    Call<SpecialitiesList> getSpecialities(
            @Header("Authorization") String authorization, @Path("id") int cityId
    );

    @GET("doctor/list/start/{start}/count/{count}/city/{cityId}/speciality/{specId}/" +
            "stations/{stationId}/near/{near}/order/{order}/deti/{deti}/na-dom/{na-dom}/" +
            "withSlots/{withSlots}/slotsDays/{slotsDays}")
    Call<DoctorsList> getDoctors(
            @Header("Authorization") String authorization, @Path("start") int start,
            @Path("count") int count, @Path("cityId") int cityId,
            @Path("specId") String specId, @Path("stationId") String stationId,
            @Path("near") String near, @Path("order") String order,
            @Path("deti") int children, @Path("na-dom") int home,
            @Path("withSlots") int withSlots, @Path("slotsDays") int slotsDays
    );
}
