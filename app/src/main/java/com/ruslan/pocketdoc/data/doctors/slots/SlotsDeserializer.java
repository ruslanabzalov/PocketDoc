package com.ruslan.pocketdoc.data.doctors.slots;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Класс, описывающий пользовательский десериализатор расписания врачей.
 */
public class SlotsDeserializer implements JsonDeserializer<SlotList> {

    @Override
    public SlotList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (!json.isJsonArray()) {
            JsonObject rootJsonObject = json.getAsJsonObject();
            Set<String> clinicsIds = rootJsonObject.keySet();
            SlotList slotList = new SlotList();
            List<Slot> slots = new ArrayList<>();
            Slot slot = new Slot();
            for (String clinicId : clinicsIds) {
                slot.setClinicId(clinicId);
                JsonArray clinicSlotsArray = rootJsonObject.getAsJsonArray(clinicId);
                List<Schedule> schedules = new ArrayList<>();
                for (JsonElement slotElement : clinicSlotsArray) {
                    JsonObject slotObject = slotElement.getAsJsonObject();
                    Schedule schedule = new Schedule(
                            slotObject.get("Id").getAsString(),
                            slotObject.get("StartTime").getAsString(),
                            slotObject.get("FinishTime").getAsString());
                    schedules.add(schedule);
                }
                slot.setSchedules(schedules);
                slots.add(slot);
            }
            slotList.setSlots(slots);
            return slotList;
        } else {
            return null;
        }
    }
}
