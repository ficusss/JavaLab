package com.company.AllExceptions;
/**
 * An exception is called if command list empty
 */
public class EmptyListCommand extends Exception{
    public EmptyListCommand (){
        super("Error: Not found commands. Please add commands.");
        System.out.println("Error: Not found commands. Please add commands.");
    }
}
