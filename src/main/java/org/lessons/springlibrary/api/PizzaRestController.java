package org.lessons.springlibrary.api;

import jakarta.validation.Valid;
import org.lessons.springlibrary.model.Pizza;
import org.lessons.springlibrary.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pizze")
public class PizzaRestController {

    // controller per la risorsa Pizza

    @Autowired
    private PizzaRepository pizzaRepository;

    // Servizio per avere la lista delle pizze
    @GetMapping
    public List<Pizza> index() {
        // restituisco la lista di tutte le pizze prese da  database
        return pizzaRepository.findAll(Sort.by("nome"));
    }

    // servizio per avere il dettaglio del singolo libro
    @GetMapping("/{id}")
    public Pizza get(@PathVariable Integer id) {
        // cerco il libro per id su database
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()) {
            return pizza.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // servizio per creare una nuova pizza, che arriva come JSON nel request Body
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    // servizio per cancellare una pizza
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @RequestBody Pizza pizza) {
        pizza.setId(id);
        return pizzaRepository.save(pizza);
    }
}
