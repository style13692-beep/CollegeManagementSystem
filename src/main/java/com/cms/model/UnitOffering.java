package com.cms.model;

public class UnitOffering {
    private String offeringId;
    private String unitCode;
    private String semester;
    private String year;
    private String instructorId;

    public UnitOffering(String offeringId, String unitCode, String semester, String year, String instructorId) {
        this.offeringId = offeringId;
        this.unitCode = unitCode;
        this.semester = semester;
        this.year = year;
        this.instructorId = instructorId;
    }

    public String getOfferingId() { return offeringId; }
    public String getUnitCode() { return unitCode; }
    public String getSemester() { return semester; }
    public String getYear() { return year; }
    public String getInstructorId() { return instructorId; }

    public void setOfferingId(String offeringId) { this.offeringId = offeringId; }
    public void setUnitCode(String unitCode) { this.unitCode = unitCode; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setYear(String year) { this.year = year; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    @Override
    public String toString() {
        return offeringId + "," + unitCode + "," + semester + "," + year + "," + instructorId;
    }

    public static UnitOffering fromString(String line) {
        String[] parts = line.split(",");
        return new UnitOffering(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }
}