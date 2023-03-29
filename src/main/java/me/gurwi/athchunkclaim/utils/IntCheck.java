package me.gurwi.athchunkclaim.utils;

public class IntCheck {

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
