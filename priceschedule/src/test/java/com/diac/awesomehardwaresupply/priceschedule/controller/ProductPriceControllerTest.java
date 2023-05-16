package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.dto.ProductPriceRequestDto;
import com.diac.awesomehardwaresupply.domain.dto.ProductPriceResponseDto;
import com.diac.awesomehardwaresupply.priceschedule.service.ProductPriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductPriceController.class)
@AutoConfigureMockMvc
public class ProductPriceControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductPriceService productPriceService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenCalculate() throws Exception {
        String value = "test";
        int number = 1234;
        ProductPriceRequestDto requestDto = ProductPriceRequestDto.builder()
                .priceLevel(value)
                .productSku(value)
                .priceCode(value)
                .customerNumber(value)
                .quantity(number)
                .build();
        ProductPriceResponseDto responseDto = new ProductPriceResponseDto(value, number);
        Mockito.when(productPriceService.calculate(requestDto)).thenReturn(responseDto);
        String requestBody = objectWriter.writeValueAsString(requestDto);
        String responseBody = objectWriter.writeValueAsString(responseDto);
        mockMvc.perform(
                        post("/product_price/calculate")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenCalculateForZeroQuantityThenRequestStatusIsBadRequest() throws Exception {
        String value = "test";
        int zeroQuantity = 0;
        ProductPriceRequestDto requestDto = ProductPriceRequestDto.builder()
                .priceLevel(value)
                .productSku(value)
                .priceCode(value)
                .customerNumber(value)
                .quantity(zeroQuantity)
                .build();
        String requestBody = objectWriter.writeValueAsString(requestDto);
        mockMvc.perform(
                post("/product_price/calculate")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenCalculateWithNullValuesThenRequestStatusIsBadRequest() throws Exception {
        int number = 1234;
        ProductPriceRequestDto requestDto = ProductPriceRequestDto.builder()
                .productSku(null)
                .quantity(number)
                .build();
        String requestBody = objectWriter.writeValueAsString(requestDto);
        mockMvc.perform(
                post("/product_price/calculate")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}