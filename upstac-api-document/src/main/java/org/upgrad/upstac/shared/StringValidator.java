package org.upgrad.upstac.shared;

public class StringValidator {

   public static boolean isNotEmptyOrNull(String str) {
        if (null != str && !str.isEmpty())
            return true;
        return false;
    }

    public static boolean isNotEmptyOrNull(Object str) {
        if (null != str)
            return true;
        return false;
    }
    public static boolean isNotEmptyOrNull(Integer str) {
        if (null != str && str !=0 )
            return true;
        return false;
    }



}
