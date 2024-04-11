package cn.csu.mypetstore_springboot.utils;

public class BasicTypeHelper {
    public static boolean isUnsignedNumber(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
