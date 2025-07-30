package es.cic.curso25.proy009.service;


import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.repository.ArbolRepository;
import es.cic.curso25.proy009.repository.RamaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class RamaServiceIntegrationTest {

    @Autowired
    private RamaService ramaService;

    @Autowired
    private RamaRepository ramaRepository;

    @Autowired
    private ArbolRepository arbolRepository;

    @Test
    void testCreateRama() {
        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        Rama rama = new Rama();
        rama.setColorHojas("verde");
        rama.setFormaHojas("ovalada");
        rama.setPodrida(false);

        Rama ramaCreada = ramaService.create(arbol.getId(), rama);

        assertTrue(ramaCreada.getId() > 0);
        Optional<Rama> ramaEnBd = ramaRepository.findById(ramaCreada.getId());
        assertTrue(ramaEnBd.isPresent());
        assertEquals(ramaCreada.getColorHojas(), ramaEnBd.get().getColorHojas());
        assertNotNull(ramaEnBd.get().getArbol());
        assertEquals(arbol.getId(), ramaEnBd.get().getArbol().getId());
    }

    @Test
    void testGetRama() {
        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        Rama rama = new Rama();
        rama.setColorHojas("rojo");
        rama.setFormaHojas("lobulada");
        rama.setPodrida(true);
        rama.setArbol(arbol);
        rama = ramaRepository.save(rama);

        Optional<Rama> ramaObtenida = ramaService.get(rama.getId());

        assertTrue(ramaObtenida.isPresent());
        assertEquals(rama.getId(), ramaObtenida.get().getId());
        assertEquals(rama.getColorHojas(), ramaObtenida.get().getColorHojas());
    }

    @Test
    void testGetAllRamas() {
        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        Rama rama1 = new Rama();
        rama1.setColorHojas("azul");
        rama1.setArbol(arbol);
        ramaService.create(arbol.getId(), rama1);

        Rama rama2 = new Rama();
        rama2.setColorHojas("amarillo");
        rama2.setArbol(arbol);
        ramaService.create(arbol.getId(), rama2);

        List<Rama> ramas = ramaService.get();

        assertEquals(2, ramas.size());
        assertTrue(ramas.stream().anyMatch(r -> "azul".equals(r.getColorHojas())));
        assertTrue(ramas.stream().anyMatch(r -> "amarillo".equals(r.getColorHojas())));
    }

    @Test
    void testUpdateRama() {
        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        Rama rama = new Rama();
        rama.setColorHojas("original");
        rama.setArbol(arbol);
        rama = ramaRepository.save(rama);

        rama.setColorHojas("modificado");
        ramaService.update(rama);

        Optional<Rama> ramaActualizada = ramaRepository.findById(rama.getId());
        assertTrue(ramaActualizada.isPresent());
        assertEquals("modificado", ramaActualizada.get().getColorHojas());
    }

    @Test
    void testDeleteRama() {
        ramaRepository.deleteAll();
        arbolRepository.deleteAll();

        Arbol arbol = new Arbol();
        arbol.setAltura(10.0);
        arbol.setCaducifolio(true);
        arbol = arbolRepository.save(arbol);

        Rama rama = new Rama();
        rama.setColorHojas("a borrar");
        rama.setArbol(arbol);
        rama = ramaRepository.save(rama);

        ramaService.delete(rama.getId());

        Optional<Rama> ramaBorrada = ramaRepository.findById(rama.getId());
        assertFalse(ramaBorrada.isPresent());
    }
}
