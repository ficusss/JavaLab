package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParserOfMethods {
    private ParserOfMethods() {}

    /**
     * Get list with sequence of coding methods.
     * @param data - data from config file.
     * @return list filled with a sequence of coding methods.
     */
    public static List<Encoder> getList(Map data) throws InvalidConfigurationData {
        List<Encoder> encoderList = new LinkedList<>();
        String currMethod, currKey = ConfigParameter.METHOD.getValue() + Integer.toString(1);
        for (int i = 1; data.containsKey(currKey);
             ++i, currKey = ConfigParameter.METHOD.getValue() + Integer.toString(i)) {
            currMethod = (String) data.get(currKey);
            // хорошо было бы сделать через switch - case
            // но в case требуются constant expression
            // a Enum ими не являются.
            if (currMethod.equals(Method.SHIFT_BYTE.getValue())) {
                encoderList.add(new ShiftByte());
            } else {
                throw new InvalidConfigurationData("Unrecognized method");
            }
        }
        return encoderList;
    }
}
