package com.cms.view;

import com.cms.controller.InstructorController;
import com.cms.model.Instructor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InstructorView extends JFrame {
    private InstructorController controller = new InstructorController();
    private JTextField idField, nameField, emailField, deptField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public InstructorView() {
        setTitle("Manage Instructors");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Instructor"));

        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        deptField = new JTextField();

        formPanel.add(new JLabel("Instructor ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));          formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));         formPanel.add(emailField);
        formPanel.add(new JLabel("Department:"));    formPanel.add(deptField);

        JButton addBtn = new JButton("Add Instructor");
        formPanel.add(new JLabel()); formPanel.add(addBtn);

        add(formPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton sortBtn = new JButton("Sort by Name");
        searchPanel.add(new JLabel("Search by name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(sortBtn);
        add(searchPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Department"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addInstructor());
        searchBtn.addActionListener(e -> searchInstructor());
        sortBtn.addActionListener(e -> { controller.sortByName(); refreshTable(); });

        refreshTable();
    }

    private void addInstructor() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String dept = deptField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required.");
            return;
        }

        controller.addInstructor(new Instructor(id, name, email, dept));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Instructor added successfully!");
    }

    private void searchInstructor() {
        Instructor found = controller.searchByName(searchField.getText().trim());
        if (found != null) {
            JOptionPane.showMessageDialog(this, "Found: " + found.getInstructorId() + " - " + found.getName());
        } else {
            JOptionPane.showMessageDialog(this, "No instructor found.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Instructor i : controller.getAllInstructors()) {
            tableModel.addRow(new Object[]{i.getInstructorId(), i.getName(), i.getEmail(), i.getDepartment()});
        }
    }

    private void clearFields() {
        idField.setText(""); nameField.setText("");
        emailField.setText(""); deptField.setText("");
    }
}