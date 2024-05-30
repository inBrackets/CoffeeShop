package io.github.inbrackets.coffeeshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoffeeController.class)
public class CoffeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeRepository coffeeRepository;

    private Coffee coffee;

    @BeforeEach
    public void setUp() {
        coffee = new Coffee("1", "Café Cereza");
    }

    @Test
    public void testGetCoffees() throws Exception {
        when(coffeeRepository.findAll()).thenReturn(List.of(coffee));

        mockMvc.perform(get("/coffees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Café Cereza"));
    }

    @Test
    public void testGetCoffeeById() throws Exception {
        when(coffeeRepository.findById(anyString())).thenReturn(Optional.of(coffee));

        mockMvc.perform(get("/coffees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Café Cereza"));
    }

    @Test
    public void testPostCoffee() throws Exception {
        when(coffeeRepository.save(any(Coffee.class))).thenReturn(coffee);

        mockMvc.perform(post("/coffees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Café Cereza\"}"))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value("Café Cereza"));
    }

    @Test
    public void testPutCoffee() throws Exception {
        when(coffeeRepository.existsById(anyString())).thenReturn(true);
        when(coffeeRepository.save(any(Coffee.class))).thenReturn(coffee);

        mockMvc.perform(put("/coffees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Café Cereza\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Café Cereza"));
    }

    @Test
    public void testDeleteCoffee() throws Exception {
        doNothing().when(coffeeRepository).deleteById(anyString());

        mockMvc.perform(delete("/coffees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}