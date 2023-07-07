package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductSpecification;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductSpecificationService;
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

@WebMvcTest(ProductSpecificationController.class)
@AutoConfigureMockMvc
public class ProductSpecificationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductSpecificationService productSpecificationService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/product_specification")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(productSpecificationService.findById(id))
                .thenReturn(ProductSpecification.builder().id(id).build());
        String requestUrl = String.format("/product_specification/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(productSpecificationService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/product_specification/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        ProductSpecification newProductSpecification = ProductSpecification.builder()
                .name(value)
                .description(value)
                .units(value)
                .build();
        ProductSpecification savedProductSpecification = ProductSpecification.builder()
                .id(id)
                .name(value)
                .description(value)
                .units(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newProductSpecification);
        String responseBody = objectWriter.writeValueAsString(savedProductSpecification);
        Mockito.when(productSpecificationService.add(newProductSpecification)).thenReturn(savedProductSpecification);
        mockMvc.perform(
                        post("/product_specification")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductSpecification productSpecification = ProductSpecification.builder()
                .name(null)
                .description(null)
                .units(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(productSpecification);
        mockMvc.perform(
                post("/product_specification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductSpecification productSpecification = ProductSpecification.builder()
                .name("")
                .description("")
                .units("")
                .build();
        String requestBody = objectWriter.writeValueAsString(productSpecification);
        mockMvc.perform(
                post("/product_specification")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        ProductSpecification productSpecification = ProductSpecification.builder()
                .id(id)
                .name(value)
                .description(value)
                .units(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productSpecification);
        String requestUrl = String.format("/product_specification/%d", id);
        Mockito.when(productSpecificationService.update(id, productSpecification)).thenReturn(productSpecification);
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
        ProductSpecification productSpecification = ProductSpecification.builder()
                .id(id)
                .name(null)
                .description(null)
                .units(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productSpecification);
        String requestUrl = String.format("/product_specification/%d", id);
        Mockito.when(productSpecificationService.update(id, productSpecification)).thenReturn(productSpecification);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductSpecification productSpecification = ProductSpecification.builder()
                .id(id)
                .name("")
                .description("")
                .units("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(productSpecification);
        String requestUrl = String.format("/product_specification/%d", id);
        Mockito.when(productSpecificationService.update(id, productSpecification)).thenReturn(productSpecification);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/product_specification/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}