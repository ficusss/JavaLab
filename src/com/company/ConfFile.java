package com.company;

import com.company.AllExceptions.InvalidConfigurationData;
import com.company.AllExceptions.RepetitionOfArgument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.util.Scanner;

public class ConfFile {
    private ConfFile() {}

    /**
     * Read config file and write data in Map.
     * @param configFile - config file with data.
     * @return configData - read data.
     * @throws IOException - an exception occurs if the transferred file does not exist.
     * @throws RepetitionOfArgument - an exception occurs if config file include repetitive arguments.
     * @throws InvalidConfigurationData - an exception occurs if read data does not meet expectations.
     * @throws NumberFormatException - an exception occurs is thrown if the read field is not a number.
     */
    public static ArrayList<Map<String, String>> readToList(File configFile, String regStr)
            throws IOException, RepetitionOfArgument {
        Scanner scanner = new Scanner(configFile);
        StringBuffer argFromConfFile = new StringBuffer();
        while (scanner.hasNext()) {
            argFromConfFile.append(scanner.nextLine());
            argFromConfFile.append(" ");
        }
        return Parser.getList(argFromConfFile, regStr);
    }

    /**
     * Read config file and write data in Map.
     * @param configFile - config file with data.
     * @return configData - read data.
     * @throws IOException - an exception occurs if the transferred file does not exist.
     * @throws RepetitionOfArgument - an exception occurs if config file include repetitive arguments.
     * @throws InvalidConfigurationData - an exception occurs if read data does not meet expectations.
     * @throws NumberFormatException - an exception occurs is thrown if the read field is not a number.
     */
    public static Map<String, String> readToMap(File configFile, String regStr)
            throws IOException, RepetitionOfArgument {
        Scanner scanner = new Scanner(configFile);
        StringBuffer argFromConfFile = new StringBuffer();
        while (scanner.hasNext()) {
            argFromConfFile.append(scanner.nextLine());
            argFromConfFile.append(" ");
        }
        return Parser.getMap(argFromConfFile, regStr);
    }
}
