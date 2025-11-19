package com.geno.springGateway.user.infraestructure.repository;

import com.geno.springGateway.user.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByName(String rolName);
}
