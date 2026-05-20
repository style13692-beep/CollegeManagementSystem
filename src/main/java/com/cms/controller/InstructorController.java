package com.cms.controller;

import com.cms.model.Instructor;
import java.io.*;
import java.util.ArrayList;

public class InstructorController {
    private ArrayList<Instructor> instructors = new ArrayList<>();
    private static final String FILE_NAME = "instructors.txt";

    public InstructorController() {
        loadFromFile();
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
        saveToFile();
    }

    public ArrayList<Instructor> getAllInstructors() {
        return instructors;
    }

    public Instructor findById(String id) {
        for (Instructor i : instructors) {
            if (i.getInstructorId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return null;
    }

    // Linear search by name
    public Instructor searchByName(String name) {
        for (Instructor i : instructors) {
            if (i.getName().toLowerCase().contains(name.toLowerCase())) {
                return i;
            }
        }
        return null;
    }

    // Bubble sort by name
    public void sortByName() {
        int n = instructors.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (instructors.get(j).getName().compareTo(instructors.get(j + 1).getName()) > 0) {
                    Instructor temp = instructors.get(j);
                    instructors.set(j, instructors.get(j + 1));
                    instructors.set(j + 1, temp);
                }
            }
        }
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Instructor i : instructors) {
                writer.write(i.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving instructors: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                instructors.add(Instructor.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading instructors: " + e.getMessage());
        }
    }
}