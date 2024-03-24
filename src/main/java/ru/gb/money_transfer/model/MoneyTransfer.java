package ru.gb.money_transfer.model;

import lombok.Data;

@Data
public class MoneyTransfer {
    // номер карты с которой осуществляется перевод
    private String cardFromNumber;
    // число до которого действует карта,с которой осуществяется перевод
    private String cardFromValidTill;
    // CVV - номер карты,с которой осуществяется перевод
    private String cardFromCVV;
    // номер карты, на которую осуществяется перевод
    private String cardToNumber;
    // сумма и валюта перевода
    private Amount amount;

    public MoneyTransfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, int value, String currency) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        amount = new Amount(value, currency);
    }

}
