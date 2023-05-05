package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductFilter;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductFilterService;
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

@WebMvcTest(ProductFilterController.class)
@AutoConfigureMockMvc
public class ProductFilterControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductFilterService productFilterService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/product_filter")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(productFilterService.findById(id)).thenReturn(ProductFilter.builder().id(id).build());
        String requestUrl = String.format("/product_filter/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(productFilterService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/product_filter/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        ProductFilter newProductFilter = ProductFilter.builder()
                .name(value)
                .description(value)
                .build();
        ProductFilter savedProductFilter = ProductFilter.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newProductFilter);
        String responseBody = objectWriter.writeValueAsString(savedProductFilter);
        Mockito.when(productFilterService.add(newProductFilter)).thenReturn(savedProductFilter);
        mockMvc.perform(
                        post("/product_filter")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductFilter productFilter = ProductFilter.builder()
                .name(null)
                .description(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(productFilter);
        mockMvc.perform(
                post("/product_filter")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductFilter productFilter = ProductFilter.builder()
                .name("")
                .description("")
                .build();
        String requestBody = objectWriter.writeValueAsString(productFilter);
        mockMvc.perform(
                post("/product_filter")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productFilter);
        String requestUrl = String.format("/product_filter/%d", id);
        Mockito.when(productFilterService.update(id, productFilter)).thenReturn(productFilter);
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
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .name(null)
                .description(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productFilter);
        String requestUrl = String.format("/product_filter/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductFilter productFilter = ProductFilter.builder()
                .id(id)
                .name("")
                .description("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(productFilter);
        String requestUrl = String.format("/product_filter/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/product_filter/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}