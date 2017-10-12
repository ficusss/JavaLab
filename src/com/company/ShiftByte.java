package com.company;

import java.io.*;
import java.util.Map;

public class ShiftByte implements Encoder {
    public ShiftByte() {}

    /**
     * Data checking.
     * @param data - data.
     * @throws InvalidConfigurationData - an exception occurs if configData does not meet expectations.
     */
    private void dataChecking(Map data) throws InvalidConfigurationData {
        if (!data.containsKey(ConfigParameter.MODE.getValue()) ||
                !data.containsKey(ConfigParameter.COUNT_BIT.getValue()))
            throw new InvalidConfigurationData("Use mode and countBits!");
        if (!data.get(ConfigParameter.MODE.getValue()).equals(Mode.RIGHT.getValue()) &&
                !data.get(ConfigParameter.MODE.getValue()).equals(Mode.LEFT.getValue()))
            throw new InvalidConfigurationData("Invalid values in mode!");
        if ((int) data.get(ConfigParameter.COUNT_BIT.getValue()) < 0)
            throw new InvalidConfigurationData("Invalid values in countBits");
    }

    /**
     * Cyclic shift of bits in byte to the left.
     * @param byte_ - byte to shift.
     * @param shift - number of bits to shift.
     * @return shifted byte.
     */
    private byte rotateLeft(byte byte_, int shift)
    {
        return (byte)(((byte_ & 0xff) << shift) | ((byte_ & 0xff) >>> (8 - shift)));
    }

    /**
     * Cyclic shift of byte.
     * @param byte_ - byte to shift.
     * @param configData - table with data(required: mode and countBits) from config file.
     * @return shifted byte.
     * @throws InvalidConfigurationData - an exception occurs if configData does not meet expectations.
     */
    public byte encode(byte byte_, Map configData) throws InvalidConfigurationData {
        dataChecking(configData);
        int shift = (int) configData.get(ConfigParameter.COUNT_BIT.getValue());
        if (configData.get(ConfigParameter.MODE.getValue()).equals(Mode.RIGHT.getValue()))
            shift = 8 - shift % 8;
        else
            shift = shift % 8;
        return rotateLeft(byte_, shift);
    }

    /**
     * Cyclic shift of each byte from input stream and write they in output stream.
     * @param in - input stream.
     * @param out - output stream.
     * @param confData - table with data(required: mode and countBits) from config file.
     * @throws IOException - an exception occurs if there are problems with the streams that are transmitted.
     * @throws InvalidConfigurationData - an exception occurs if configData does not meet expectations.
     */
    public void encodeAllStream(FileInputStream in, FileOutputStream out, Map configData)
            throws IOException, InvalidConfigurationData{
        dataChecking(configData);
        BufferedInputStream bufferIn = new BufferedInputStream(in);
        BufferedOutputStream bufferOut = new BufferedOutputStream(out);
        int shift;
        byte[] byte_ = new byte[1];
        if (configData.get(ConfigParameter.MODE.getValue()).equals(Mode.RIGHT.getValue()))
            shift = 8 - (int) configData.get(ConfigParameter.COUNT_BIT.getValue()) % 8;
        else
            shift = (int) configData.get(ConfigParameter.COUNT_BIT.getValue()) % 8;

        while(bufferIn.read(byte_) != -1) {
            byte_[0] = rotateLeft(byte_[0], shift);
            bufferOut.write(byte_);
        }
        bufferOut.flush();
    }
}
