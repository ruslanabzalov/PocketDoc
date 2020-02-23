package abzalov.ruslan.pocketdoc.drugs.drug;

import java.util.UUID;

/**
 * Класс, описывающий лекарство.
 */
public class Drug {

    private UUID mId; // Идентификатор лекарства.
    private String mName; // Название лекарства.
    private String mUse; // Способ применения.
    private int mDurationInDays; // Продолжительность в днях.
    private String mDosage; // Дозировка.
    private int mHourlyGaps; // Принимать каждые.
    private String mFirstReception; // Время первого приёма.
    private String mLastReception; // Время последнего приёма.

    public Drug() {
        this.mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUse() {
        return mUse;
    }

    public void setUse(String use) {
        mUse = use;
    }

    public int getDurationInDays() {
        return mDurationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        mDurationInDays = durationInDays;
    }

    public String getDosage() {
        return mDosage;
    }

    public void setDosage(String dosage) {
        mDosage = dosage;
    }

    public int getHourlyGaps() {
        return mHourlyGaps;
    }

    public void setHourlyGaps(int hourlyGaps) {
        mHourlyGaps = hourlyGaps;
    }

    public String getFirstReception() {
        return mFirstReception;
    }

    public void setFirstReception(String firstReception) {
        mFirstReception = firstReception;
    }

    public String getLastReception() {
        return mLastReception;
    }

    public void setLastReception(String lastReception) {
        mLastReception = lastReception;
    }
}
