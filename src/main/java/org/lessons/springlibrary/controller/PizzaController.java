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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Pizza pizza = getPizzaById(id);
        model.addAttribute("pizza", pizza);
        return "/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        Pizza pizzaToEdit = getPizzaById(id);
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        formPizza.setId(pizzaToEdit.getId());
        pizzaRepository.save(formPizza);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Pizza pizzaToDelete = getPizzaById(id);
        pizzaRepository.delete(pizzaToDelete);
        return "redirect:/";
    }


    private Pizza getPizzaById(Integer id) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza con id " + id + " non trovato");
        }
        return result.get();
    }

}