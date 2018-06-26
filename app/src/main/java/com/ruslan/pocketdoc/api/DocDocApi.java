package com.ruslan.pocketdoc.api;

import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DocDocApi {

    @GET("metro/city/1")
    Call<StationList> getStations();

    @GET("speciality/city/1")
    Call<SpecialityList> getSpecialities();

    @GET("doctor/list/attachView/{attachView}/count/{count}/city/1/speciality/{specId}/" +
            "stations/{stationId}/near/{near}/order/{order}/deti/{deti}/na-dom/{na-dom}/" +
            "withSlots/{withSlots}/slotsDays/{slotsDays}")
    Call<DoctorList> getDoctors(
            @Path("attachView") int start, @Path("count") int count, @Path("specId") String specId,
            @Path("stationId") String stationId, @Path("near") String near,
            @Path("order") String order, @Path("deti") int children, @Path("na-dom") int home,
            @Path("withSlots") int withSlots, @Path("slotsDays") int slotsDays
    );

    @GET("doctor/{doctorId}/city/{cityId}/withSlots/{withSlots}/slotsDays{slotsDays}")
    Call<Doctor> getDoctor(
            @Path("doctorId") int doctorId, @Path("withSlots") int withSlots,
            @Path("slotsDays") int slotsDays
    );

    @GET("clinic/count/city/1/type/1,2")
    Call<Integer> getClinicsNumber();

    @GET("clinic/list/start/0/count/500/city/1/type/1,2")
    Call<ClinicList> getClinics();
}
