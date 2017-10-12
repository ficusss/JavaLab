package com.company;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.*;

public class Parser {
    static public String REG_COMMAND_LINE;
    static public String REG_CONFIG_FILE;

    /**
     * Static block with initialization of regular expressions.
     */
    static {
        REG_COMMAND_LINE = "(\\w+)=(\\w+\\.\\w+)";
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
     * Get parsed string as like table.
     * @param string - string to parsing.
     * @param regStr - regular expression.
     * @return hashMap - table with parsed string.
     * @throws RepetitionOfArgument - an exception occurs if some arguments in string are repeated.
     */
    public static Map<String, String> getMap(StringBuffer string, String regStr) throws RepetitionOfArgument {
        Map<String, String> hashMap = new HashMap<String, String>();
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
