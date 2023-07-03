package org.lessons.springlibrary.repository;

import org.lessons.springlibrary.model.Ingredienti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientiRepository extends JpaRepository<Ingredienti, Integer> {
}
