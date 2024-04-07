package cn.csu.mypetstore_springboot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelToSnakeConverter {

    public static String camelToSnake(String camelCase) {
        // Define a regex pattern to match camelCase
        Pattern pattern = Pattern.compile("([a-z])([A-Z]+)");
        Matcher matcher = pattern.matcher(camelCase);

        // Use matcher to replace matches with snake_case
        return matcher.replaceAll("$1_$2").toLowerCase();
    }

    public static void main(String[] args) {
        String camelCase = "camelCaseExample";
        String snakeCase = camelToSnake(camelCase);
        System.out.println(snakeCase); // Output: camel_case_example
    }
}
