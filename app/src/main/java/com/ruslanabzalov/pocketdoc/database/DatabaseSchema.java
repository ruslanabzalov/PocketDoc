package com.ruslanabzalov.pocketdoc.database;

/**
 * Класс, описывающий схему базы данных.
 */
public class DatabaseSchema {

    /**
     * Класс, описывающий таблицу "История посещений".
     */
    public static final class MedicalRecordsTable {

        /**
         * Константа, хранящая название таблицы "История посещений".
         */
        public static final String NAME = "medical_records";

        /**
         * Класс, описывающий столбцы таблицы "История посещений".
         */
        public static final class Cols {
            public static final String DOC_TYPE = "doc_type";
            public static final String USER_NAME = "user_name";
            public static final String RECORD_DATE = "record_date";
        }
    }

    /**
     * Класс, описывающий таблицу "Медицинские центры".
     */
    public static final class HospitalsTable {

        /**
         * Константа, хранящая название таблицы "Медицинские центры".
         */
        public static final String NAME = "hospitals";

        /**
         * Класс, описывающий столбцы таблицы "Медицинские центры".
         */
        public static final class Cols {
            public static final String NAME = "name";
            public static final String TYPE = "type";
            public static final String DESCRIPTION = "description";
            public static final String ADDRESS = "address";
            public static final String PHONE = "phone";
            public static final String LONGITUDE = "longitude";
            public static final String LATITUDE = "latitude";
        }
    }
}
