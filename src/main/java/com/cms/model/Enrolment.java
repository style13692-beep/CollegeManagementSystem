package com.cms.model;

public class Enrolment {
    private String enrolmentId;
    private String studentId;
    private String offeringId;
    private String enrolmentDate;
    private String status;

    public Enrolment(String enrolmentId, String studentId, String offeringId, String enrolmentDate, String status) {
        this.enrolmentId = enrolmentId;
        this.studentId = studentId;
        this.offeringId = offeringId;
        this.enrolmentDate = enrolmentDate;
        this.status = status;
    }

    public String getEnrolmentId() { return enrolmentId; }
    public String getStudentId() { return studentId; }
    public String getOfferingId() { return offeringId; }
    public String getEnrolmentDate() { return enrolmentDate; }
    public String getStatus() { return status; }

    public void setEnrolmentId(String enrolmentId) { this.enrolmentId = enrolmentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setOfferingId(String offeringId) { this.offeringId = offeringId; }
    public void setEnrolmentDate(String enrolmentDate) { this.enrolmentDate = enrolmentDate; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return enrolmentId + "," + studentId + "," + offeringId + "," + enrolmentDate + "," + status;
    }

    public static Enrolment fromString(String line) {
        String[] parts = line.split(",");
        return new Enrolment(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }
}