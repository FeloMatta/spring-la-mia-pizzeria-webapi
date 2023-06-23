package org.lessons.springLaMiaPizzeriaCrud.controller;

import org.lessons.springLaMiaPizzeriaCrud.model.Pizza;
import org.lessons.springLaMiaPizzeriaCrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    // dipende da PizzaRepository
    @Autowired
    private PizzaRepository pizzaRepository;


   /* @GetMapping
    public String list(Model model) {
        List<Pizza> pizze = pizzaRepository.findAll();
        model.addAttribute("listaPizze", pizze);
        return "/Pizze/list";
    }*/

    @GetMapping
    public String list(
            @RequestParam(name = "keyword", required = false) String searchString,
            Model model) { // Model è la mappa di attributi che il controller passa alla view
        List<Pizza> pizze;

        if (searchString == null || searchString.isBlank()) {
            // se non ho il parametro searchString faccio la query generica
            // recupero la lista delle pizze dal database
            pizze = pizzaRepository.findAll();
        } else {
            // se ho il parametro searchString faccio la query con filtro
            // pizze = pizzaRepository.findByTitle(searchString);
            pizze = pizzaRepository.findByNomeContainingIgnoreCase(
                    searchString);
        }

        // passo la lista delle pizze alla view
        model.addAttribute("listaPizze", pizze);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        // restituisco il nome del template della view
        return "/Pizze/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer pizzaId, Model model) {
        // cerca su database i dettagli della pizza con quell'id
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            // passa la pizza alla view
            model.addAttribute("pizza", result.get());
            // ritorna il nome del template della view
            return "/pizze/detail";
        } else {
            // ritorno un HTTP status 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
    }
}
