package com.company;

/**
 * Enum with keys for file name.
 */
enum FileName {
    INPUT("input"),
    OUTPUT("output"),
    CONFIG("config");

    private final String string;
    FileName(String str) {
        this.string = str;
    }

    public String getValue() {
        return string;
    }
}

/**
 * Enum with keys for parameter from config file of shift method.
 */
enum ShiftByteConfig {
    MODE("mode"),
    COUNT_BITS("count_bits"),
    START("start"),
    STEP("step"),
    PACK("pack");

    private final String string;

    ShiftByteConfig(String str) {
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
    METHOD("method"),
    CONFIG_FILE("config_file");

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
    RIGHT(">> "),
    LEFT("<< ");

    private final String string;

    Mode(String str) {
        this.string = str;
    }

    public String getValue() {
        return string;
    }
}