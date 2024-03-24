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

}
