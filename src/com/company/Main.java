package com.company;
import java.io.*;
import java.util.Map;

public class Main {
    /**
     * The main method. Calls the script of the program.
     * @param args command-line arguments(type: String)
     */
    public static void main(String[] args) {
        try {
            Map hashMapArg = Parser.getMap(Parser.arrToString(args), Parser.REG_COMMAND_LINE);

            FileInputStream input = new FileInputStream((String) hashMapArg.get(FileName.INPUT.getValue()));
            FileOutputStream output = new FileOutputStream((String) hashMapArg.get(FileName.OUTPUT.getValue()));
            File configFile = new File((String) hashMapArg.get(FileName.CONFIG.getValue()));

            Map configData = ConfFile.read(configFile);
            Encoder.ShiftByte(input, output, configData);
        } catch (InvalidConfigurationData|RepetitionOfArgument|IOException|NumberFormatException e ) {
            System.out.println(e.getMessage());
            LogFile.WriterLogFile(e);
        } catch (NullPointerException e) {
            System.out.println("Invalid command line data");
            LogFile.WriterLogFile(e);
        }
    }
}
