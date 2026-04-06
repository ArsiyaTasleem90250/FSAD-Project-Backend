package com.klu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klu.demo.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}