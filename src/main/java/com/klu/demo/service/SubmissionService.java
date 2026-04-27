package com.klu.demo.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klu.demo.entity.Course;
import com.klu.demo.entity.Submission;
import com.klu.demo.repository.CourseRepository;
import com.klu.demo.repository.SubmissionRepository;

@Service
public class SubmissionService {
    @Autowired private SubmissionRepository repo;
    @Autowired private CourseRepository courseRepo;

    private String genCourseId(String course) {
        String c = course == null ? "" : course.trim().toLowerCase();
        int hash = Math.abs(c.hashCode());
        return String.format("%04d", (hash % 9000) + 1000);
    }

    private Integer clampMarks(Integer marks) {
        if (marks == null) return null;
        return Math.max(0, Math.min(100, marks));
    }

    private Course ensureCourseExists(String courseTitle) {
        if (courseTitle == null || courseTitle.isBlank()) return null;
        String normalized = courseTitle.trim();
        return courseRepo.findByTitleIgnoreCase(normalized)
                .orElseGet(() -> {
                    Course c = new Course();
                    c.setTitle(normalized);
                    return courseRepo.save(c);
                });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void seedCoursesOnReady() {
        repo.findAll().forEach(s -> ensureCourseExists(s.getCourse()));
    }

    @Transactional
    public Submission add(Submission submission) {
        submission.setId(null);
        String title = submission.getCourse() == null ? "" : submission.getCourse().trim();
        Course course = ensureCourseExists(title);
        submission.setCourse(course != null ? course.getTitle() : title);
        submission.setCourseId(genCourseId(title));
        submission.setMarks(clampMarks(submission.getMarks()));
        if (submission.getSubmittedAt() == null) submission.setSubmittedAt(Instant.now());
        return repo.save(submission);
    }

    public List<Submission> all() {
        return repo.findAll();
    }

    @Transactional
    public Submission update(Long id, Submission incoming) {
        return repo.findById(id).map(existing -> {
            String title = incoming.getCourse() == null ? existing.getCourse() : incoming.getCourse().trim();
            Course course = ensureCourseExists(title);
            existing.setCourse(course != null ? course.getTitle() : title);
            existing.setCourseId(genCourseId(title));
            existing.setStudentName(incoming.getStudentName());
            existing.setStudentEmail(incoming.getStudentEmail());
            existing.setDepartment(incoming.getDepartment());
            existing.setIdNumber(incoming.getIdNumber());
            existing.setFileName(incoming.getFileName());
            if (incoming.getFileData() != null) existing.setFileData(incoming.getFileData());
            existing.setMarks(clampMarks(incoming.getMarks()));
            existing.setGradedBy(incoming.getGradedBy());
            existing.setSubmittedAt(incoming.getSubmittedAt() != null ? incoming.getSubmittedAt() : existing.getSubmittedAt());
            existing.setMarkedAt(incoming.getMarkedAt());
            return repo.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Submission not found"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
