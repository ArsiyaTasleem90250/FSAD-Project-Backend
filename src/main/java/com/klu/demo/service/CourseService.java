package com.klu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.demo.entity.Course;
import com.klu.demo.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(Course course) {
        if(course==null || course.getTitle()==null || course.getTitle().isBlank()){
            throw new IllegalArgumentException("Course title is required");
        }
        String title = course.getTitle().trim();
        // avoid duplicates by title (case-insensitive)
        return courseRepository.findByTitleIgnoreCase(title)
                .orElseGet(() -> {
                    course.setTitle(title);
                    return courseRepository.save(course);
                });
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
