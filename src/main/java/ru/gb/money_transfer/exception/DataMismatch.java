package ru.gb.money_transfer.exception;

public class DataMismatch extends RuntimeException {

    public DataMismatch(String msg) {
        super(msg);
    }
}