package org.lessons.springlibrary.controller;

import org.lessons.springlibrary.model.Offerta;
import org.lessons.springlibrary.model.Pizza;
import org.lessons.springlibrary.repository.OffertaRepository;
import org.lessons.springlibrary.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offerte")
public class OffertaController {
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    OffertaRepository offertaRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        //  creo nuova offerta
        Offerta offerta = new Offerta();
        // precarico la data della offerta con la data odierna
        offerta.setInizioOfferta(LocalDate.now());
        // precarico la pizza con quella passata come paramentro
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (pizza.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + pizzaId + " not found");
        }
        offerta.setPizza(pizza.get());
        //  aggiungo al model l'attributo con l'offerta
        model.addAttribute("offerta", offerta);
        return "/offerte/form";
    }
}
