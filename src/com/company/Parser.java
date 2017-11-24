package com.company;


import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.*;

public class Parser {
    static public String REG_COMMAND_LINE;
    static public String REG_CONFIG_FILE;
    static public String REG_MAIN_CONFIG_FILE;


    /**
     * Static block with initialization of regular expressions.
     */
    static {
        REG_COMMAND_LINE = "(\\w+)=(\\w+\\.\\w+)";
        REG_MAIN_CONFIG_FILE = "(^#\\w+)=(\\w+\\.\\w+)";
        REG_CONFIG_FILE = "(\\w+)=((\\W+)|(\\w+))";
    }

    private Parser() {}

    /**
     * Bonding strings from args in one string.
     * @param args - array with any string.
     * @return argStr - glued string.
     */
    public static StringBuffer arrToString(String[] args) {
        StringBuffer argStr = new StringBuffer();
        for (String str : args) {
            argStr.append(str);
            argStr.append(" ");
        }
        return argStr;
    }

    /**
     * Get parsed string as like list.
     * @param string - string to parsing.
     * @param regStr - regular expression.
     * @return list - list with parsed string.
     */
    public static ArrayList<Map<String, String>> getList(StringBuffer string, String regStr) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        Pattern p = Pattern.compile(regStr);
        Matcher m = p.matcher(string);
        Map<String, String> begin = new HashMap<>();
        begin.put(ConfigParameter.METHOD.getValue(), "Begin");
        list.add(begin);
        while (m.find()) {
            Map<String, String> tmp = new HashMap<>();
            tmp.put(ConfigParameter.METHOD.getValue(), m.group(1));
            tmp.put(ConfigParameter.CONFIG_FILE.getValue(), m.group(2));
            list.add(tmp);
        }
        Map<String, String> end = new HashMap<>();
        end.put(ConfigParameter.METHOD.getValue(), "End");
        list.add(end);
        return list;
    }

    /**
     * Get parsed string as like table.
     * @param string - string to parsing.
     * @param regStr - regular expression.
     * @return hashMap - table with parsed string.
     * @throws RepetitionOfArgument - an exception occurs if some arguments in string are repeated.
     */
    public static Map<String, String> getMap(StringBuffer string, String regStr) throws RepetitionOfArgument {
        Map<String, String> hashMap = new HashMap<>();
        Pattern p = Pattern.compile(regStr);
        Matcher m = p.matcher(string);
        String key;
        while (m.find()) {
            key = m.group(1);
                if (!hashMap.containsKey(key)) {
                    hashMap.put(key, m.group(2));
                } else {
                    throw new RepetitionOfArgument(key);
                }
        }
        return hashMap;
    }
}
