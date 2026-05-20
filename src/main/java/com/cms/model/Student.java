package com.cms.model;

public class Student {
    private String studentId;
    private String name;
    private String email;
    private String enrollmentYear;

    public Student(String studentId, String name, String email, String enrollmentYear) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.enrollmentYear = enrollmentYear;
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getEnrollmentYear() { return enrollmentYear; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setEnrollmentYear(String enrollmentYear) { this.enrollmentYear = enrollmentYear; }

    @Override
    public String toString() {
        return studentId + "," + name + "," + email + "," + enrollmentYear;
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        return new Student(parts[0], parts[1], parts[2], parts[3]);
    }
}