package com.company;


import com.company.AllExceptions.EmptyReturn;
import com.company.AllExceptions.InvalidConfigurationData;
import com.company.AllExceptions.RepetitionOfArgument;

import java.io.*;
import java.util.Map;

import static java.lang.Thread.sleep;

public class ShiftByte implements Runnable {

    private static int TIME_OUT;

    static {
        TIME_OUT = 5;
    }

    private String thName;
    private InputData inManager;
    private OutputData outManager;
    private int countSteps;
    private int start, step, pack;
    private Map<String, String> configData;

    /**
     * ShiftByte-constructor: Preprocessing data for method.
     *
     * @param configFile - name configuration file.
     * @param InManager - link on the input manager.
     * @param OutManager - link on the output manager.
     * @throws IOException              - an exception occurs if problem with files.
     * @throws RepetitionOfArgument     - an exception is called if arguments are repeated.
     * @throws InvalidConfigurationData - an exception occurs if any data does not meet expectations.
     * @throws EmptyReturn              - an exception occurs if previous algorithm don't have returns data.
     * @throws NullPointerException     - an exception occurs if the required key does not exist.
     */
    public ShiftByte(String configFile, Object InManager, Object OutManager, String _thName) throws IOException,
            RepetitionOfArgument, InvalidConfigurationData, EmptyReturn, NullPointerException {
        File conf = new File(configFile);
        configData = ConfFile.readToMap(conf, Parser.REG_CONFIG_FILE);
        dataChecking(configData);
        inManager = (InputData) InManager;
        outManager = (OutputData) OutManager;
        countSteps = 0;
        thName = _thName;
        start = Integer.parseInt(configData.get(ShiftByteConfig.START.getValue()));
        step = Integer.parseInt(configData.get(ShiftByteConfig.STEP.getValue()));
        pack = Integer.parseInt(configData.get(ShiftByteConfig.PACK.getValue()));
    }


    /**
     * Cyclic shift of each byte from input stream and write they in output stream.
     */
    @Override
    public void run() {
        try {
            while (!inManager.itIsAll()) {
                int currStart = start + step * countSteps;
                byte[] data = inManager.getData(currStart, pack);
                if (data == null) {
                    sleep(TIME_OUT);
                    continue;
                }
                int shift;
                if (configData.get(ShiftByteConfig.MODE.getValue()).equals(Mode.RIGHT.getValue()))
                    shift = 8 - Integer.parseInt(configData.get(ShiftByteConfig.COUNT_BITS.getValue())) % 8;
                else
                    shift = Integer.parseInt(configData.get(ShiftByteConfig.COUNT_BITS.getValue())) % 8;
                for (int i = 0; i < data.length; i++) {
                    data[i] = rotateLeft(data[i], shift);
                }
                outManager.putData(data, currStart);
                countSteps++;
            }
        } catch (InterruptedException e) {
            return;
        } catch (Exception e) {
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
        if (Integer.parseInt(data.get(ShiftByteConfig.START.getValue())) < 0)
            throw new InvalidConfigurationData("Invalid values in start");
        if (Integer.parseInt(data.get(ShiftByteConfig.PACK.getValue())) < 0)
            throw new InvalidConfigurationData("Invalid values in pack");
        if (Integer.parseInt(data.get(ShiftByteConfig.STEP.getValue())) < 0)
            throw new InvalidConfigurationData("Invalid values in step");
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
}
