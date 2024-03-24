package ru.gb.money_transfer;

import ru.gb.money_transfer.model.MoneyTransfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {
    private final static int PORT = 8080;
    private Properties properties;

    @BeforeEach
    public void init() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private final static GenericContainer<?> myApp = new GenericContainer<>("transfer_service_backend:latest")
            .withExposedPorts(PORT);

    @Test
    void moneyTransferTest() {
        MoneyTransfer request = new MoneyTransfer(
                properties.getProperty("CARD_NUMBER_1"),
                properties.getProperty("CARD_VALID_TILL_1"),
                properties.getProperty("CARD_CVV_1"),
                properties.getProperty("CARD_NUMBER_2"),
                Integer.parseInt(properties.getProperty("VALUE")),
                properties.getProperty("CURRENCY")
        );

        ResponseEntity<String> forEntity = restTemplate.postForEntity(
                "http://localhost:" + myApp.getMappedPort(PORT) + "/transfer", request, String.class);
        System.out.println(forEntity.getBody());
        String expected = "operationId: 1 has been verified";
        String actual = forEntity.getBody();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void confirmOperationTest() {
        String code = properties.getProperty("CODE");

        ResponseEntity<String> forEntity = restTemplate.postForEntity(
                "http://localhost:" + myApp.getMappedPort(PORT) + "/confirmOperation", code, String.class);
        System.out.println(forEntity.getBody());
        String expected = "operationId: 1 is completed";
        String actual = forEntity.getBody();
        Assertions.assertEquals(expected, actual);

        System.out.println(HttpStatus.INTERNAL_SERVER_ERROR);

        // выбрасывает ошибку 500, т.к. экземпляр класса MoneyTransfer, используемый в запросе, создается при запросе /transfer
        //Assertions.assertEquals(expected, actual);
    }
}