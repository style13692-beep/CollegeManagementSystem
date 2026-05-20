package com.cms.model;

public class Instructor {
    private String instructorId;
    private String name;
    private String email;
    private String department;

    public Instructor(String instructorId, String name, String email, String department) {
        this.instructorId = instructorId;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public String getInstructorId() { return instructorId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }

    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return instructorId + "," + name + "," + email + "," + department;
    }

    public static Instructor fromString(String line) {
        String[] parts = line.split(",");
        return new Instructor(parts[0], parts[1], parts[2], parts[3]);
    }
}