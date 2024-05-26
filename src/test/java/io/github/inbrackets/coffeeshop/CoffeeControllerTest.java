package io.github.inbrackets.coffeeshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoffeeController.class)
class CoffeeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static Coffee coffee1 = new Coffee("1", "Café Cereza");
    private final static Coffee coffee2 = new Coffee("2", "Café Ganador");
    private final static Coffee coffee3 = new Coffee("3", "Café Lareño");
    private final static Coffee coffee4 = new Coffee("4", "Café Três Pontas");
    private final static Coffee newCoffee = new Coffee("5", "New Coffee");

    @BeforeEach
    void setUp() throws Exception {
        for(Coffee coffee: List.of(coffee1, coffee2, coffee3, coffee4)) {
            mockMvc.perform(put("/coffees/{id}", coffee.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(coffee)))
                    .andExpect(jsonPath("$.name", is(coffee.getName())));
        }
        mockMvc.perform(delete("/coffees/{id}", newCoffee.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getCoffees() throws Exception {
        mockMvc.perform(get("/coffees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name", is(coffee1.getName())))
                .andExpect(jsonPath("$[1].name", is(coffee2.getName())))
                .andExpect(jsonPath("$[2].name", is(coffee3.getName())))
                .andExpect(jsonPath("$[3].name", is(coffee4.getName())));
    }

    @Test
    void getCoffeeById() throws Exception {
        mockMvc.perform(get("/coffees/{id}", coffee1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(coffee1.getName())));

        mockMvc.perform(get("/coffees/{id}", "nonexistent-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void postCoffee() throws Exception {
        Coffee newCoffee = new Coffee("5", "Café Novo");

        mockMvc.perform(post("/coffees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCoffee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newCoffee.getName())));
    }

    @Test
    void putCoffee() throws Exception {
        Coffee updatedCoffee = new Coffee(coffee1.getId(), "Updated Coffee");

        mockMvc.perform(put("/coffees/{id}", coffee1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCoffee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedCoffee.getName())));

        Coffee newCoffee = new Coffee("5", "New Coffee");

        mockMvc.perform(put("/coffees/{id}", "new-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCoffee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(newCoffee.getName())));
    }

    @Test
    void deleteCoffee() throws Exception {
        mockMvc.perform(delete("/coffees/{id}", coffee4.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/coffees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}