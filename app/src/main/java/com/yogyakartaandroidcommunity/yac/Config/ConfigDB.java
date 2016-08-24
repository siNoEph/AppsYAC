package com.yogyakartaandroidcommunity.yac.Config;

/**
 * Created by Septian on 21-Aug-16.
 */
public class ConfigDB {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yac.db";

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }
}
