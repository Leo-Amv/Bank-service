package ru.gb.money_transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.money_transfer.logger.Logger;
import ru.gb.money_transfer.model.MoneyTransfer;
import ru.gb.money_transfer.service.MoneyTransferService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class MoneyTransferController {
    private final Logger logger = Logger.getLog();
    private final MoneyTransferService service;

    @Autowired
    public MoneyTransferController(MoneyTransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public String verificationCard(@RequestBody MoneyTransfer body){
        logger.log("POST запрос с данными карт принят");
        return service.verificationCard(body);
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody String body) {
        System.out.println(body);
        String code = body.substring(body.indexOf(":") + 2, body.lastIndexOf("\""));
        logger.log("POST запрос с кодом подтверждения принят");
        return service.confirmOperation(code);
    }

}
