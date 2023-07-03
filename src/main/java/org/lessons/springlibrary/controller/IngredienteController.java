package org.lessons.springlibrary.controller;

import jakarta.validation.Valid;
import org.lessons.springlibrary.model.Ingredienti;
import org.lessons.springlibrary.model.Pizza;
import org.lessons.springlibrary.repository.IngredientiRepository;
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
@RequestMapping("/ingrediente")
public class IngredienteController {

    @Autowired
    private IngredientiRepository ingredientiRepository;

    @GetMapping
    public String index(Model model, @RequestParam("edit") Optional<Integer> ingredientiId) {
        // recupero da db tutte le categorie
        List<Ingredienti> ingredientiList = ingredientiRepository.findAll();
        // passo al model un attributo categories con tutte le categorie
        model.addAttribute("ingredienti", ingredientiList);

        Ingredienti ingredientiObj;
        // se ho il parametro categoryId allora cerco la categoria su database
        if (ingredientiId.isPresent()) {
            Optional<Ingredienti> ingredientiDb = ingredientiRepository.findById(ingredientiId.get());
            // se è presente valorizzo ingredientiObj con la categoria da db
            if (ingredientiDb.isPresent()) {
                ingredientiObj = ingredientiDb.get();
            } else {
                // se non è presente valorizzo categoryObj con una categoria vuota
                ingredientiObj = new Ingredienti();
            }
        } else {
            // se non ho il parametro categoryObj con una categoria vuota
            ingredientiObj = new Ingredienti();
        }
        // passo al model un attributo categoryObj per mappare il form su un oggetto di tipo Category
        model.addAttribute("ingredientiObj", ingredientiObj);
        return "/ingrediente/index";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("ingredientiObj") Ingredienti formIngredienti,
                       BindingResult bindingResult, Model model) {
        // verfichiamo se ci sono errori
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredientiRepository.findAll());
            return "/ingrediente/index";
        }
        // salvare la categoria
        ingredientiRepository.save(formIngredienti);
        // fa la redirect alla index
        return "redirect:/ingrediente";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // prima di eliminare la categoria la dissocio da tutti i libri
        Optional<Ingredienti> result = ingredientiRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // categoria che devo eliminare
        Ingredienti ingredientiToDelete = result.get();
        // per ogni libro associato alla categoria da eliminare
        for (Pizza pizza : ingredientiToDelete.getPizza()) {
            pizza.getIngredienti().remove(ingredientiToDelete);
        }

        ingredientiRepository.deleteById(id);
        return "redirect:/ingrediente";
    }
}