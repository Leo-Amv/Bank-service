package ru.gb.money_transfer.dao;

import lombok.Getter;
import ru.gb.money_transfer.model.Card;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class DatabaseCards {
    private final String PATH = "src/main/resources/application.properties";
    private final Map<String, Card> listCards;
    private final String confirmationCode;

    public DatabaseCards() {
        listCards = new ConcurrentHashMap<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addCard(properties);
        confirmationCode = properties.getProperty("CODE");
    }

    private void addCard(Properties properties){
        String cardCounter = properties.getProperty("NUMBER_REG_CARDHOLDER");
        for (int i = 1; i <= Integer.parseInt(cardCounter); i++) {
            listCards.put(properties.getProperty("CARD_NUMBER_" + i), new Card(
                    properties.getProperty("CARD_NUMBER_" + i),
                    properties.getProperty("CARD_VALID_TILL_" + i),
                    properties.getProperty("CARD_CVV_" + i),
                    Integer.parseInt(properties.getProperty("CARD_ACCOUNT_" + i)),
                    properties.getProperty("CURRENCY_" + i)
            ));
        }
    }
}
