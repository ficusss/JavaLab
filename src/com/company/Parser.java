package com.company;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.*;

/**
 * Created by Grigory on 29.09.2017.
 */
public class Parser {
    static public String regCommandLine;
    static public String regConfigFile;

    static {
        regCommandLine = "(\\w+)=(\\w+\\.\\w+)";
        regConfigFile = "(\\w+)=((\\W+)|(\\w+))";
    }

    private Parser() {}

    public static String arrToString(String[] args) {
        String argStr = "";
        for (String str : args)
            argStr += str + " ";
        return argStr;
    }

    public static Map<String, String> getMap(String argString, String regStr) throws RepetitionOfArgument {
        Map<String, String> hashMap = new HashMap<String, String>();
        Pattern p = Pattern.compile(regStr);
        Matcher m = p.matcher(argString);
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
