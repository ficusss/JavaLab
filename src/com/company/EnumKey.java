package com.company;

/**
 * Enum with keys for file name.
 */
enum FileName {
    INPUT("input"), OUTPUT("output"), CONFIG("config");
    private final String string;
    FileName(String str) {
        this.string = str;
    }

    public String getValue() {
        return string;
    }
}

/**
 * Enum with keys for parameter from config file.
 */
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

/**
 * Enum with keys for possible modes.
 */
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