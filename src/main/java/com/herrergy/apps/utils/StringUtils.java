package com.herrergy.apps.utils;

public class StringUtils {

    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }

    public static String capitalize(String value) {
        if (isEmpty(value)) {
            return value;
        }

        return String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1);
    }

    public static String repeat(String value, int count) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < count; i++) {
            builder.append(value);
        }

        return builder.toString();
    }
}
