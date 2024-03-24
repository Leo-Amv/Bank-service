package ru.gb.money_transfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.money_transfer.exception.DataMismatch;
import ru.gb.money_transfer.logger.Logger;
import ru.gb.money_transfer.model.MoneyTransfer;
import ru.gb.money_transfer.repository.CardRepository;
import ru.gb.money_transfer.exception.IncorrectDataEntry;

@Service
public class MoneyTransferService {
    private final Logger logger = Logger.getLog();
    private final CardRepository cardRepository;
    private MoneyTransfer moneyTransfer;

    @Autowired
    public MoneyTransferService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public String verificationCard(MoneyTransfer moneyTransfer){
        this.moneyTransfer = moneyTransfer;
        if (isEmpty(moneyTransfer.getCardFromNumber()) || isEmpty(moneyTransfer.getCardFromValidTill()) ||
                isEmpty(moneyTransfer.getCardFromCVV()) || isEmpty(moneyTransfer.getCardToNumber())) {
            logger.log("ERROR! Ошибка ввода данных карты: Error input data card");
            throw new IncorrectDataEntry("Error input data card");
        }
        if (isEmpty(moneyTransfer.getAmount().getCurrency()) || isEmptyNumber(moneyTransfer.getAmount().getValue())) {
            logger.log("ERROR! Ошибка ввода суммы: Error input data amount");
            throw new IncorrectDataEntry("Error input data amount");
        }
        String operationId = cardRepository.verificationCard(moneyTransfer);
        if (isEmpty(operationId)) {
            logger.log("ERROR! Ошибка перевода: Error transfer");
            throw new DataMismatch("Error transfer");
        }
        return operationId;
    }

    public String confirmOperation(String code){
        if (isEmpty(code)) {
            logger.log("ERROR! Ошибка ввода кода подтверждения: Error input data code");
            throw new IncorrectDataEntry("Error input data code");
        }
        String operationId = cardRepository.confirmOperation(code);
        if (isEmpty(operationId)) {
            logger.log("ERROR! Ошибка перевода: Error transfer");
            throw new DataMismatch("Error transfer");
        }

        logger.log("Операция: \"" + operationId + "\" выполнена \n" +
                "Карта списания: " + moneyTransfer.getCardFromNumber() + "\n" +
                "Карта зачисления: " + moneyTransfer.getCardToNumber() + "\n" +
                "Сумма перевода: " + moneyTransfer.getAmount().getValue() + "\n" +
                "Валюта перевода: " + moneyTransfer.getAmount().getCurrency());
        return operationId;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmptyNumber(int num) {
        return num <= 0;
    }

}
