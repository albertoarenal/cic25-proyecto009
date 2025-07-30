package es.cic.curso25.proy009.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy009.controller.ArbolController;
import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.repository.ArbolRepository;

@Service
@Transactional
public class ArbolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArbolService.class);

    @Autowired
    private ArbolRepository arbolRepository;

    @Transactional(readOnly = true)
    public Optional<Arbol> get(Long id) {

        Optional<Arbol> arbol = arbolRepository.findById(id);

        return arbol;
    }

    @Transactional(readOnly = true)
    public List<Arbol> getAll() {

        List<Arbol> arboles = arbolRepository.findAll();

        return arboles;
    }

    public Arbol create(Arbol arbol) {

        arbolRepository.save(arbol);

        return arbol;
    }

    public Arbol update(Arbol arbol) {

        arbolRepository.save(arbol);

        return arbol;
    }

    public void delete(Long id) {

        arbolRepository.deleteById(id);
    }
}
