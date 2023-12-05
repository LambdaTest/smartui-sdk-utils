package com.github.lambdatest;

public class Utils {
    public static String getSmartUIServerAddress() {
        return System.getenv().getOrDefault("SMARTUI_SERVER_ADDRESS", "http://localhost:8080");
    }
}
