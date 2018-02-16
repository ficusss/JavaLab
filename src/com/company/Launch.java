package com.company;


import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;


public class Launch {

    private static int TIME_OUT;
    static {
        TIME_OUT = 100;
    }

    public Launch() {}

    public static void start(Map<String, String> commandData, ArrayList<Map<String, String>> listMethods) {
        try {
            InputData in = new InputData(commandData);
            OutputData out = new OutputData(commandData, in.getBufferSize());
            in.start();
            out.start();
            for (int i = 0; i < listMethods.size(); i++) {
                Class cls = Class.forName("com.company." +
                        listMethods.get(i).get(ConfigParameter.METHOD.getValue()));
                Constructor constructor = cls.getConstructor(String.class, Object.class, Object.class, String.class);
                Object obj = constructor.newInstance(listMethods.get(i).get(ConfigParameter.CONFIG_FILE.getValue()),
                        in, out, Integer.toString(i));
                new Thread((Runnable) obj).start();
            }
            while(!in.itIsAll()) {
                sleep(TIME_OUT);
                out.writeToFile();
                in.uploadData();
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
            LogFile.WriterLogFile(e);
        }

    }

}
