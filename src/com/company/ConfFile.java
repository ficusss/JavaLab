package com.company;

import java.io.IOException;
import java.util.Map;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Grigory on 05.10.2017.
 */
public class ConfFile {
    private ConfFile() {}

    public static Map read(File confFile) throws IOException, RepetitionOfArgument,
            InvalidConfigurationData, NumberFormatException {
        Scanner scanner = new Scanner(confFile);
        String argFromConfFile = "";
        while (scanner.hasNext())
            argFromConfFile += scanner.nextLine() + " ";
        Map confData = Parser.getMap(argFromConfFile, Parser.regConfigFile);
        if (!confData.containsKey(ConfigParameter.MODE.getValue()) ||
                !confData.containsKey(ConfigParameter.COUNT_BIT.getValue())) {
            throw new InvalidConfigurationData("Use mode and countBits!");
        }
        confData.put(ConfigParameter.COUNT_BIT.getValue(),
                Integer.parseInt((String) confData.get(ConfigParameter.COUNT_BIT.getValue())));
        if (!confData.get(ConfigParameter.MODE.getValue()).equals(Mode.RIGHT.getValue()) &&
                !confData.get(ConfigParameter.MODE.getValue()).equals(Mode.LEFT.getValue())) {
            throw new InvalidConfigurationData("Invalid values in mode!");
        }
        if ((int) confData.get(ConfigParameter.COUNT_BIT.getValue()) < 0) {
            throw new InvalidConfigurationData("Invalid values in countBits");
        }
        return confData;
    }
}
