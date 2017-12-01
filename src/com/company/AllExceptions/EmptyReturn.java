package com.company.AllExceptions;

/**
 *  An exception is called if method nothing return
 */

public class EmptyReturn extends Exception {
    public EmptyReturn() {
        super("Error: algorithm does not have a return value");
        System.out.println("Error: algorithm does not have a return value");
    }
}

