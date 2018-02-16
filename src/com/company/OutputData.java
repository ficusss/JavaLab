package com.company;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


public class OutputData {

    private byte[] data;
    private String filename;
    private FileOutputStream outputStream;

    /**
     * Constructor for output manager
     *
     * @param _data - data from config file.
     * @param dataSize - size buffer.
     * @throws NullPointerException - an exception occurs if the required key does not exist.
     * @throws IOException - an exception occurs if problem with files.
     */
    public OutputData(Map<String, String> _data, int dataSize) throws NullPointerException, IOException {
        filename = _data.get(FileName.OUTPUT.getValue());
        data = new byte[dataSize];
    }

    /**
     * Put data in buffer and try write to file.
     *
     * @param _data - data for buffering.
     * @param pos - position for data.
     * @throws IOException - an exception occurs if problem with files.
     */
    public void putData(byte[] _data, int pos) throws IOException {
        synchronized (data) {
            for (int i = 0; i < _data.length; i++) {
                data[pos%data.length + i] = _data[i];
            }
        }
    }

    /**
     * Start output manager
     *
     * @throws IOException - an exception occurs if problem with files.
     */
    public void start() throws IOException {
        outputStream = new FileOutputStream(filename);
    }

    /**
     * Write data in file
     *
     * @throws IOException - an exception occurs if problem with files.
     */
    public void writeToFile() throws IOException {
        synchronized (data) {
            System.out.println("1");
            outputStream.write(data);
            for(int i = 0; i < data.length; i++) {
                data[i] = 0;
            }
        }
    }
}
