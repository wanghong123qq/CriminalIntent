package com.example.wanghong.database;

/**
 * Created by wanghong on 2016/6/2.
 */
public class CrimeDBschema {
    public static final class CrimeTable {
        public static final String TABLE_NAME = "crimes";

        public static final class col {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";


        }


    }
}
