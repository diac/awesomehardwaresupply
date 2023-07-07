package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.priceschedule.service.PriceLevelService;
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

@WebMvcTest(PriceLevelController.class)
@AutoConfigureMockMvc
public class PriceLevelControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PriceLevelService priceLevelService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/price_level")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenAll() throws Exception {
        mockMvc.perform(
                get("/price_level")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdFound() throws Exception {
        int id = 1;
        Mockito.when(priceLevelService.findById(id)).thenReturn(PriceLevel.builder().id(id).build());
        String requestUrl = String.format("/price_level/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdNotFound() throws Exception {
        int id = 1;
        Mockito.when(priceLevelService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/price_level/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        PriceLevel newPriceLevel = PriceLevel.builder()
                .name(value)
                .build();
        PriceLevel savedPriceLevel = PriceLevel.builder()
                .id(id)
                .name(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newPriceLevel);
        String responseBody = objectWriter.writeValueAsString(savedPriceLevel);
        Mockito.when(priceLevelService.add(newPriceLevel)).thenReturn(savedPriceLevel);
        mockMvc.perform(
                        post("/price_level")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        PriceLevel priceLevel = PriceLevel.builder()
                .name(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(priceLevel);
        mockMvc.perform(
                post("/price_level")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        PriceLevel priceLevel = PriceLevel.builder()
                .name("")
                .build();
        String requestBody = objectWriter.writeValueAsString(priceLevel);
        mockMvc.perform(
                post("/price_level")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        String value = "test";
        PriceLevel priceLevel = PriceLevel.builder()
                .name(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(priceLevel);
        Mockito.when(priceLevelService.add(priceLevel))
                .thenThrow(ResourceConstraintViolationException.class);
        mockMvc.perform(
                        post("/price_level")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .name(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceLevel);
        String requestUrl = String.format("/price_level/%d", id);
        Mockito.when(priceLevelService.update(id, priceLevel)).thenReturn(priceLevel);
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
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .name(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceLevel);
        String requestUrl = String.format("/price_level/%d", id);
        Mockito.when(priceLevelService.update(id, priceLevel)).thenReturn(priceLevel);
        mockMvc.perform(
                        put(requestUrl)
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .name("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceLevel);
        String requestUrl = String.format("/price_level/%d", id);
        Mockito.when(priceLevelService.update(id, priceLevel)).thenReturn(priceLevel);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        int id = 1;
        String value = "test";
        PriceLevel priceLevel = PriceLevel.builder()
                .id(id)
                .name(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(priceLevel);
        String requestUrl = String.format("/price_level/%d", id);
        Mockito.when(priceLevelService.update(id, priceLevel))
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
        String requestUrl = String.format("/price_level/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}