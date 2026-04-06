package com.klu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.demo.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}