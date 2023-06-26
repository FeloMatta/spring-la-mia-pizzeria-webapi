package org.lessons.springlibrary.controller;

import jakarta.validation.Valid;
import org.lessons.springlibrary.model.Pizza;
import org.lessons.springlibrary.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PizzaController {


    @Autowired
    private PizzaRepository pizzaRepository;

//    @GetMapping
//    public String list(Model model) {
//        List<Pizza> pizza = pizzaRepository.findAll();
//        model.addAttribute("pizza", pizza);
//        return "/pizza";
//    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Integer pizzaId, Model model) {

        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Pizza> pizza;

        if (searchString == null || searchString.isBlank()) {
            pizza = pizzaRepository.findAll();
        } else {
            pizza = pizzaRepository.findByNome(searchString);
        }
        model.addAttribute("pizzaList", pizza);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        return "/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/create";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/";
    }


}