package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.CustomerPricing;
import com.diac.awesomehardwaresupply.priceschedule.service.CustomerPricingService;
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

@WebMvcTest(CustomerPricingController.class)
@AutoConfigureMockMvc
public class CustomerPricingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private CustomerPricingService customerPricingService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/customer_pricing")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdFound() throws Exception {
        int id = 1;
        Mockito.when(customerPricingService.findById(id)).thenReturn(CustomerPricing.builder().id(id).build());
        String requestUrl = String.format("/customer_pricing/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdNotFound() throws Exception {
        int id = 1;
        Mockito.when(customerPricingService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/customer_pricing/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        CustomerPricing newCustomerPricing = CustomerPricing.builder()
                .customerNumber(value)
                .productSku(value)
                .build();
        CustomerPricing savedCustomerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber(value)
                .productSku(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newCustomerPricing);
        String responseBody = objectWriter.writeValueAsString(savedCustomerPricing);
        Mockito.when(customerPricingService.add(newCustomerPricing)).thenReturn(savedCustomerPricing);
        mockMvc.perform(
                        post("/customer_pricing")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        CustomerPricing customerPricing = CustomerPricing.builder()
                .customerNumber(null)
                .productSku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(customerPricing);
        mockMvc.perform(
                post("/customer_pricing")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        CustomerPricing customerPricing = CustomerPricing.builder()
                .customerNumber("")
                .productSku("")
                .build();
        String requestBody = objectWriter.writeValueAsString(customerPricing);
        mockMvc.perform(
                post("/customer_pricing")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .customerNumber(value)
                .productSku(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(customerPricing);
        Mockito.when(customerPricingService.add(customerPricing))
                .thenThrow(ResourceConstraintViolationException.class);
        mockMvc.perform(
                post("/customer_pricing")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber(value)
                .productSku(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(customerPricing);
        String requestUrl = String.format("/customer_pricing/%d", id);
        Mockito.when(customerPricingService.update(id, customerPricing)).thenReturn(customerPricing);
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
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber(null)
                .productSku(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(customerPricing);
        String requestUrl = String.format("/customer_pricing/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber("")
                .productSku("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(customerPricing);
        String requestUrl = String.format("/customer_pricing/%d", id);
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
        CustomerPricing customerPricing = CustomerPricing.builder()
                .id(id)
                .customerNumber(value)
                .productSku(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(customerPricing);
        String requestUrl = String.format("/customer_pricing/%d", id);
        Mockito.when(customerPricingService.update(id, customerPricing))
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
        String requestUrl = String.format("/customer_pricing/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}