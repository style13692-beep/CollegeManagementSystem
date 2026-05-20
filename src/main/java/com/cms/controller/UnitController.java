package com.cms.controller;

import com.cms.model.Unit;
import java.io.*;
import java.util.ArrayList;

public class UnitController {
    private ArrayList<Unit> units = new ArrayList<>();
    private static final String FILE_NAME = "units.txt";

    public UnitController() {
        loadFromFile();
    }

    public void addUnit(Unit unit) {
        units.add(unit);
        saveToFile();
    }

    public ArrayList<Unit> getAllUnits() {
        return units;
    }

    public Unit findByCode(String code) {
        for (Unit u : units) {
            if (u.getUnitCode().equalsIgnoreCase(code)) {
                return u;
            }
        }
        return null;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Unit u : units) {
                writer.write(u.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving units: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                units.add(Unit.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading units: " + e.getMessage());
        }
    }

    // Linear search by unit name
    public Unit searchByName(String name) {
        for (Unit u : units) {
            if (u.getUnitName().toLowerCase().contains(name.toLowerCase())) {
                return u;
            }
        }
        return null;
    }

    // Bubble sort units by unit code
    public void sortByCode() {
        int n = units.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (units.get(j).getUnitCode().compareTo(units.get(j + 1).getUnitCode()) > 0) {
                    Unit temp = units.get(j);
                    units.set(j, units.get(j + 1));
                    units.set(j + 1, temp);
                }
            }
        }
    }
}