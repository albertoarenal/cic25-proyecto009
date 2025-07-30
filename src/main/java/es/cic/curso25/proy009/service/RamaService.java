package es.cic.curso25.proy009.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.repository.ArbolRepository;
import es.cic.curso25.proy009.repository.RamaRepository;

@Service
@Transactional
public class RamaService {

    @Autowired
    private RamaRepository ramaRepository;

    @Autowired
    private ArbolRepository arbolRepository;

    @Transactional(readOnly = true)
    public Optional<Rama> get(Long id) {

        Optional<Rama> rama = ramaRepository.findById(id);

        return rama;

    }

    @Transactional(readOnly = true)
    public List<Rama> get() {

        List<Rama> ramas = ramaRepository.findAll();

        return ramas;

    }

    public Rama create(Rama rama) {

        ramaRepository.save(rama);

        return rama;

    }

    public Rama create(Long idArbol, Rama rama) {

        Optional<Arbol> arbol = arbolRepository.findById(idArbol);
        rama.setArbol(arbol.get());
        ramaRepository.save(rama);
        arbol.get().getRamas().add(rama);
        arbolRepository.save(arbol.get());

        return rama;
    }

    public Rama update(Rama rama) {

        Rama existente = ramaRepository.findById(rama.getId())
                .orElseThrow(() -> new RuntimeException("Rama no encontrada"));

        // Actualizar campos que pueden cambiar
        existente.setColorHojas(rama.getColorHojas());
        existente.setFormaHojas(rama.getFormaHojas());

        // Reasignar el árbol desde la base de datos (evita el error)
        Long arbolId = rama.getArbol().getId();
        Arbol arbol = arbolRepository.findById(arbolId)
                .orElseThrow(() -> new RuntimeException("Árbol no encontrado"));
        existente.setArbol(arbol);

        ramaRepository.save(existente);

        return existente;
    }

    public void delete(Long id) {

        ramaRepository.deleteById(id);
        ;

    }

}
