package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DocDocApi {

    @GET("speciality/city/1")
    Flowable<SpecialityList> getSpecialities();

    @GET("metro/city/1")
    Flowable<StationList> getStations();

    @GET("doctor/list/start/0/count/500/city/1/speciality/{specialityId}/" +
            "stations/{stationId}/near/strict/order/-rating/deti/0/na-dom/0/withSlots/0")
    Flowable<DoctorList> getDoctors(
            @Path("specialityId") String specialityId, @Path("stationId") String stationId
    );

    @GET("clinic/count/city/1/type/1,2")
    Flowable<Integer> getClinicsNumber();

    @GET("clinic/list/start/0/count/500/city/1/type/1,2")
    Flowable<ClinicList> getClinics();
}
