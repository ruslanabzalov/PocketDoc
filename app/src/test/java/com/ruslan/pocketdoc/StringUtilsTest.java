package com.ruslan.pocketdoc;

import com.ruslan.pocketdoc.data.specialities.Speciality;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

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
        assertEquals("Специальность не указана",
                StringUtils.getCorrectSpecialitiesString(Arrays.asList(new Speciality[0])));
        assertEquals("Хирург",
                StringUtils.getCorrectSpecialitiesString(Arrays.asList(mOneSpeciality)));
        assertEquals("Терапевт, уролог",
                StringUtils.getCorrectSpecialitiesString(Arrays.asList(mTwoSpecialities)));
        assertEquals("Уролог, хирург, терапевт",
                StringUtils.getCorrectSpecialitiesString(Arrays.asList(mThreeSpecialities)));
    }

    @Test
    public void correctExperienceStringTest() {
        assertEquals("Нет опыта", StringUtils.getCorrectExperienceString(0));
        assertEquals("13 лет", StringUtils.getCorrectExperienceString(13));
        assertEquals("20 лет", StringUtils.getCorrectExperienceString(20));
        assertEquals("1 год", StringUtils.getCorrectExperienceString(1));
        assertEquals("34 года", StringUtils.getCorrectExperienceString(34));
        assertEquals("39 лет", StringUtils.getCorrectExperienceString(39));
    }

    @Test
    public void correctAgePriceStringTest() {
        assertEquals("Стоимость не указана", StringUtils.getCorrectPriceString(0));
        assertEquals("3000\u20bd", StringUtils.getCorrectPriceString(3000));
    }

    @Test
    public void correctDateStringTest() {
        assertEquals("", StringUtils.makeCorrectDateString(null));
        assertEquals("2018-08-20", StringUtils.makeCorrectDateString(new Date()));
    }
}