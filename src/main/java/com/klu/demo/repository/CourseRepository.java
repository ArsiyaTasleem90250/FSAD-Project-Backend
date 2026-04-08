package com.klu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.demo.entity.Course;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByTitleIgnoreCase(String title);
}
