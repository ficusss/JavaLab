package com.company;


import com.company.AllExceptions.EmptyReturn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * End blank for the conveyor.
 */
public class End implements Encoder, Handler {

    private final String nameOutputFile;
    private byte[] inputData;

    /**
     * Determines whether a data method can potentially return something
     *
     * @return true or false
     */
    @Override
    public boolean isReturn() {
        return false;
    }

    /**
     * Gives processed data.
     *
     * @return - null.
     */
    @Override
    public byte[] getOutputData() {
        return null;
    }

    /**
     * Writes data from a temporary file to a specific.
     */
    @Override
    public void run() {
        try {
            FileOutputStream outputStream = new FileOutputStream(nameOutputFile);
            outputStream.write(inputData);
        } catch (IOException e) {
            System.out.println("Error: problem with file " + e);
            LogFile.WriterLogFile(e);
        }
    }

    /**
     * End-constructor: Preprocessing data for method.
     *
     * @param prevAlgorithm - link on previous algorithm.
     * @param argMap - arguments command line.
     * @throws NullPointerException - an exception occurs if the required key does not exist.
     * @throws EmptyReturn - an exception occurs if previous algorithm don't have returns data.
     */
    public End(Handler prevAlgorithm, Map<String, String> argMap) throws NullPointerException, EmptyReturn {
        if(!prevAlgorithm.isReturn())
            throw new EmptyReturn();
        nameOutputFile = argMap.get(FileName.OUTPUT.getValue());
        inputData = prevAlgorithm.getOutputData();
    }
}
