package com.campingmapping.team4.spring.utils.config;

public class MyConstants {

    public final static String JWT_COOKIE_NAME = "R9SBUCVDWF";//accesstoken
    public final static String JWT_REFRESH_COOKIE_NAME = "4M5N6Q8R9S";//refreshtoken
    public final static String GOOGLE_COOKIE_NAME = "JSESSIONID";//google
    public final static String GOOGLE_BUCKETNAME = "morariphoto"; // google storage name
    // public final static Long ACCESS_TOKEN_VALIDATION_SECOND = 60L * 60 * 1 * 1000; // 1 hr
    public final static Long ACCESS_TOKEN_VALIDATION_SECOND =  5L * 1000; // 5s
    public final static Long REFRESH_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 1 * 1000; // 1 Day
    public final static Long REMEMBER_REFRESH_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 30 * 1000; // 30 Day
    public final static String LOGIN_PAGE = "/morari/login"; // 登入畫面 
    public final static String SUPER_ADMIN_NAME = "sa"; // SA帳號 
    public final static String SUPER_ADMIN_PASSWORD = "0000"; // SA密碼 
  
    
}