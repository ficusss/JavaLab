package com.company;


import java.lang.reflect.Constructor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Initial blank for the conveyor.
 */
public class Begin implements Encoder, Handler {

    private byte[] outputData;
    static private Map<String, Class> outputTypes;

    static {
        outputTypes = new HashMap<>();
        outputTypes.put(OutputTypes.STRING.getValue(), OutString.class);
        outputTypes.put(OutputTypes.ARR_BYTE.getValue(), OutArrayByte.class);
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

    /**
     *
     * @param c
     * @return
     * @throws Exception - all exception for reflect
     */
    @Override
    public Object getOutput(Class c) throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException, ClassNotFoundException {
        Class c1 = Class.forName(c.getName());
        Constructor constructor = c1.getDeclaredConstructor(Begin.class);
        return constructor.newInstance(this);
    }

    @Override
    public void run() {}

    /**
     * Returns a list of data types that the class can return
     *
     * @return types of data that a class can return
     */
    @Override
    public Map<String, Class> getReturnedTypes() {
        return outputTypes;
    }

    /**
     * Inner class for convert output data to array of bytes
     */
    public class OutArrayByte implements InnerConverter {
        /**
         * Constructor of class
         */
        public OutArrayByte() {}

        /**
         * Returns a data in format like array byte
         *
         * @return data
         */
        @Override
        public Object getData() {
            return outputData;
        }
    }

    /**
     * Inner class for convert output data to String
     */
    public class OutString implements InnerConverter {
        /**
         * Constructor of class
         */
        public OutString() {}

        /**
         * Returns a data in format like string
         *
         * @return data
         */
        @Override
        public Object getData() {
            return new String(outputData);
        }
    }
}
