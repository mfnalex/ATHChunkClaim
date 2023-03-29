package me.gurwi.athchunkclaim.utils;

public class FloatCheck {

    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
