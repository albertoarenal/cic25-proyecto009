package es.cic.curso25.proy009.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy009.repository.ArbolRepository;
import es.cic.curso25.proy009.repository.RamaRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.repository.ArbolRepository;
import es.cic.curso25.proy009.repository.RamaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RamaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RamaRepository ramaRepository;

    @Autowired
    private ArbolRepository arbolRepository;

    @Test
    void testCreateRama() throws Exception {

        Arbol testArbol = new Arbol();
        testArbol.setAltura(15.0);
        testArbol.setCaducifolio(true);
        testArbol = arbolRepository.save(testArbol);

        Rama rama = new Rama();
        rama.setColorHojas("verde claro");
        rama.setFormaHojas("palmeada");
        rama.setPodrida(false);

        String ramaJson = objectMapper.writeValueAsString(rama);

        mockMvc.perform(post("/api/rama/" + testArbol.getId())
                .contentType("application/json")
                .content(ramaJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Rama ramaGuardada = objectMapper.readValue(respuesta, Rama.class);
                    assertNotNull(ramaGuardada.getId());
                    assertEquals(rama.getColorHojas(), ramaGuardada.getColorHojas());

                    Optional<Rama> ramaEnBd = ramaRepository.findById(ramaGuardada.getId());
                    assertTrue(ramaEnBd.isPresent());
                    assertEquals(ramaGuardada, ramaEnBd.get());
                });
    }

    @Test
    void testGetRamaById() throws Exception {

        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        // Crear un árbol de prueba para asociar ramas
        Arbol testArbol = new Arbol();
        testArbol.setAltura(15.0);
        testArbol.setCaducifolio(true);
        testArbol = arbolRepository.save(testArbol);

        Rama rama = new Rama();
        rama.setColorHojas("rojo");
        rama.setFormaHojas("lobulada");
        rama.setPodrida(true);
        rama.setArbol(testArbol);
        rama = ramaRepository.save(rama);

        MvcResult result = mockMvc.perform(get("/api/rama/" + rama.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        Rama ramaObtenida = objectMapper.readValue(respuesta, Rama.class);

        assertEquals(rama.getId(), ramaObtenida.getId());
        assertEquals(rama.getColorHojas(), ramaObtenida.getColorHojas());
    }

    @Test
    void testGetAllRamas() throws Exception {

        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        // Crear un árbol de prueba para asociar ramas
        Arbol testArbol = new Arbol();
        testArbol.setAltura(15.0);
        testArbol.setCaducifolio(true);
        testArbol = arbolRepository.save(testArbol);

        Rama rama1 = new Rama();
        rama1.setColorHojas("azul");
        rama1.setArbol(testArbol);
        ramaRepository.save(rama1);

        Rama rama2 = new Rama();
        rama2.setColorHojas("amarillo");
        rama2.setArbol(testArbol);
        ramaRepository.save(rama2);

        MvcResult result = mockMvc.perform(get("/api/rama"))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        Rama[] ramasObtenidas = objectMapper.readValue(respuesta, Rama[].class);
        assertEquals(2, ramasObtenidas.length);
        assertTrue(Arrays.stream(ramasObtenidas).anyMatch(r -> "azul".equals(r.getColorHojas())));
        assertTrue(Arrays.stream(ramasObtenidas).anyMatch(r -> "amarillo".equals(r.getColorHojas())));
    }

    @Test
    void testUpdateRama() throws Exception {

        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        // Crear un árbol de prueba para asociar ramas
        Arbol testArbol = new Arbol();
        testArbol.setAltura(15.0);
        testArbol.setCaducifolio(true);
        testArbol = arbolRepository.save(testArbol);

        Rama rama = new Rama();
        rama.setColorHojas("original");
        rama.setArbol(testArbol);
        rama = ramaRepository.save(rama);

        rama.setColorHojas("modificado");
        String ramaJson = objectMapper.writeValueAsString(rama);

        mockMvc.perform(put("/api/rama")
                .contentType("application/json")
                .content(ramaJson))
                .andExpect(status().isOk());

        Optional<Rama> ramaActualizada = ramaRepository.findById(rama.getId());
        assertTrue(ramaActualizada.isPresent());
        assertEquals("modificado", ramaActualizada.get().getColorHojas());
    }

    @Test
    void testDeleteRama() throws Exception {

        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        // Crear un árbol de prueba para asociar ramas
        Arbol testArbol = new Arbol();
        testArbol.setAltura(15.0);
        testArbol.setCaducifolio(true);
        testArbol = arbolRepository.save(testArbol);

        Rama rama = new Rama();
        rama.setColorHojas("a borrar");
        rama.setArbol(testArbol);
        rama = ramaRepository.save(rama);

        mockMvc.perform(delete("/api/rama/" + rama.getId()))
                .andExpect(status().isOk());

        Optional<Rama> ramaBorrada = ramaRepository.findById(rama.getId());
        assertFalse(ramaBorrada.isPresent());
    }
}
