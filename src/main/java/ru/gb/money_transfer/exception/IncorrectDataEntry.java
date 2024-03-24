package ru.gb.money_transfer.exception;

public class IncorrectDataEntry extends RuntimeException {

    public IncorrectDataEntry(String msg) {
        super(msg);
    }
}