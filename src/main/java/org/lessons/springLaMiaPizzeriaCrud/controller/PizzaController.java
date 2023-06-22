package org.lessons.springLaMiaPizzeriaCrud.controller;

import org.lessons.springLaMiaPizzeriaCrud.model.Pizza;
import org.lessons.springLaMiaPizzeriaCrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Pizze")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String list(Model model) {
        List<Pizza> pizze = pizzaRepository.findAll();
        model.addAttribute("listaPizze", pizze);
        return "/Pizze/list";
    }
}
