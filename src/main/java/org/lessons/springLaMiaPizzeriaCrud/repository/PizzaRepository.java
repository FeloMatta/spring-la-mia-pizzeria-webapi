package org.lessons.springLaMiaPizzeriaCrud.repository;

import org.lessons.springLaMiaPizzeriaCrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    // metodo per cercare le pizze con nome uguale a quello passato
    List<Pizza> findByNome(String nome);

    // metodo per cercare le pizze il cui nome o descrizione contiene una stringa
    List<Pizza> findByNomeContainingIgnoreCase(String nome);

}
