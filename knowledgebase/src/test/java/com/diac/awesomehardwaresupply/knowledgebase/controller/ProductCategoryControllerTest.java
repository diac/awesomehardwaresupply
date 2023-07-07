package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductCategory;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductCategoryService;
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

@WebMvcTest(ProductCategoryController.class)
@AutoConfigureMockMvc
public class ProductCategoryControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductCategoryService productCategoryService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/product_category")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(productCategoryService.findById(id)).thenReturn(ProductCategory.builder().id(id).build());
        String requestUrl = String.format("/product_category/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(productCategoryService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/product_category/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        ProductCategory newProductCategory = ProductCategory.builder()
                .name(value)
                .description(value)
                .build();
        ProductCategory savedProductCategory = ProductCategory.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newProductCategory);
        String responseBody = objectWriter.writeValueAsString(savedProductCategory);
        Mockito.when(productCategoryService.add(newProductCategory)).thenReturn(savedProductCategory);
        mockMvc.perform(
                        post("/product_category")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductCategory productCategory = ProductCategory.builder()
                .name(null)
                .description(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(productCategory);
        mockMvc.perform(
                post("/product_category")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductCategory productCategory = ProductCategory.builder()
                .name("")
                .description("")
                .build();
        String requestBody = objectWriter.writeValueAsString(productCategory);
        mockMvc.perform(
                post("/product_category")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        int id = 1;
        String value = "test";
        ProductCategory productCategory = ProductCategory.builder()
                .name(value)
                .description(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(productCategory);
        Mockito.when(productCategoryService.add(productCategory))
                .thenThrow(ResourceConstraintViolationException.class);
        mockMvc.perform(
                post("/product_category")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productCategory);
        String requestUrl = String.format("/product_category/%d", id);
        Mockito.when(productCategoryService.update(id, productCategory)).thenReturn(productCategory);
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
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .name(null)
                .description(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productCategory);
        String requestUrl = String.format("/product_category/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .name("")
                .description("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(productCategory);
        String requestUrl = String.format("/product_category/%d", id);
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
        ProductCategory productCategory = ProductCategory.builder()
                .id(id)
                .name(value)
                .description(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productCategory);
        String requestUrl = String.format("/product_category/%d", id);
        Mockito.when(productCategoryService.update(id, productCategory))
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
        String requestUrl = String.format("/product_category/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}