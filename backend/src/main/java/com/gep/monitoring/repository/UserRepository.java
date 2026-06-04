package com.gep.monitoring.repository;

import com.gep.monitoring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Une méthode magique Spring Data : on l'écrit juste, et Spring code la requête SQL derrière !
    User findByEmail(String email);
}