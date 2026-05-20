package com.cms.view;

import com.cms.controller.StudentController;
import com.cms.model.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentView extends JFrame {
    private StudentController controller = new StudentController();
    private JTextField idField, nameField, emailField, yearField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentView() {
        setTitle("Manage Students");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        yearField = new JTextField();

        formPanel.add(new JLabel("Student ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));       formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));      formPanel.add(emailField);
        formPanel.add(new JLabel("Enrollment Year:")); formPanel.add(yearField);

        JButton addBtn = new JButton("Add Student");
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

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Year"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addStudent());
        searchBtn.addActionListener(e -> searchStudent());
        sortBtn.addActionListener(e -> { controller.sortByName(); refreshTable(); });

        refreshTable();
    }

    private void addStudent() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String year = yearField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required.");
            return;
        }

        controller.addStudent(new Student(id, name, email, year));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Student added successfully!");
    }

    private void searchStudent() {
        Student found = controller.searchByName(searchField.getText().trim());
        if (found != null) {
            JOptionPane.showMessageDialog(this, "Found: " + found.getStudentId() + " - " + found.getName());
        } else {
            JOptionPane.showMessageDialog(this, "No student found.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : controller.getAllStudents()) {
            tableModel.addRow(new Object[]{s.getStudentId(), s.getName(), s.getEmail(), s.getEnrollmentYear()});
        }
    }

    private void clearFields() {
        idField.setText(""); nameField.setText("");
        emailField.setText(""); yearField.setText("");
    }
}