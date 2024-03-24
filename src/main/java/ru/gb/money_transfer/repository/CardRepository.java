package ru.gb.money_transfer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CardRepository {
    private static AtomicLong operationId;
    private final String confirmationCode;

    @Autowired
    public CardRepository() {
        operationId = new AtomicLong();
        confirmationCode = new String();
    }
}
