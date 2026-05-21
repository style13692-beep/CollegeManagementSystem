package com.cms.view;

import com.cms.controller.InstructorController;
import com.cms.model.Instructor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.cms.view.ViewHelper.*;

public class InstructorView extends JFrame {

    private InstructorController controller = new InstructorController();
    private JTextField idField, nameField, emailField, deptField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public InstructorView() {
        setTitle("Manage Instructors");
        setSize(720, 540);
        setLocationRelativeTo(null);

        JPanel root = buildRoot(this, "👨‍🏫  Manage Instructors");

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBackground(MainFrame.BG);
        content.setBorder(new EmptyBorder(10, 14, 14, 14));
        root.add(content, BorderLayout.CENTER);

        // ── Form ──────────────────────────────────────────────
        JPanel formPanel = titledPanel("Add Instructor", new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        idField    = field(0);
        nameField  = field(0);
        emailField = field(0);
        deptField  = field(0);

        String[] labels = {"Instructor ID:", "Name:", "Email:", "Department:"};
        JTextField[] fields = {idField, nameField, emailField, deptField};

        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i; gc.weightx = 0.22;
            formPanel.add(label(labels[i]), gc);
            gc.gridx = 1; gc.weightx = 0.78;
            formPanel.add(fields[i], gc);
        }

        JButton addBtn = primaryBtn("Add Instructor");
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
                new String[]{"ID", "Name", "Email", "Department"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);

        JPanel tableSection = new JPanel(new BorderLayout(0, 6));
        tableSection.setBackground(MainFrame.BG);
        tableSection.add(searchRow,          BorderLayout.NORTH);
        tableSection.add(styledTable(table), BorderLayout.CENTER);
        content.add(tableSection, BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────
        addBtn.addActionListener(e    -> addInstructor());
        searchBtn.addActionListener(e -> searchInstructor());
        sortBtn.addActionListener(e   -> { controller.sortByName(); refreshTable(); });

        refreshTable();
    }

    private void addInstructor() {
        String id   = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String dept  = deptField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.addInstructor(new Instructor(id, name, email, dept));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Instructor added successfully!");
    }

    private void searchInstructor() {
        Instructor found = controller.searchByName(searchField.getText().trim());
        if (found != null)
            JOptionPane.showMessageDialog(this, "Found: " + found.getInstructorId() + " – " + found.getName());
        else
            JOptionPane.showMessageDialog(this, "No instructor found.", "Not found", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Instructor i : controller.getAllInstructors())
            tableModel.addRow(new Object[]{i.getInstructorId(), i.getName(), i.getEmail(), i.getDepartment()});
    }

    private void clearFields() {
        for (JTextField f : new JTextField[]{idField, nameField, emailField, deptField})
            f.setText("");
    }
}