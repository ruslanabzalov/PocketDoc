package com.ruslanabzalov.pocketdoc.database;

public class DatabaseSchema {

    public static final class MedicalRecordsTable {

        public static final String NAME = "medical_records";

        public static final class Cols {
            public static final String DOC_TYPE = "doc_type";
            public static final String USER_NAME = "user_name";
            public static final String RECORD_DATE = "record_date";
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
