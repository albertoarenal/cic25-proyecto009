package es.cic.curso25.proy009.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.repository.ArbolRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    List<Rama> ramas = new ArrayList<>();

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

    @Test
    void testGetAllArboles() throws Exception {
        arbolRepository.deleteAll();

        Arbol arbol1 = new Arbol();
        arbol1.setAltura(10.0);
        arbol1.setCaducifolio(true);

        Rama rama = new Rama();

        rama.setArbol(arbol1);
        rama.setColorHojas("Rojo");
        ramas.add(rama);
        arbol1.setRamas(ramas);
        arbolRepository.save(arbol1);

        Arbol arbol2 = new Arbol();
        arbol2.setAltura(20.0);
        arbol2.setCaducifolio(false);
        arbolRepository.save(arbol2);

        MvcResult result = mockMvc.perform(get("/api/arbol"))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        
        Arbol[] arbolesObtenidos = objectMapper.readValue(respuesta, Arbol[].class);
      
        assertEquals(2, arbolesObtenidos.length);
        assertTrue(arbolesObtenidos[0].getAltura() == 10.0);
        assertTrue(arbolesObtenidos[1].getAltura() == 20.0);

    }

    @Test
    void testGetArbolById() throws Exception {
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(15.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        MvcResult result = mockMvc.perform(get("/api/arbol/" + arbol.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        Arbol arbolObtenido = objectMapper.readValue(respuesta, Arbol.class);

        assertEquals(arbol.getId(), arbolObtenido.getId());
        assertEquals(arbol.getAltura(), arbolObtenido.getAltura());
    }

    @Test
    void testUpdateArbol() throws Exception {
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        arbol.setAltura(25.0);
        String arbolJson = objectMapper.writeValueAsString(arbol);

        mockMvc.perform(put("/api/arbol")
                .contentType("application/json")
                .content(arbolJson))
                .andExpect(status().isOk());

        Optional<Arbol> arbolActualizado = arbolRepository.findById(arbol.getId());
        assertTrue(arbolActualizado.isPresent());
        assertEquals(25.0, arbolActualizado.get().getAltura());
    }

    @Test
    void testDeleteArbol() throws Exception {
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        mockMvc.perform(delete("/api/arbol/" + arbol.getId()))
                .andExpect(status().isOk());

        Optional<Arbol> arbolBorrado = arbolRepository.findById(arbol.getId());
        assertFalse(arbolBorrado.isPresent());
    }

}
