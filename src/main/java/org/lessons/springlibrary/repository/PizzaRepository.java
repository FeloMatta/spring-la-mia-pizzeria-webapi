package org.lessons.springlibrary.repository;

import org.lessons.springlibrary.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    List<Pizza> findByNome(String nome);

}