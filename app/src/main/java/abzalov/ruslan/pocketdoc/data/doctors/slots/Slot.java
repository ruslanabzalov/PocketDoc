package abzalov.ruslan.pocketdoc.data.doctors.slots;

import java.util.List;

public class Slot {

    private String clinicId;

    private List<Schedule> mSchedules;

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public List<Schedule> getSchedules() {
        return mSchedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        mSchedules = schedules;
    }
}
