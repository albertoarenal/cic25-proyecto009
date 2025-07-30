package es.cic.curso25.proy009.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.repository.RamaRepository;

@Service
@Transactional
public class RamaService {

    @Autowired
    private RamaRepository ramaRepository;

    @Transactional(readOnly = true)
    public Optional<Rama> get(Long id){

        Optional<Rama> rama = ramaRepository.findById(id);


        return rama;

    }

    @Transactional(readOnly = true)
    public List<Rama> get(){

        List<Rama> ramas = ramaRepository.findAll();


        return ramas;

    }

    public Rama create(Rama rama){

        ramaRepository.save(rama);

        return rama;

    }

    public Rama update(Rama rama){

        ramaRepository.save(rama);

        return rama;
    }

    public void delete(Long id){

        ramaRepository.deleteById(id);;
  
    }

}
