package com.company;

enum FileName {
    INPUT("input"), OUTPUT("output"), CONFIG("config"), LOG("LogFile.txt");
    private final String string;
    FileName(String str) {
        this.string = str;
    }

    public String getValue() {
        return string;
    }
}

enum ConfigParameter {
    MODE("mode"), COUNT_BIT("countBits");
    private final String string;

    ConfigParameter(String str) {
        this.string = str;
    }

    public String getValue() {
        return string;
    }
}

enum Mode {
    RIGHT(">>"), LEFT("<<");
    private final String string;

    Mode(String str) {
        this.string = str;
    }

    public String getValue() {
        return string;
    }
}