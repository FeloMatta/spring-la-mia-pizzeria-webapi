package org.lessons.springlibrary.repository;

import org.lessons.springlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // metodo che recupera uno User a partire dall'email (che sarebbe  poi lo username)
    Optional<User> findByEmail(String email);
}
