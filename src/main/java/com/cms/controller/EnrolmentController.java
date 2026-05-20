package com.cms.controller;

import com.cms.model.Enrolment;
import java.io.*;
import java.util.ArrayList;

public class EnrolmentController {
    private ArrayList<Enrolment> enrolments = new ArrayList<>();
    private static final String FILE_NAME = "enrolments.txt";

    public EnrolmentController() {
        loadFromFile();
    }

    public void addEnrolment(Enrolment enrolment) {
        enrolments.add(enrolment);
        saveToFile();
    }

    public ArrayList<Enrolment> getAllEnrolments() {
        return enrolments;
    }

    public ArrayList<Enrolment> findByStudent(String studentId) {
        ArrayList<Enrolment> result = new ArrayList<>();
        for (Enrolment e : enrolments) {
            if (e.getStudentId().equalsIgnoreCase(studentId)) {
                result.add(e);
            }
        }
        return result;
    }

    public ArrayList<Enrolment> findByOffering(String offeringId) {
        ArrayList<Enrolment> result = new ArrayList<>();
        for (Enrolment e : enrolments) {
            if (e.getOfferingId().equalsIgnoreCase(offeringId)) {
                result.add(e);
            }
        }
        return result;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Enrolment e : enrolments) {
                writer.write(e.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving enrolments: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                enrolments.add(Enrolment.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading enrolments: " + e.getMessage());
        }
    }
}