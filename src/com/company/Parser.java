package com.company;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.*;

public class Parser {
    static public String regCommandLine;
    static public String regConfigFile;

    /**
     * Static block with initialization of regular expressions.
     */
    static {
        regCommandLine = "(\\w+)=(\\w+\\.\\w+)";
        regConfigFile = "(\\w+)=((\\W+)|(\\w+))";
    }

    private Parser() {}

    /**
     * Bonding strings from args in one string.
     * @param args - array with any string.
     * @return argStr - glued string.
     */
    public static String arrToString(String[] args) {
        String argStr = "";
        for (String str : args)
            argStr += str + " ";
        return argStr;
    }

    /**
     * Get parsed string as like table.
     * @param string - string to parsing.
     * @param regStr - regular expression.
     * @return hashMap - table with parsed string.
     * @throws RepetitionOfArgument - an exception occurs if some arguments in string are repeated.
     */
    public static Map<String, String> getMap(String string, String regStr) throws RepetitionOfArgument {
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
