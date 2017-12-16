package com.company;


import com.company.AllExceptions.EmptyReturn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

/**
 * End blank for the conveyor.
 */
public class End implements Encoder, Handler {

    private final String nameOutputFile;
    private byte[] inputData;
    static private ArrayList<String> inputTypes;

    static {
        inputTypes = new ArrayList<>();
        inputTypes.add(OutputTypes.ARR_BYTE.getValue());
    }

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
     * Returns a list of data types that the class can return
     *
     * @return types of data that a class can return
     */
    @Override
    public Map<String, Class> getReturnedTypes() {
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

    @Override
    public Object getOutput(Class c) throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException, ClassNotFoundException {
        return null;
    }

    /**
     * End-constructor: Preprocessing data for method.
     *
     * @param prevAlgorithm - link on previous algorithm.
     * @param argMap        - arguments command line.
     * @throws NullPointerException - an exception occurs if the required key does not exist.
     * @throws EmptyReturn          - an exception occurs if previous algorithm don't have returns data.
     */
    public End(Handler prevAlgorithm, Map<String, String> argMap) throws NullPointerException, EmptyReturn,
            NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException {
        if (!prevAlgorithm.isReturn())
            throw new EmptyReturn();
        nameOutputFile = argMap.get(FileName.OUTPUT.getValue());
        Map<String, Class> prevDataTypes = prevAlgorithm.getReturnedTypes();
        for (String in : inputTypes) {
            if (prevDataTypes.containsKey(in)) {
                Class tmp = prevDataTypes.get(in);
                InnerConverter converter = (InnerConverter) prevAlgorithm.getOutput(tmp);
                inputData = (byte[]) converter.getData();
                break;
            }
        }
    }
}