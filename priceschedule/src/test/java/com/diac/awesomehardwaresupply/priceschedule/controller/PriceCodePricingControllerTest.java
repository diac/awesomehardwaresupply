package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.domain.model.PriceCodePricing;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.priceschedule.service.PriceCodePricingService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceCodePricingController.class)
@AutoConfigureMockMvc
public class PriceCodePricingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PriceCodePricingService priceCodePricingService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/price_code_pricing")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdFound() throws Exception {
        int id = 1;
        Mockito.when(priceCodePricingService.findById(id)).thenReturn(PriceCodePricing.builder().id(id).build());
        String requestUrl = String.format("/price_code_pricing/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdNotFound() throws Exception {
        int id = 1;
        Mockito.when(priceCodePricingService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/price_code_pricing/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        PriceCodePricing newPriceCodePricing = PriceCodePricing.builder()
                .priceCode(PriceCode.builder().id(id).build())
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        PriceCodePricing savedPriceCodePricing = PriceCodePricing.builder()
                .id(id)
                .priceCode(PriceCode.builder().id(id).build())
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        String requestBody = objectWriter.writeValueAsString(newPriceCodePricing);
        String responseBody = objectWriter.writeValueAsString(savedPriceCodePricing);
        Mockito.when(priceCodePricingService.add(newPriceCodePricing)).thenReturn(savedPriceCodePricing);
        mockMvc.perform(
                        post("/price_code_pricing")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .priceCode(null)
                .priceLevel(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(priceCodePricing);
        mockMvc.perform(
                post("/price_code_pricing")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .priceCode(PriceCode.builder().id(id).build())
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        String requestBody = objectWriter.writeValueAsString(priceCodePricing);
        Mockito.when(priceCodePricingService.add(priceCodePricing))
                .thenThrow(ResourceConstraintViolationException.class);
        mockMvc.perform(
                post("/price_code_pricing")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .id(id)
                .priceCode(PriceCode.builder().id(id).build())
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceCodePricing);
        String requestUrl = String.format("/price_code_pricing/%d", id);
        Mockito.when(priceCodePricingService.update(id, priceCodePricing)).thenReturn(priceCodePricing);
        mockMvc.perform(
                        put(requestUrl)
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(jsonValue));
    }

    @Test
    public void whenPutWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .id(id)
                .priceCode(null)
                .priceLevel(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceCodePricing);
        String requestUrl = String.format("/price_code_pricing/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        int id = 1;
        PriceCodePricing priceCodePricing = PriceCodePricing.builder()
                .id(id)
                .priceCode(PriceCode.builder().id(id).build())
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceCodePricing);
        String requestUrl = String.format("/price_code_pricing/%d", id);
        Mockito.when(priceCodePricingService.update(id, priceCodePricing))
                .thenThrow(ResourceConstraintViolationException.class);
        mockMvc.perform(
                        put(requestUrl)
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/price_code_pricing/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}