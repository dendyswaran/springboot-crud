package com.deloitte.baseapp.commons;

public class LogInfo {

    /**
     * Please use this function when do the logging
     *
     * @param clazz
     * @param method
     * @param message
     * @return
     */
    public static String print(final String clazz, final String method, final String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(clazz).append(".").append(method).append("]")
                .append(" ")
                .append(message);

        return sb.toString();
    }
}
