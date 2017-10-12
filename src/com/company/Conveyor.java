package com.company;

import java.io.*;
import java.util.Collection;
import java.util.Map;

public class Conveyor {
    private Conveyor() {}

    /**
     * Encode the input data (input stream) with a sequence of methods.
     * @param in - input stream.
     * @param out - output stream.
     * @param collectionEncode - sequence of coding methods.
     * @param confData - table with supporting data from config file.
     * @throws IOException - an exception occurs if there are problems with the streams that are transmitted.
     * @throws InvalidConfigurationData - an exception occurs if configData does not meet expectations.
     */
    public static void start(FileInputStream in, FileOutputStream out, Collection<Encoder> encoderCollection,
                             Map configData) throws IOException, InvalidConfigurationData {
        BufferedInputStream bufferIn = new BufferedInputStream(in);
        BufferedOutputStream bufferOut = new BufferedOutputStream(out);
        byte[] byte_ = new byte[1];

        while (bufferIn.read(byte_) != -1) {
            for (Encoder currEncoder : encoderCollection) {
                byte_[0] = currEncoder.encode(byte_[0], configData);
            }
            bufferOut.write(byte_);
        }
        bufferOut.flush();
    }
}
