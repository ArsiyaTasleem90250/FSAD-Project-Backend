
package com.klu.demo.entity;

import jakarta.persistence.*;

@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String fileUrl;

    private Long studentId;
    private Long courseId;

    private String status;
    private Integer marks;
    private String feedback;

    // Constructors
    public Submission() {}

    public Submission(Long id, String title, String fileUrl, Long studentId, Long courseId,
                      String status, Integer marks, String feedback) {
        this.id = id;
        this.title = title;
        this.fileUrl = fileUrl;
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
        this.marks = marks;
        this.feedback = feedback;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
