package es.cic.curso25.proy009.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.repository.ArbolRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class ArbolControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArbolRepository arbolRepository;

    @Test
    void testCreate() throws Exception {

        Arbol arbol = new Arbol();

        arbol.setAltura(12.0);
        arbol.setCaducifolio(false);

        String arbolJson = objectMapper.writeValueAsString(arbol);

        mockMvc.perform(post("/api/arbol/guardar")
                .contentType("application/json")
                .content(arbolJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Arbol arbolGuardado = objectMapper.readValue(respuesta, Arbol.class);
                    assertTrue(arbolGuardado.getId() > 0);
                    assertTrue(arbolGuardado.getAltura() == 12.0);

                    Optional<Arbol> arbolEnBd = arbolRepository.findById(arbolGuardado.getId());
                    assertTrue(arbolEnBd.get().equals(arbolGuardado));
                });

    }


}
