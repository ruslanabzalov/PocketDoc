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

public interface DocDocApi {

    @GET("speciality/city/1")
    Flowable<SpecialityList> getSpecialities();

    @GET("metro/city/1")
    Flowable<StationList> getStations();

    @GET("doctor/list/start/0/count/500/city/1/speciality/{specialityId}/stations/{stationId}" +
            "/near/strict/order/-rating/deti/0/na-dom/0/withSlots/1/slotsDays/1")
    Flowable<DoctorList> getDoctors(
            @Path("specialityId") String specialityId, @Path("stationId") String stationId
    );

    @GET("doctor/{doctorId}/city/1/withSlots/1/slotsDays/1")
    Flowable<DoctorInfo> getDoctor(@Path("doctorId") int doctorId);

    @GET("clinic/list/start/0/count/500/city/1/type/1,2")
    Flowable<ClinicList> getClinics();

    @GET("clinic/{clinicId}")
    Flowable<Clinic> getClinicInfo(@Path("clinicId") int clinicId);

    @GET("slot/list/doctor/{doctorId}/clinic/{clinicId}/from/{startDate}/to/{finishDate}")
    Flowable<SlotList> getSlots(
            @Path("doctorId") int doctorId, @Path("clinicId") int clinicId,
            @Path("startDate") String startDate, @Path("finishDate") String finishDate
    );
}
