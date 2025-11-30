package com.geno.springGateway.user.infraestructure.repository;

import com.geno.springGateway.user.entities.Rol;
import com.geno.springGateway.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
