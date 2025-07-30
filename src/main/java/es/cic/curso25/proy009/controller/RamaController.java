package es.cic.curso25.proy009.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.service.RamaService;

@RestController
@RequestMapping("/api/rama")
public class RamaController {

    @Autowired
    private RamaService ramaService;

    @GetMapping("/{id}")
    public Optional<Rama> get(@PathVariable Long id) {

        Optional<Rama> rama = ramaService.get(id);

        return rama;
    }

    @GetMapping()
    public List<Rama> getAll() {

        return ramaService.get();
    }

    @PostMapping("{idArbol}")
    public Rama create(@PathVariable Long idArbol, @RequestBody Rama rama) {

        ramaService.create(idArbol, rama);

        return rama;
    }

    @PutMapping()
    public void update(@RequestBody Rama rama) {

        ramaService.update(rama);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ramaService.delete(id);
    }
}
