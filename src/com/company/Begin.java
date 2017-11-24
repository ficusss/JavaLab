package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Initial blank for the conveyor.
 */
public class Begin implements Encoder, Handler {

    private byte[] outputData;

    /**
     * Begin-constructor: Preprocessing data for method
     *
     * @param data - data for work of method.
     */
    public Begin(Map<String, String> data) throws NullPointerException, IOException{
        String filename = data.get(FileName.INPUT.getValue());
        outputData = Files.readAllBytes(Paths.get(filename));
    }

    /**
     * Determines whether a data method can potentially return something
     *
     * @return true or false
     */
    @Override
    public boolean isReturn() {
        return true;
    }

    @Override
    public void run() {}

    /**
     * Gives processed data.
     *
     * @return - processed data.
     */
    @Override
    public byte[] getOutputData() {
        return outputData;
    }

}
