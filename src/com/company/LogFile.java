package com.company;

import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
    /**
     * Writes message in LogFile.
     * @param message - any message.
     */
    public static void  WriterLogFile (Exception message){
        try(FileWriter logFile = new FileWriter("LogFile.txt")) {
            logFile.write(message.getMessage());
        }
        catch (IOException | NullPointerException e){
            System.out.println("Error: cannot write to logFile, because: error=" + e.getMessage());
        }
    }
}
