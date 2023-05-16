package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceCode;
import com.diac.awesomehardwaresupply.priceschedule.service.PriceCodeService;
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

@WebMvcTest(PriceCodeController.class)
@AutoConfigureMockMvc
public class PriceCodeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PriceCodeService priceCodeService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/price_code")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenAll() throws Exception {
        mockMvc.perform(
                get("/price_code/all")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdFound() throws Exception {
        int id = 1;
        Mockito.when(priceCodeService.findById(id)).thenReturn(PriceCode.builder().id(id).build());
        String requestUrl = String.format("/price_code/%s", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isFound());
    }

    @Test
    public void whenGetByIdNotFound() throws Exception {
        int id = 1;
        Mockito.when(priceCodeService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/price_code/%s", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        PriceCode newPriceCode = PriceCode.builder()
                .name(value)
                .build();
        PriceCode savedPriceCode = PriceCode.builder()
                .id(id)
                .name(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newPriceCode);
        String responseBody = objectWriter.writeValueAsString(savedPriceCode);
        Mockito.when(priceCodeService.add(newPriceCode)).thenReturn(savedPriceCode);
        mockMvc.perform(
                        post("/price_code")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        PriceCode priceCode = PriceCode.builder()
                .name(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(priceCode);
        mockMvc.perform(
                post("/price_code")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        PriceCode priceCode = PriceCode.builder()
                .name("")
                .build();
        String requestBody = objectWriter.writeValueAsString(priceCode);
        mockMvc.perform(
                post("/price_code")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .name(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceCode);
        String requestUrl = String.format("/price_code/%d", id);
        Mockito.when(priceCodeService.update(id, priceCode)).thenReturn(priceCode);
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
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .name(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceCode);
        String requestUrl = String.format("/price_code/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        PriceCode priceCode = PriceCode.builder()
                .id(id)
                .name("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceCode);
        String requestUrl = String.format("/price_code/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/price_code/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}