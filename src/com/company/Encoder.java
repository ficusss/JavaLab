package com.company;
import java.io.*;
import java.util.Map;

/**
 * Created by Grigory on 20.09.2017.
 */
public class Encoder {
    private Encoder() {}

    /**
     * Cyclic shift of bits in byte to the left.
     * @param byte_ - byte to shift.
     * @param shift - number of bits to shift.
     * @return shifted byte.
     */
    private static byte rotateLeft(byte byte_, int shift)
    {
        return (byte)(((byte_ & 0xff) << shift) | ((byte_ & 0xff) >>> (8 - shift)));
    }

    /**
     * Cyclic shift of each byte from input stream and write they in output stream.
     * @param in - input stream.
     * @param out - output stream.
     * @param confData - table with data(mode and countBits) from config file.
     * @throws IOException - an exception occurs if there are problems with the streams that are transmitted.
     * @throws InvalidConfigurationData - an exception occurs if configData does not meet expectations.
     */
    public static void ShiftByte(FileInputStream in, FileOutputStream out, Map confData)
            throws IOException, InvalidConfigurationData{
        if (!confData.get(ConfigParameter.MODE.getValue()).equals(Mode.RIGHT.getValue()) &&
                !confData.get(ConfigParameter.MODE.getValue()).equals(Mode.LEFT.getValue()))
            throw new InvalidConfigurationData("Invalid values in mode!");
        if ((int) confData.get(ConfigParameter.COUNT_BIT.getValue()) < 0)
            throw new InvalidConfigurationData("Invalid values in countBits");

        BufferedInputStream bufferIn = new BufferedInputStream(in);
        BufferedOutputStream bufferOut = new BufferedOutputStream(out);
        int shift;
        byte[] byte_ = new byte[1];
        if (confData.get(ConfigParameter.MODE.getValue()).equals(Mode.RIGHT.getValue()))
            shift = 8 - (int) confData.get(ConfigParameter.COUNT_BIT.getValue()) % 8;
        else
            shift = (int) confData.get(ConfigParameter.COUNT_BIT.getValue()) % 8;

        while(bufferIn.read(byte_) != -1) {
            byte_[0] = rotateLeft(byte_[0], shift);
            bufferOut.write(byte_);
        }
        bufferOut.flush();
    }
}
