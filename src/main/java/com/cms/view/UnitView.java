package com.cms.view;

import com.cms.controller.UnitController;
import com.cms.model.Unit;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UnitView extends JFrame {
    private UnitController controller = new UnitController();
    private JTextField codeField, nameField, creditsField, descField, prereqField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public UnitView() {
        setTitle("Manage Units");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Unit"));

        codeField = new JTextField();
        nameField = new JTextField();
        creditsField = new JTextField();
        descField = new JTextField();
        prereqField = new JTextField();

        formPanel.add(new JLabel("Unit Code:")); formPanel.add(codeField);
        formPanel.add(new JLabel("Unit Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Credits:"));   formPanel.add(creditsField);
        formPanel.add(new JLabel("Description:")); formPanel.add(descField);
        formPanel.add(new JLabel("Prerequisites:")); formPanel.add(prereqField);

        JButton addBtn = new JButton("Add Unit");
        formPanel.add(new JLabel()); formPanel.add(addBtn);

        add(formPanel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton sortBtn = new JButton("Sort by Code");
        searchPanel.add(new JLabel("Search by name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(sortBtn);
        add(searchPanel, BorderLayout.CENTER);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Code", "Name", "Credits", "Description", "Prerequisites"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        // Actions
        addBtn.addActionListener(e -> addUnit());
        searchBtn.addActionListener(e -> searchUnit());
        sortBtn.addActionListener(e -> { controller.sortByCode(); refreshTable(); });

        refreshTable();
    }

    private void addUnit() {
        try {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            int credits = Integer.parseInt(creditsField.getText().trim());
            String desc = descField.getText().trim();
            String prereq = prereqField.getText().trim();

            if (code.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Code and Name are required.");
                return;
            }

            controller.addUnit(new Unit(code, name, credits, desc, prereq));
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Unit added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Credits must be a number.");
        }
    }

    private void searchUnit() {
        String keyword = searchField.getText().trim();
        Unit found = controller.searchByName(keyword);
        if (found != null) {
            JOptionPane.showMessageDialog(this, "Found: " + found.getUnitCode() + " - " + found.getUnitName());
        } else {
            JOptionPane.showMessageDialog(this, "No unit found with that name.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Unit u : controller.getAllUnits()) {
            tableModel.addRow(new Object[]{u.getUnitCode(), u.getUnitName(), u.getCredits(), u.getDescription(), u.getPrerequisites()});
        }
    }

    private void clearFields() {
        codeField.setText(""); nameField.setText("");
        creditsField.setText(""); descField.setText(""); prereqField.setText("");
    }
}