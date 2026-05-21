package com.cms.view;

import com.cms.controller.UnitOfferingController;
import com.cms.model.UnitOffering;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.cms.view.ViewHelper.*;

public class UnitOfferingView extends JFrame {

    private UnitOfferingController controller = new UnitOfferingController();
    private JTextField idField, unitCodeField, semesterField, yearField, instructorIdField;
    private JTable table;
    private DefaultTableModel tableModel;

    public UnitOfferingView() {
        setTitle("Manage Unit Offerings");
        setSize(760, 560);
        setLocationRelativeTo(null);

        JPanel root = buildRoot(this, "📅  Manage Unit Offerings");

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBackground(MainFrame.BG);
        content.setBorder(new EmptyBorder(10, 14, 14, 14));
        root.add(content, BorderLayout.CENTER);

        // ── Form ──────────────────────────────────────────────
        JPanel formPanel = titledPanel("Add Offering", new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        idField           = field(0);
        unitCodeField     = field(0);
        semesterField     = field(0);
        yearField         = field(0);
        instructorIdField = field(0);

        // Two-column layout: row 0: Offering ID + Unit Code; row 1: Semester + Year; row 2: Instructor
        String[] colALabel = {"Offering ID:", "Semester:", "Instructor ID:"};
        JTextField[] colAField = {idField, semesterField, instructorIdField};
        String[] colBLabel = {"Unit Code:", "Year:", ""};
        JTextField[] colBField = {unitCodeField, yearField, null};

        for (int i = 0; i < 3; i++) {
            gc.gridx = 0; gc.gridy = i; gc.weightx = 0.12;
            formPanel.add(label(colALabel[i]), gc);
            gc.gridx = 1; gc.weightx = 0.38;
            formPanel.add(colAField[i], gc);

            if (colBField[i] != null) {
                gc.gridx = 2; gc.weightx = 0.12;
                formPanel.add(label(colBLabel[i]), gc);
                gc.gridx = 3; gc.weightx = 0.38;
                formPanel.add(colBField[i], gc);
            }
        }

        JButton addBtn = primaryBtn("Add Offering");
        gc.gridx = 3; gc.gridy = 3;
        gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.EAST;
        formPanel.add(addBtn, gc);

        content.add(formPanel, BorderLayout.NORTH);

        // ── Table ─────────────────────────────────────────────
        tableModel = new DefaultTableModel(
                new String[]{"Offering ID", "Unit Code", "Semester", "Year", "Instructor ID"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);

        JPanel tableSection = new JPanel(new BorderLayout());
        tableSection.setBackground(MainFrame.BG);
        tableSection.add(styledTable(table), BorderLayout.CENTER);
        content.add(tableSection, BorderLayout.CENTER);

        addBtn.addActionListener(e -> addOffering());
        refreshTable();
    }

    private void addOffering() {
        String id           = idField.getText().trim();
        String unitCode     = unitCodeField.getText().trim();
        String semester     = semesterField.getText().trim();
        String year         = yearField.getText().trim();
        String instructorId = instructorIdField.getText().trim();

        if (id.isEmpty() || unitCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Offering ID and Unit Code are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.addOffering(new UnitOffering(id, unitCode, semester, year, instructorId));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Offering added successfully!");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (UnitOffering o : controller.getAllOfferings())
            tableModel.addRow(new Object[]{o.getOfferingId(), o.getUnitCode(), o.getSemester(), o.getYear(), o.getInstructorId()});
    }

    private void clearFields() {
        for (JTextField f : new JTextField[]{idField, unitCodeField, semesterField, yearField, instructorIdField})
            f.setText("");
    }
}