package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.CityList;
import com.ruslan.pocketdoc.data.DocList;
import com.ruslan.pocketdoc.data.SpecsList;
import com.ruslan.pocketdoc.data.StationsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DocDocApi {

    @GET("city")
    Call<CityList> getCities(@Header("Authorization") String authorization);

    @GET("metro/city/{id}")
    Call<StationsList> getStations(
            @Header("Authorization") String authorization, @Path("id") int cityId
    );

    @GET("speciality/city/{id}")
    Call<SpecsList> getSpecs(
            @Header("Authorization") String authorization, @Path("id") int cityId
    );

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
