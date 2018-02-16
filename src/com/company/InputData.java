package com.company;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class InputData {

    private static final int sizeBuffer;
    static {
        sizeBuffer = 20;
    }

    private int n_sizeBuffer;
    private boolean endFile;
    private byte[] data;
    private int counter;
    private String filename;
    private FileInputStream inputStream;

    /**
     * Constructor for InputData
     *
     * @param _data - names of files
     * @throws IOException - an exception occurs if problem with files.
     */
    public InputData(Map<String, String> _data) throws IOException {
        filename = _data.get(FileName.INPUT.getValue());
        data = new byte[sizeBuffer];
        endFile = false;
        counter = -1;
        n_sizeBuffer = sizeBuffer;
    }

    /**
     * Returned size of buffer
     *
     * @return sizeBuffer
     */
    public int getBufferSize() {
        return sizeBuffer;
    }

    /**
     * Started manager of input data
     *
     * @throws IOException - an exception occurs if problem with files.
     */
    public void start() throws IOException {
        inputStream = new FileInputStream(filename);
        uploadData();
    }

    /**
     * Upload next pack of data
     *
     * @throws IOException - an exception occurs if problem with files.
     */
    public void uploadData() throws IOException {
        int countReadByte = inputStream.read(data);
        if (countReadByte < sizeBuffer) {
            endFile = true;
            n_sizeBuffer = countReadByte;
        }
        counter++;
    }

    /**
     * Get required data sets
     *
     * @param pos - start position
     * @param size - size required data
     * @return data from pos to pos+size
     */
    public synchronized byte[] getData(int pos, int size) {
        int startBuff = sizeBuffer * counter;
        int endBuff = startBuff + n_sizeBuffer;
        if (pos + size <= endBuff && pos >= startBuff) {
            return Arrays.copyOfRange(data, pos % n_sizeBuffer, pos % n_sizeBuffer + size);
        } else if (pos <= endBuff && pos >= startBuff && pos + size > endBuff) {
            return Arrays.copyOfRange(data, pos % n_sizeBuffer, endBuff % n_sizeBuffer);
        }
        return null;
    }

    /**
     * Whether the end of the file is reached
     *
     * @return true or false
     */
    public boolean itIsAll() {
        return endFile;
    }
}
