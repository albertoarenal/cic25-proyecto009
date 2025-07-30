package es.cic.curso25.proy009.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.service.ArbolService;

@RestController
@RequestMapping("/api/arbol")
public class ArbolController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArbolController.class);

    @Autowired
    private ArbolService arbolService;


    @GetMapping("/{id}")
    public Optional<Arbol> get(@PathVariable Long id){
       
       Optional<Arbol> arbol = arbolService.get(id);

       return arbol;

        
    }
    @GetMapping()
    public List<Arbol> getAll(){
        
        return arbolService.getAll();
    }
    
    @PostMapping("/guardar")
    public Arbol create(@RequestBody Arbol arbol){

        arbolService.create(arbol);

        return arbol;

    }

    @PutMapping()
     public Arbol update(@RequestBody Arbol arbol){

        arbolService.update(arbol);

        return arbol;
     }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        arbolService.delete(id);

    }
    

}
