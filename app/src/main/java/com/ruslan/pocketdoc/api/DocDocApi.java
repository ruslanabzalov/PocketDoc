package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorInfo;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DocDocApi {

    @GET("speciality/city/1")
    Flowable<SpecialityList> getSpecialities();

    @GET("metro/city/1")
    Flowable<StationList> getStations();

    @GET(
            "doctor/list/start/0/count/500/city/1/speciality/{specialityId}/stations/{stationId}/" +
                    "near/strict/order/-rating/deti/0/na-dom/0/withSlots/1/slotsDays/14"
    )
    Flowable<DoctorList> getDoctors(
            @Path("specialityId") String specialityId, @Path("stationId") String stationId
    );

    @GET("doctor/{doctorId}/city/1/withSlots/1/slotsDays/14")
    Single<DoctorInfo> getDoctor(@Path("doctorId") int doctorId);

    @GET("clinic/list/start/{start}/count/{count}/city/1/type/1,2")
    Flowable<ClinicList> getClinics(@Path("start") int start, @Path("count") int count);

    @Headers("Content-Type: application/json")
    @POST("request")
    Single<CreateRecordResponse> createRecord(@Body Requestable body);
}
