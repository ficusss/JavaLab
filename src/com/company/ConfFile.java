package com.company;

import java.io.IOException;
import java.util.Map;
import java.io.File;
import java.util.Scanner;

public class ConfFile {
    private ConfFile() {}

    /**
     * Read config file and write data in Map.
     * @param confFile - config file with data.
     * @return confData - read data.
     * @throws IOException - an exception occurs if the transferred file does not exist.
     * @throws RepetitionOfArgument - an exception occurs if config file include repetitive arguments.
     * @throws InvalidConfigurationData - an exception occurs if read data does not meet expectations.
     * @throws NumberFormatException - an exception occurs is thrown if the read field is not a number.
     */
    public static Map read(File confFile) throws IOException, RepetitionOfArgument,
            InvalidConfigurationData, NumberFormatException {
        Scanner scanner = new Scanner(confFile);
        StringBuffer argFromConfFile = new StringBuffer();
        while (scanner.hasNext()) {
            argFromConfFile.append(scanner.nextLine());
            argFromConfFile.append(" ");
        }
        Map confData = Parser.getMap(argFromConfFile, Parser.REG_CONFIG_FILE);
        if (!confData.containsKey(ConfigParameter.MODE.getValue()) ||
                !confData.containsKey(ConfigParameter.COUNT_BIT.getValue())) {
            throw new InvalidConfigurationData("Use mode and countBits!");
        }
        confData.put(ConfigParameter.COUNT_BIT.getValue(),
                Integer.parseInt((String) confData.get(ConfigParameter.COUNT_BIT.getValue())));
        if (!(confData.get(ConfigParameter.MODE.getValue()).equals(Mode.RIGHT.getValue()) ||
                confData.get(ConfigParameter.MODE.getValue()).equals(Mode.LEFT.getValue()))) {
            throw new InvalidConfigurationData("Invalid values in mode!");
        }
        if ((int) confData.get(ConfigParameter.COUNT_BIT.getValue()) < 0) {
            throw new InvalidConfigurationData("Invalid values in countBits");
        }
        return confData;
    }
}
