package es.cic.curso25.proy009.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.repository.ArbolRepository;

@SpringBootTest
public class ArbolServiceIntegrationTest {

    @Autowired
    private ArbolService arbolService;

    @Autowired
    private ArbolRepository arbolRepository;

    @Test
    void testCreate() {
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(12.0);
        arbol.setCaducifolio(false);

        Arbol arbolCreado = arbolService.create(arbol);

        assertTrue(arbolCreado.getId() > 0);
        Optional<Arbol> arbolEnBd = arbolRepository.findById(arbolCreado.getId());
        assertTrue(arbolEnBd.isPresent());
        assertEquals(arbolCreado.getAltura(), arbolEnBd.get().getAltura());
    }

    @Test
    void testGet() {
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(15.0);
        arbol.setCaducifolio(true);
        Arbol arbolCreado = arbolService.create(arbol);

        Optional<Arbol> arbolObtenido = arbolService.get(arbolCreado.getId());

        assertTrue(arbolObtenido.isPresent());
        assertEquals(arbolCreado.getId(), arbolObtenido.get().getId());
        assertEquals(arbolCreado.getAltura(), arbolObtenido.get().getAltura());
    }

    @Test
    void testGetAll() {
        arbolRepository.deleteAll();

        Arbol arbol1 = new Arbol();
        arbol1.setAltura(10.0);
        arbol1.setCaducifolio(true);
        arbolService.create(arbol1);

        Arbol arbol2 = new Arbol();
        arbol2.setAltura(20.0);
        arbol2.setCaducifolio(false);
        arbolService.create(arbol2);

        List<Arbol> arboles = arbolService.getAll();

        assertEquals(2, arboles.size());
        assertTrue(arboles.stream().anyMatch(a -> a.getAltura() == 10.0));
        assertTrue(arboles.stream().anyMatch(a -> a.getAltura() == 20.0));
    }

    @Test
    void testUpdate() {
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        Arbol arbolCreado = arbolService.create(arbol);

        arbolCreado.setAltura(25.0);
        arbolService.update(arbolCreado);

        Optional<Arbol> arbolActualizado = arbolService.get(arbolCreado.getId());

        assertTrue(arbolActualizado.isPresent());
        assertEquals(25.0, arbolActualizado.get().getAltura());
    }

    @Test
    void testDelete() {
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        Arbol arbolCreado = arbolService.create(arbol);

        arbolService.delete(arbolCreado.getId());

        Optional<Arbol> arbolBorrado = arbolService.get(arbolCreado.getId());

        assertFalse(arbolBorrado.isPresent());
    }
}
