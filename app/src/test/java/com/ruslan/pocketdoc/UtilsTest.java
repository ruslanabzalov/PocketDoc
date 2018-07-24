package com.ruslan.pocketdoc;

import com.ruslan.pocketdoc.data.specialities.Speciality;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    private Speciality[] mOneSpeciality;
    private Speciality[] mTwoSpecialities;
    private Speciality[] mThreeSpecialities;

    @Before
    public void setUpSpecialities() {
        Speciality firstSpeciality = new Speciality("123");
        firstSpeciality.setName("Уролог");
        Speciality secondSpeciality = new Speciality("234");
        secondSpeciality.setName("Терапевт");
        Speciality thirdSpeciality = new Speciality("456");
        thirdSpeciality.setName("Хирург");
        mOneSpeciality = new Speciality[] {thirdSpeciality};
        mTwoSpecialities = new Speciality[] {secondSpeciality, firstSpeciality};
        mThreeSpecialities = new Speciality[] {firstSpeciality, thirdSpeciality, secondSpeciality};
    }

    @Test
    public void correctSpecialitiesStringTest() {
        assertEquals("Хирург",
                Utils.getCorrectSpecialitiesString(Arrays.asList(mOneSpeciality)));
        assertEquals("Терапевт, уролог",
                Utils.getCorrectSpecialitiesString(Arrays.asList(mTwoSpecialities)));
        assertEquals("Уролог, хирург, терапевт",
                Utils.getCorrectSpecialitiesString(Arrays.asList(mThreeSpecialities)));
    }

    @Test
    public void correctExperienceStringTest() {
        assertEquals("Нет опыта", Utils.getCorrectExperienceString(0));
        assertEquals("13 лет", Utils.getCorrectExperienceString(13));
        assertEquals("20 лет", Utils.getCorrectExperienceString(20));
        assertEquals("1 год", Utils.getCorrectExperienceString(1));
        assertEquals("34 года", Utils.getCorrectExperienceString(34));
        assertEquals("39 лет", Utils.getCorrectExperienceString(39));
    }

    @Test
    public void correctAgePriceStringTest() {
        assertEquals("3000\u20bd", Utils.getCorrectPriceString(3000));
    }
}