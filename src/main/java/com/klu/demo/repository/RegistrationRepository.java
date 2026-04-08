package com.klu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.demo.entity.Registration;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByEmailIgnoreCase(String email);
    void deleteByEmailIgnoreCase(String email);
}
