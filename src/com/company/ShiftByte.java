package com.company;


import com.company.AllExceptions.EmptyReturn;
import com.company.AllExceptions.InvalidConfigurationData;
import com.company.AllExceptions.RepetitionOfArgument;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShiftByte implements Encoder, Handler {

    private byte[] inputData;
    private Map<String, String> configData;
    private byte[] outputData;
    static private ArrayList<String> inputTypes;
    static private Map<String, InnerConverter> outputTypes;

    static {
        inputTypes = new ArrayList<>();
        inputTypes.add(OutputTypes.ARR_BYTE.getValue());
        outputTypes = new HashMap<>();
        outputTypes.put(OutputTypes.ARR_BYTE.getValue(), OutArrayByte.class);
        outputTypes.put(OutputTypes.STRING.getValue(), OutString.class);
    }



    /**
     * ShiftByte-constructor: Preprocessing data for method.
     *
     * @param prevAlgorithm - link on previous algorithm.
     * @param configFile - name configuration file.
     * @throws IOException - an exception occurs if problem with files.
     * @throws RepetitionOfArgument - an exception is called if arguments are repeated.
     * @throws InvalidConfigurationData - an exception occurs if any data does not meet expectations.
     * @throws EmptyReturn - an exception occurs if previous algorithm don't have returns data.
     * @throws NullPointerException - an exception occurs if the required key does not exist.
     */
    public ShiftByte(Handler prevAlgorithm, String configFile) throws IOException,
            RepetitionOfArgument, InvalidConfigurationData, EmptyReturn, NullPointerException {
        if (!prevAlgorithm.isReturn())
            throw new EmptyReturn();
        Map<String, InnerConverter> map = prevAlgorithm.getReturnedTypes();
        for (String in : inputTypes) {
            if (map.containsKey(in)) {
                InnerConverter inputType = map.get(in);
                inputData = (byte[]) inputType.get();
                break;
            }
        }
        outputData = inputData.clone();
        File conf = new File(configFile);
        configData = ConfFile.readToMap(conf, Parser.REG_CONFIG_FILE);
        dataChecking(configData);
    }

    /**
     * Determines whether a data method can potentially return something.
     *
     * @return true or false.
     */
    public boolean isReturn() {
        return true;
    }

    /**
     * Gives processed data.
     *
     * @return - processed data.
     */
    public byte[] getOutputData() {
        return outputData;
    }

    /**
     * Cyclic shift of each byte from input stream and write they in output stream.
     */
    public void run() {
        try {
            int shift;
            if (configData.get(ShiftByteConfig.MODE.getValue()).equals(Mode.RIGHT.getValue()))
                shift = 8 - Integer.parseInt(configData.get(ShiftByteConfig.COUNT_BITS.getValue())) % 8;
            else
                shift = Integer.parseInt(configData.get(ShiftByteConfig.COUNT_BITS.getValue())) % 8;
            for(int i = 0; i < inputData.length; i++) {
                outputData[i] = rotateLeft(inputData[i], shift);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            LogFile.WriterLogFile(e);
        }
    }

    /**
     * Data checking.
     *
     * @param data - data.
     * @throws InvalidConfigurationData - an exception occurs if configData does not meet expectations.
     */
    private void dataChecking(Map<String, String> data) throws InvalidConfigurationData {
        if (!data.containsKey(ShiftByteConfig.MODE.getValue()) ||
                !data.containsKey(ShiftByteConfig.COUNT_BITS.getValue()))
            throw new InvalidConfigurationData("Use mode and countBits!");
        if (!data.get(ShiftByteConfig.MODE.getValue()).equals(Mode.RIGHT.getValue()) &&
                !data.get(ShiftByteConfig.MODE.getValue()).equals(Mode.LEFT.getValue()))
            throw new InvalidConfigurationData("Invalid values in mode!");
        if (Integer.parseInt(data.get(ShiftByteConfig.COUNT_BITS.getValue())) < 0)
            throw new InvalidConfigurationData("Invalid values in countBits");
    }

    /**
     * Cyclic shift of bits in byte to the left.
     *
     * @param byte_ - byte to shift.
     * @param shift - number of bits to shift.
     * @return shifted byte.
     */
    private byte rotateLeft(byte byte_, int shift) {
        return (byte) (((byte_ & 0xff) << shift) | ((byte_ & 0xff) >>> (8 - shift)));
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
