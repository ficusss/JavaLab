package com.company;

import com.company.AllExceptions.RepetitionOfArgument;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    /**
     * The main method. Calls the script of the program.
     * @param args command-line arguments(type: String)
     */
    public static void main(String[] args) {
        try {
            Map<String, String> mapArg = Parser.getMap(Parser.arrToString(args), Parser.REG_COMMAND_LINE);
            File configFile = new File(mapArg.get(FileName.CONFIG.getValue()));
            ArrayList<Map<String, String>> listOfMethod = ConfFile.readToList(configFile, Parser.REG_MAIN_CONFIG_FILE);
            Conveyor.start(mapArg, listOfMethod);
        } catch (RepetitionOfArgument |IOException e ) {
            System.out.println(e.getMessage());
            LogFile.WriterLogFile(e);
        } catch (NullPointerException e) {
            System.out.println("Invalid command line data");
            LogFile.WriterLogFile(e);
        }
    }
}
