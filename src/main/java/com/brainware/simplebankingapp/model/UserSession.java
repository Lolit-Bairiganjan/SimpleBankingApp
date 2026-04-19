package com.brainware.simplebankingapp.model;

public class UserSession {
    private static int customerId;
    private static String username;

    public static void setSession(int id, String name) {
        customerId = id;
        username = name;
    }

    public static int getCustomerId() { return customerId; }
    public static String getUsername() { return username; }
    public static void cleanSession() { customerId = 0; username = null; }
}