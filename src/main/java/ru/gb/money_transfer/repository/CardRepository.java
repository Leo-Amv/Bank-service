package ru.gb.money_transfer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.gb.money_transfer.dao.DatabaseCards;
import ru.gb.money_transfer.logger.Logger;
import ru.gb.money_transfer.model.Card;
import ru.gb.money_transfer.model.MoneyTransfer;

import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CardRepository {
    private final Logger logger = Logger.getLog();
    private static AtomicLong operationId;
    private final DatabaseCards databaseCards;
    private final String confirmationCode;

    @Autowired
    public CardRepository() {
        databaseCards = new DatabaseCards();
        operationId = new AtomicLong();
        confirmationCode = databaseCards.getConfirmationCode();
    }

    public String verificationCard(MoneyTransfer moneyTransfer){
        Card cardSender;
        if (databaseCards.getListCards().containsKey(moneyTransfer.getCardFromNumber())){
            cardSender = databaseCards.getListCards().get(moneyTransfer.getCardFromNumber());
            if (!databaseCards.getListCards().containsKey(moneyTransfer.getCardToNumber())) {
                logger.log("Номер карты получателя № " + moneyTransfer.getCardToNumber() + " введен НЕ верно!");
                return null;
            } else if (!moneyTransfer.getCardFromValidTill().equals(cardSender.getCardValid())) {
                logger.log("Дата действия карты отправителя: " + moneyTransfer.getCardFromValidTill() + " введена НЕ верно!");
                return null;
            } else if (!moneyTransfer.getCardFromCVV().equals(cardSender.getCardCVV())) {
                logger.log("Код CVV карты отправителя: " + moneyTransfer.getCardFromCVV() + " введен НЕ верно!");
                return null;
            } else if (!moneyTransfer.getAmount().getCurrency().equals(cardSender.getCurrency())) {
                logger.log("Значение валюты перевода: " + moneyTransfer.getAmount().getCurrency() + " введено НЕ верно!");
                return null;
            } else if (moneyTransfer.getAmount().getValue() > cardSender.getAccount()) {
                logger.log("Сумма перевода превышает сумму счета отправителя!");
                return null;
            } else {
                operationId.getAndIncrement();
                logger.log("Карта отправителя № " + moneyTransfer.getCardFromNumber() + " проверена");
                logger.log("Карта получателя № " + moneyTransfer.getCardToNumber() + " проверена");
                return "operationId: " + operationId + " has been verified";
            }
        } else {
            logger.log("Номер карты отправителя № " + moneyTransfer.getCardFromNumber() + " введен НЕ верно!");
            return null;
        }
    }
    public String confirmOperation(String code) {
        if (code.equals(confirmationCode)) {
            logger.log("Код потверждения операции введен верно");
            return "operationId: " + operationId + " is completed";
        }else {
            logger.log("Код потверждения операции введен НЕ верно!!!");
            return null;
        }

    }


}
