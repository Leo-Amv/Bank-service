package ru.gb.money_transfer.model;

import lombok.Data;

@Data
public class Card {
    private final String cardNumber;
    private final String cardValid;
    private final String cardCVV;
    private final int account;
    private final String currency;

    public Card(String cardNumber, String cardValid, String cardCVV, int account, String currency) {
        this.cardNumber = cardNumber;
        this.cardValid = cardValid;
        this.cardCVV = cardCVV;
        this.account = account;
        this.currency = currency;
    }

}
