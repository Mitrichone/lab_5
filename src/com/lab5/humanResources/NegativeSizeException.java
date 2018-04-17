package com.lab5.humanResources;

public class NegativeSizeException extends NegativeArraySizeException {
    NegativeSizeException(){
        super();
    }
    NegativeSizeException(String message){
        super(message);
    }
    public String toString() {
        return this.getClass().getName()
                + ":\nПопытка передать в конструктор отрицательное значение размера массива";
    }
}
