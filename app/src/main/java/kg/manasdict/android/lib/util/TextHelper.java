package kg.manasdict.android.lib.util;

/**
 * Created by root on 4/11/16.
 */
public class TextHelper {

    public static String formatWord(String s) {
        return s.toLowerCase().replaceAll("\\s+$", "");
    }
}
