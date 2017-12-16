package com.company;


import com.company.AllExceptions.EmptyListCommand;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;

public class Conveyor {
    private Conveyor() {}

    /**
     * Pipelined data processing.
     *
     * @param argData - data from command line.
     * @param listMethods - list of methods.
     */
    public static void start(Map argData, ArrayList<Map<String, String>> listMethods) {
        try {
            if (listMethods.isEmpty())
                throw new EmptyListCommand();
            Class prevAlgorithm, currAlgorithm;
            Object prevObj = new Object(), currObj;

            for (int i = 0; i < listMethods.size(); i++) {
                if (listMethods.get(i).get(ConfigParameter.METHOD.getValue()).equals("Begin")) {
                    prevAlgorithm = Class.forName("com.company." + "Begin");
                    Constructor constructor = prevAlgorithm.getConstructor(Map.class);
                    currObj = constructor.newInstance(argData);
                } else if (listMethods.get(i).get(ConfigParameter.METHOD.getValue()).equals("End")) {
                    currAlgorithm = Class.forName("com.company." + "End");
                    Constructor constructor = currAlgorithm.getConstructor(Handler.class, Map.class);
                    currObj = constructor.newInstance(prevObj, argData);
                } else {
                    currAlgorithm = Class.forName("com.company." +
                            listMethods.get(i).get(ConfigParameter.METHOD.getValue()));
                    Constructor constructor = currAlgorithm.getConstructor(Handler.class, String.class);
                    currObj = constructor.newInstance(prevObj,
                            listMethods.get(i).get(ConfigParameter.CONFIG_FILE.getValue()));
                }
                ((Encoder) currObj).run();
                prevObj = currObj;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: not found method " + e.getMessage());
            LogFile.WriterLogFile(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getStackTrace());
            e.printStackTrace();
            LogFile.WriterLogFile(e);
        }
    }
}
