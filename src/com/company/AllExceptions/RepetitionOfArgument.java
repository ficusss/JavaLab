package com.company.AllExceptions;

/**
 * An exception is called if arguments are repeated
 */
public class RepetitionOfArgument extends Exception {
    public RepetitionOfArgument(String nameFile){
        super("Error: this argument repetition " + nameFile);
    }
}
