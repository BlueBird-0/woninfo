package com.bluebird.inhak.woninfo;

import android.provider.BaseColumns;

/**
 * Created by InHak on 2018-01-28.
 */

public class DBStruct {
    public static final class CreateDB implements BaseColumns {
        public static final String PRIMARY_KEY = "primary_key";
        public static final String TITLE = "title";
        public static final String LIKES = "likes";
        public static final String CONTENT = "content";

        public static final String _TABLENAME = "info";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                +PRIMARY_KEY+" text not null , "                //primary 선언해야함
                +TITLE+" text not null , "
                +LIKES+" text not null , "
                +CONTENT+" text not null );";
    }
}
