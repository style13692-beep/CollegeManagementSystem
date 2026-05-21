package com.cms.view;

import com.cms.controller.StudentController;
import com.cms.model.Student;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.cms.view.ViewHelper.*;

public class StudentView extends JFrame {

    private StudentController controller = new StudentController();
    private JTextField idField, nameField, emailField, yearField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentView() {
        setTitle("Manage Students");
        setSize(720, 560);
        setLocationRelativeTo(null);

        JPanel root = buildRoot(this, "🎓  Manage Students");

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBackground(MainFrame.BG);
        content.setBorder(new EmptyBorder(10, 14, 14, 14));
        root.add(content, BorderLayout.CENTER);

        // ── Form ──────────────────────────────────────────────
        JPanel formPanel = titledPanel("Add Student", new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        idField    = field(0);
        nameField  = field(0);
        emailField = field(0);
        yearField  = field(0);

        String[] labels = {"Student ID:", "Name:", "Email:", "Enrollment Year:"};
        JTextField[] fields = {idField, nameField, emailField, yearField};

        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i; gc.weightx = 0.20;
            formPanel.add(label(labels[i]), gc);
            gc.gridx = 1; gc.weightx = 0.80;
            formPanel.add(fields[i], gc);
        }

        JButton addBtn = primaryBtn("Add Student");
        gc.gridx = 1; gc.gridy = labels.length;
        gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.EAST;
        formPanel.add(addBtn, gc);

        content.add(formPanel, BorderLayout.NORTH);

        // ── Search row ────────────────────────────────────────
        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchRow.setBackground(MainFrame.BG);
        searchField = field(18);
        JButton searchBtn = secondaryBtn("Search by Name");
        JButton sortBtn   = secondaryBtn("Sort by Name");
        searchRow.add(label("Search:"));
        searchRow.add(searchField);
        searchRow.add(searchBtn);
        searchRow.add(sortBtn);

        // ── Table ─────────────────────────────────────────────
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Year"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);

        JPanel tableSection = new JPanel(new BorderLayout(0, 6));
        tableSection.setBackground(MainFrame.BG);
        tableSection.add(searchRow,          BorderLayout.NORTH);
        tableSection.add(styledTable(table), BorderLayout.CENTER);
        content.add(tableSection, BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────
        addBtn.addActionListener(e    -> addStudent());
        searchBtn.addActionListener(e -> searchStudent());
        sortBtn.addActionListener(e   -> { controller.sortByName(); refreshTable(); });

        refreshTable();
    }

    private void addStudent() {
        String id   = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String year = yearField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.addStudent(new Student(id, name, email, year));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Student added successfully!");
    }

    private void searchStudent() {
        Student found = controller.searchByName(searchField.getText().trim());
        if (found != null)
            JOptionPane.showMessageDialog(this, "Found: " + found.getStudentId() + " – " + found.getName());
        else
            JOptionPane.showMessageDialog(this, "No student found.", "Not found", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : controller.getAllStudents())
            tableModel.addRow(new Object[]{s.getStudentId(), s.getName(), s.getEmail(), s.getEnrollmentYear()});
    }

    private void clearFields() {
        for (JTextField f : new JTextField[]{idField, nameField, emailField, yearField})
            f.setText("");
    }
}