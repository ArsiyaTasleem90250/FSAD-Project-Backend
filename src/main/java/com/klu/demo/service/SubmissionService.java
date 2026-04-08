package com.klu.demo.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import com.klu.demo.entity.Course;
import com.klu.demo.entity.Submission;
import com.klu.demo.repository.CourseRepository;
import com.klu.demo.repository.SubmissionRepository;

@Service
public class SubmissionService {
    @Autowired private SubmissionRepository repo;
    @Autowired private CourseRepository courseRepo;

    private String genCourseId(String course){
        String c = course==null ? "" : course.trim().toLowerCase();
        int hash = Math.abs(c.hashCode());
        return String.format("%04d", (hash % 9000) + 1000);
    }

    private Integer clampMarks(Integer marks){
        if(marks == null) return null;
        return Math.max(1, Math.min(100, marks));
    }

    private Course ensureCourseExists(String courseTitle){
        if(courseTitle==null || courseTitle.isBlank()) return null;
        String normalized = courseTitle.trim();
        return courseRepo.findByTitleIgnoreCase(normalized)
                  .orElseGet(() -> {
                      Course c = new Course();
                      c.setTitle(normalized);
                      return courseRepo.save(c);
                  });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void seedCoursesOnReady(){
        repo.findAll().forEach(s -> ensureCourseExists(s.getCourse()));
    }

    @Transactional
    public Submission add(Submission s){
        s.setId(null);
        String title = s.getCourse()==null ? "" : s.getCourse().trim();
        Course c = ensureCourseExists(title);
        s.setCourse(c!=null ? c.getTitle() : title);
        s.setCourseId(genCourseId(title));
        s.setMarks(clampMarks(s.getMarks()));
        if(s.getSubmittedAt()==null) s.setSubmittedAt(Instant.now());
        return repo.save(s);
    }
    public List<Submission> all(){ return repo.findAll(); }

    @Transactional
    public Submission update(Long id, Submission in){
        return repo.findById(id).map(ex -> {
            String title = in.getCourse()==null ? "" : in.getCourse().trim();
            Course c = ensureCourseExists(title);
            ex.setCourse(c!=null ? c.getTitle() : title);
            ex.setCourseId(genCourseId(title));
            ex.setMarks(clampMarks(in.getMarks()));
            ex.setStudentName(in.getStudentName());
            ex.setStudentEmail(in.getStudentEmail());
            ex.setDepartment(in.getDepartment());
            ex.setIdNumber(in.getIdNumber());
            ex.setFileName(in.getFileName());
            if(in.getFileData()!=null) ex.setFileData(in.getFileData());
            ex.setMarks(in.getMarks());
            ex.setGradedBy(in.getGradedBy());
            ex.setSubmittedAt(in.getSubmittedAt()!=null ? in.getSubmittedAt() : ex.getSubmittedAt());
            ex.setMarkedAt(in.getMarkedAt());
            return repo.save(ex);
        }).orElseThrow(() -> new IllegalArgumentException("Submission not found"));
    }

    public void delete(Long id){ repo.deleteById(id); }
}
