package ru.gb.money_transfer.model;

import lombok.Data;

@Data
public class Amount {
    // значение суммы перевода
    private int value;
    // валюта, в которой осуществляется перевод
    private String currency;
    public Amount(int value, String currency) {
        this.value = value;
        this.currency = currency;
    }
}
