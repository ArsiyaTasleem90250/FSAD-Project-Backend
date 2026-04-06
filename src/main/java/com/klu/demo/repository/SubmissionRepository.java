package com.klu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.demo.entity.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}