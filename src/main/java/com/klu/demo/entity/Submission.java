package com.klu.demo.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String course;
    private String courseId;          // 4‑digit stable code
    private String studentName;
    private String idNumber;
    private String fileName;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String fileData;

    private Integer marks;
    private String gradedBy;
    private Instant submittedAt;
    private Instant markedAt;

    public Submission() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileData() { return fileData; }
    public void setFileData(String fileData) { this.fileData = fileData; }
    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }
    public String getGradedBy() { return gradedBy; }
    public void setGradedBy(String gradedBy) { this.gradedBy = gradedBy; }
    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
    public Instant getMarkedAt() { return markedAt; }
    public void setMarkedAt(Instant markedAt) { this.markedAt = markedAt; }

	public Object getStudentEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDepartment() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStudentEmail(Object studentEmail) {
		// TODO Auto-generated method stub
		
	}

	public void setDepartment(Object department) {
		// TODO Auto-generated method stub
		
	}
}
