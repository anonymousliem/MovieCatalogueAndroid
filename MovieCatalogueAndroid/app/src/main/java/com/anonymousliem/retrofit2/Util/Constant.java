package com.anonymousliem.retrofit2.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constant {

    public static String KEY = "33ab4ce4afc00a2be87a4b9101ccf924";
    public static String BASE_URL = "http://api.themoviedb.org/3/";
    public static String BACKDROP_PATH = "https://image.tmdb.org/t/p/w500_and_h282_face";
    public static String DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
}