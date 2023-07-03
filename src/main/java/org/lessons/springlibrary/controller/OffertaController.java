package org.lessons.springlibrary.controller;

import jakarta.validation.Valid;
import org.lessons.springlibrary.model.Offerta;
import org.lessons.springlibrary.model.Pizza;
import org.lessons.springlibrary.repository.OffertaRepository;
import org.lessons.springlibrary.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("offerta") Offerta formOfferta,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "/offerte/form";
        }
        offertaRepository.save(formOfferta);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Offerta offerta = getOffertaById(id);
        model.addAttribute("offerta", offerta);
        return "/offerta/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("offerta") Offerta formOfferta,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        Offerta offertaToEdit = getOffertaById(id);
        if (bindingResult.hasErrors()) {
            return "/offerta/edit";
        }
        formOfferta.setId(offertaToEdit.getId());
        offertaRepository.save(formOfferta);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Offerta offertaToDelete = getOffertaById(id);
        offertaRepository.delete(offertaToDelete);
        return "redirect:/";
    }

    private Offerta getOffertaById(Integer id) {
        Optional<Offerta> result = offertaRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "offerta con id " + id + " non trovato");
        }
        return result.get();
    }
}
