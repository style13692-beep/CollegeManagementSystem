package com.cms.model;

public class Unit {
    private String unitCode;
    private String unitName;
    private int credits;
    private String description;
    private String prerequisites;

    public Unit(String unitCode, String unitName, int credits, String description, String prerequisites) {
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.credits = credits;
        this.description = description;
        this.prerequisites = prerequisites;
    }

    public String getUnitCode() { return unitCode; }
    public String getUnitName() { return unitName; }
    public int getCredits() { return credits; }
    public String getDescription() { return description; }
    public String getPrerequisites() { return prerequisites; }

    public void setUnitCode(String unitCode) { this.unitCode = unitCode; }
    public void setUnitName(String unitName) { this.unitName = unitName; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setDescription(String description) { this.description = description; }
    public void setPrerequisites(String prerequisites) { this.prerequisites = prerequisites; }

    @Override
    public String toString() {
        return unitCode + "," + unitName + "," + credits + "," + description + "," + prerequisites;
    }

    public static Unit fromString(String line) {
        String[] parts = line.split(",");
        return new Unit(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4]);
    }
}