package org.lessons.springLaMiaPizzeriaCrud.repository;

import org.lessons.springLaMiaPizzeriaCrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

}
