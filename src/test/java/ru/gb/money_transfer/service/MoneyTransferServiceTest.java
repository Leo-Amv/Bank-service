package ru.gb.money_transfer.service;

import ru.gb.money_transfer.model.MoneyTransfer;
import ru.gb.money_transfer.exception.IncorrectDataEntry;
import ru.gb.money_transfer.exception.DataMismatch;
import ru.gb.money_transfer.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTransferServiceTest {


    private final CardRepository cardRepository = new CardRepository();
    private final MoneyTransferService moneyTransferService = new MoneyTransferService(cardRepository);
    private MoneyTransfer verificationCard;

    @BeforeEach
    public void init() {
        verificationCard = new MoneyTransfer("cardFromNumber", "cardFromValidTill",
                "cardFromCVV", "cardToNumber", 10000, "currency");
    }

    @Test
    public void test_IncorrectDataEntry_errorInputDataCard() {
        verificationCard.setCardFromNumber(null);
        Exception exception = assertThrows(IncorrectDataEntry.class, () -> moneyTransferService.verificationCard(verificationCard));
        assertEquals("Error input data card", exception.getMessage());
    }

    @Test
    public void test_IncorrectDataEntry_errorInputDataAmount() {
        verificationCard.getAmount().setValue(0);
        Exception exception = assertThrows(IncorrectDataEntry.class, () -> moneyTransferService.verificationCard(verificationCard));
        assertEquals("Error input data amount", exception.getMessage());
    }

    @Test
    public void test_IncorrectDataEntry_errorInputDataCode() {
        String code = null;
        Exception exception = assertThrows(IncorrectDataEntry.class, () -> moneyTransferService.confirmOperation(code));
        assertEquals("Error input data code", exception.getMessage());
    }

    @Test
    public void test_DataMismatch_verificationCard() {
        Exception exception1 = assertThrows(DataMismatch.class, () -> moneyTransferService.verificationCard(verificationCard));
        assertEquals("Error transfer", exception1.getMessage());
    }

    @Test
    public void test_DataMismatch_confirmOperation() {
        String code = "code";
        Exception exception2 = assertThrows(DataMismatch.class, () -> moneyTransferService.confirmOperation(code));
        assertEquals("Error transfer", exception2.getMessage());
    }

}