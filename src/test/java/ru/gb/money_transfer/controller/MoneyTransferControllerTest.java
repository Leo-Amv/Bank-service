package ru.gb.money_transfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.gb.money_transfer.model.MoneyTransfer;
import ru.gb.money_transfer.service.MoneyTransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoneyTransferController.class)
public class MoneyTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MoneyTransferService moneyTransferService;

    @Test
    public void test_postRequestTransfer() throws Exception {
        MoneyTransfer verificationCard = new MoneyTransfer("cardFromNumber", "cardFromValidTill",
                "cardFromCVV", "cardToNumber", 0, "currency");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/transfer")
                .content(objectMapper.writeValueAsString(verificationCard))
                .contentType(MediaType.APPLICATION_JSON.getMediaType());

        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }

    @Test
    public void test_postRequestConfirmOperation() throws Exception {
        String body = "confirmation code";

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/confirmOperation")
                .content(objectMapper.writeValueAsString(body))
                .contentType(MediaType.APPLICATION_JSON.getMediaType());

        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }

}
