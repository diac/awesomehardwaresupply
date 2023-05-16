package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.PriceLevel;
import com.diac.awesomehardwaresupply.domain.model.ProductPricing;
import com.diac.awesomehardwaresupply.priceschedule.service.ProductPricingService;
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

@WebMvcTest(ProductPricingController.class)
@AutoConfigureMockMvc
public class ProductPricingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductPricingService productPricingService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/product_pricing")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdFound() throws Exception {
        int id = 1;
        Mockito.when(productPricingService.findById(id)).thenReturn(ProductPricing.builder().id(id).build());
        String requestUrl = String.format("/product_pricing/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isFound());
    }

    @Test
    public void whenGetByIdNotFound() throws Exception {
        int id = 1;
        Mockito.when(productPricingService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/product_pricing/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        ProductPricing newProductPricing = ProductPricing.builder()
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        ProductPricing savedProductPricing = ProductPricing.builder()
                .id(id)
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        String requestBody = objectWriter.writeValueAsString(newProductPricing);
        String responseBody = objectWriter.writeValueAsString(savedProductPricing);
        Mockito.when(productPricingService.add(newProductPricing)).thenReturn(savedProductPricing);
        mockMvc.perform(
                        post("/product_pricing")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductPricing productPricing = ProductPricing.builder()
                .productSku(null)
                .priceLevel(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(productPricing);
        mockMvc.perform(
                post("/product_pricing")
                        .contentType(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductPricing productPricing = ProductPricing.builder()
                .productSku("")
                .priceLevel(PriceLevel.builder().id(1).build())
                .build();
        String requestBody = objectWriter.writeValueAsString(productPricing);
        mockMvc.perform(
                post("/product_pricing")
                        .contentType(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .productSku(value)
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        String jsonValue = objectWriter.writeValueAsString(productPricing);
        String requestUrl = String.format("/product_pricing/%d", id);
        Mockito.when(productPricingService.update(id, productPricing)).thenReturn(productPricing);
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
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .productSku(null)
                .priceLevel(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productPricing);
        String requestUrl = String.format("/product_pricing/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductPricing productPricing = ProductPricing.builder()
                .id(id)
                .productSku("")
                .priceLevel(PriceLevel.builder().id(id).build())
                .build();
        String jsonValue = objectWriter.writeValueAsString(productPricing);
        String requestUrl = String.format("/product_pricing/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/product_pricing/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}