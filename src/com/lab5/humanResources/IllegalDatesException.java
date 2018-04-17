package com.lab5.humanResources;

import java.util.List;

public class IllegalDatesException extends Exception {
    IllegalDatesException(){
        super();
    }
    IllegalDatesException(String message){
        super(message);
    }
    public IllegalDatesException(String message, Throwable cause) {
        super(message, cause);
    }
}
