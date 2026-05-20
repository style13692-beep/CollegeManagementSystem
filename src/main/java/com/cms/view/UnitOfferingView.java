package com.cms.view;

import com.cms.controller.UnitOfferingController;
import com.cms.model.UnitOffering;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UnitOfferingView extends JFrame {
    private UnitOfferingController controller = new UnitOfferingController();
    private JTextField idField, unitCodeField, semesterField, yearField, instructorIdField;
    private JTable table;
    private DefaultTableModel tableModel;

    public UnitOfferingView() {
        setTitle("Manage Unit Offerings");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Unit Offering"));

        idField = new JTextField();
        unitCodeField = new JTextField();
        semesterField = new JTextField();
        yearField = new JTextField();
        instructorIdField = new JTextField();

        formPanel.add(new JLabel("Offering ID:"));    formPanel.add(idField);
        formPanel.add(new JLabel("Unit Code:"));      formPanel.add(unitCodeField);
        formPanel.add(new JLabel("Semester:"));       formPanel.add(semesterField);
        formPanel.add(new JLabel("Year:"));           formPanel.add(yearField);
        formPanel.add(new JLabel("Instructor ID:"));  formPanel.add(instructorIdField);

        JButton addBtn = new JButton("Add Offering");
        formPanel.add(new JLabel()); formPanel.add(addBtn);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Offering ID", "Unit Code", "Semester", "Year", "Instructor ID"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addBtn.addActionListener(e -> addOffering());

        refreshTable();
    }

    private void addOffering() {
        String id = idField.getText().trim();
        String unitCode = unitCodeField.getText().trim();
        String semester = semesterField.getText().trim();
        String year = yearField.getText().trim();
        String instructorId = instructorIdField.getText().trim();

        if (id.isEmpty() || unitCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Offering ID and Unit Code are required.");
            return;
        }

        controller.addOffering(new UnitOffering(id, unitCode, semester, year, instructorId));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Offering added successfully!");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (UnitOffering o : controller.getAllOfferings()) {
            tableModel.addRow(new Object[]{o.getOfferingId(), o.getUnitCode(), o.getSemester(), o.getYear(), o.getInstructorId()});
        }
    }

    private void clearFields() {
        idField.setText(""); unitCodeField.setText("");
        semesterField.setText(""); yearField.setText(""); instructorIdField.setText("");
    }
}