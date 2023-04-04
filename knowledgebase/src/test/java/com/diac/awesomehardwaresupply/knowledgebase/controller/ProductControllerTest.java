package com.diac.awesomehardwaresupply.knowledgebase.controller;

import com.diac.awesomehardwaresupply.domain.model.Product;
import com.diac.awesomehardwaresupply.knowledgebase.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.EntityNotFoundException;
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

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/product")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetFound() throws Exception {
        int id = 1;
        Mockito.when(productService.findById(id)).thenReturn(Product.builder().id(id).build());
        String requestUrl = String.format("/product/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isFound());
    }

    @Test
    public void whenGetNotFound() throws Exception {
        int id = 1;
        Mockito.when(productService.findById(id)).thenThrow(EntityNotFoundException.class);
        String requestUrl = String.format("/product/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenFindBySkuFound() throws Exception {
        String sku = "test";
        Mockito.when(productService.findBySku(sku))
                .thenReturn(
                        Product.builder()
                                .id(1)
                                .sku(sku)
                                .build()
                );
        String requestUrl = String.format("/product/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isFound());
    }

    @Test
    public void whenFindBySkuNotFound() throws Exception {
        String sku = "test";
        Mockito.when(productService.findBySku(sku)).thenThrow(EntityNotFoundException.class);
        String requestUrl = String.format("/product/find_by_sku/%s", sku);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        Product newProduct = Product.builder()
                .name(value)
                .description(value)
                .sku(value)
                .build();
        Product savedProduct = Product.builder()
                .id(id)
                .name(value)
                .description(value)
                .sku(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newProduct);
        String responseBody = objectWriter.writeValueAsString(savedProduct);
        Mockito.when(productService.add(newProduct)).thenReturn(savedProduct);
        mockMvc.perform(
                        post("/product")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        Product product = Product.builder()
                .name(null)
                .description(null)
                .sku(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(product);
        mockMvc.perform(
                post("/product")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        Product product = Product.builder()
                .name("")
                .description("")
                .sku("")
                .build();
        String requestBody = objectWriter.writeValueAsString(product);
        mockMvc.perform(
                post("/product")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        Product product = Product.builder()
                .id(id)
                .name(value)
                .description(value)
                .sku(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(product);
        String requestUrl = String.format("/product/%d", id);
        Mockito.when(productService.update(id, product)).thenReturn(product);
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
        Product product = Product.builder()
                .id(id)
                .name(null)
                .description(null)
                .sku(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(product);
        String requestUrl = String.format("/product/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        Product product = Product.builder()
                .id(id)
                .name("")
                .description("")
                .sku("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(product);
        String requestUrl = String.format("/product/%d", id);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/product/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }

}