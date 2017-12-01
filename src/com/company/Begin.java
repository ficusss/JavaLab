package com.company;


import com.sun.java.util.jar.pack.Package;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Initial blank for the conveyor.
 */
public class Begin implements Encoder, Handler {

    private byte[] outputData;
    static private Map<String, InnerConverter> outputTypes;

    static {
        outputTypes = new HashMap<>();
        outputTypes.put(OutputTypes.STRING.getValue(), OutArrayByte.class);
        outputTypes.put(OutputTypes.ARR_BYTE.getValue(), OutString.class);
    }

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
    public Class getOutputData(Class cls) {
        return cls;
    }

    /**
     * Returns a list of data types that the class can return
     *
     * @return types of data that a class can return
     */
    @Override
    public ArrayList<String> getOutputTypes() {
        return outputTypes;
    }

    /**
     * Inner class for convert output data to array of bytes
     */
    private class OutArrayByte implements InnerConverter {
        @Override
        public Object get() {
            return outputData;
        }
    }

    /**
     * Inner class for convert output data to String
     */
    private class OutString implements InnerConverter {
        @Override
        public Object get() {
            return new String(outputData);
        }
    }
}
