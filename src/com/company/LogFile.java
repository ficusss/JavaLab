package com.company;

import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
    public static void  WriterLogFile (Exception message){
        try(FileWriter logFile = new FileWriter(FileName.LOG.getValue())) {
            logFile.write(message.getMessage());
        }
        catch (IOException | NullPointerException e){
            System.out.println("Error: cannot write to logFile, because: error=" + e.getMessage());
        }
    }
}
