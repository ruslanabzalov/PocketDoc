package com.ruslanabzalov.pocketdoc.database;

/**
 * Класс, описывающий схему базы данных.
 */
public class DatabaseSchema {
    /**
     * Класс, описывающий таблицу записей к врачам.
     */
    public static final class MedicalRecordsTable {

        /**
         * Константа, хранящая имя таблицы.
         */
        public static final String NAME = "medical_records";

        /**
         * Класс, описывающий столбцы таблицы MedicalRecordsTable.
         */
        public static final class Cols {
            public static final String DOC_NAME = "doc_name";
//            public static final String DOC_TYPE = "doc_type";
//            public static final String CLINIC_NAME = "clinic_name";
            public static final String DOC_ADDRESS = "doc_address";
            public static final String DOC_DESCRIPTION = "doc_description";
//            public static final String USER_NAME = "user_name";
//            public static final String USER_PHONE = "user_phone";
//            public static final String RECORD_DATE = "record_date";
        }
    }
//
//    /**
//     * Класс, описывающий таблицу болезней.
//     */
//    public static final class DiseasesTable {
//
//        /**
//         * Константа, хранящая имя таблицы.
//         */
//        public static final String NAME = "diseases";
//
//        /**
//         * Класс, описывающий столбцы таблицы DiseasesTable.
//         */
//        public static final class Cols {
//            public static final String DISEASE_NAME = "disease_name";
//        }
//    }
}
