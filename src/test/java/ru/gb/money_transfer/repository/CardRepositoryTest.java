package ru.gb.money_transfer.repository;

import ru.gb.money_transfer.dao.DatabaseCards;
import ru.gb.money_transfer.model.MoneyTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CardRepositoryTest {

    private CardRepository cardRepository;
    private DatabaseCards databaseCards;
    private MoneyTransfer moneyTransfer;
    private Properties properties;
    private String code;

    @BeforeEach
    public void init() {
        cardRepository = new CardRepository();
        properties = new Properties();
        databaseCards = new DatabaseCards();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        code = properties.getProperty("CODE");
    }

    @ParameterizedTest
    // значение CODE имитирует не верный ввод данных
    @CsvSource({
            "CODE,          CARD_VALID_TILL_1, CARD_CVV_1, CARD_NUMBER_2",
            "CARD_NUMBER_1, CODE,              CARD_CVV_1, CARD_NUMBER_2",
            "CARD_NUMBER_1, CARD_VALID_TILL_1, CODE,       CARD_NUMBER_2",
            "CARD_NUMBER_1, CARD_VALID_TILL_1, CARD_CVV_1, CODE"
    })
    public void test_verificationCard(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber) {
        moneyTransfer = new MoneyTransfer(
                properties.getProperty(cardFromNumber),
                properties.getProperty(cardFromValidTill),
                properties.getProperty(cardFromCVV),
                properties.getProperty(cardToNumber),
                Integer.parseInt(properties.getProperty("VALUE")),
                properties.getProperty("CURRENCY")
        );
        assertNull(cardRepository.verificationCard(moneyTransfer));
    }

    @Test
    public void test_confirmOperation() {
        assertAll("code",
                () -> assertEquals(code, databaseCards.getConfirmationCode()));
    }

    @Test
    public void test_resultMethods() {
        moneyTransfer = new MoneyTransfer(
                properties.getProperty("CARD_NUMBER_1"),
                properties.getProperty("CARD_VALID_TILL_1"),
                properties.getProperty("CARD_CVV_1"),
                properties.getProperty("CARD_NUMBER_2"),
                Integer.parseInt(properties.getProperty("VALUE")),
                properties.getProperty("CURRENCY")
        );

        String resultVerificationCard = cardRepository.verificationCard(moneyTransfer);
        assertEquals("operationId: 1 has been verified", resultVerificationCard);

        String resultConfirmOperation = cardRepository.confirmOperation(properties.getProperty("CODE"));
        assertEquals("operationId: 1 is completed", resultConfirmOperation);
    }
}