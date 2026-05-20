package com.cms.controller;

import com.cms.model.UnitOffering;
import java.io.*;
import java.util.ArrayList;

public class UnitOfferingController {
    private ArrayList<UnitOffering> offerings = new ArrayList<>();
    private static final String FILE_NAME = "offerings.txt";

    public UnitOfferingController() {
        loadFromFile();
    }

    public void addOffering(UnitOffering offering) {
        offerings.add(offering);
        saveToFile();
    }

    public ArrayList<UnitOffering> getAllOfferings() {
        return offerings;
    }

    public UnitOffering findById(String id) {
        for (UnitOffering o : offerings) {
            if (o.getOfferingId().equalsIgnoreCase(id)) {
                return o;
            }
        }
        return null;
    }

    public ArrayList<UnitOffering> findByUnitCode(String unitCode) {
        ArrayList<UnitOffering> result = new ArrayList<>();
        for (UnitOffering o : offerings) {
            if (o.getUnitCode().equalsIgnoreCase(unitCode)) {
                result.add(o);
            }
        }
        return result;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (UnitOffering o : offerings) {
                writer.write(o.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving offerings: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                offerings.add(UnitOffering.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading offerings: " + e.getMessage());
        }
    }
}