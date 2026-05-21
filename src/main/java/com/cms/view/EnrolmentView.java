package com.cms.view;

import com.cms.controller.EnrolmentController;
import com.cms.model.Enrolment;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.cms.view.ViewHelper.*;

public class EnrolmentView extends JFrame {

    private EnrolmentController controller = new EnrolmentController();
    private JTextField enrolIdField, studentIdField, offeringIdField, dateField, statusField, filterField;
    private JTable table;
    private DefaultTableModel tableModel;

    public EnrolmentView() {
        setTitle("Manage Enrolments");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel root = buildRoot(this, "📝  Manage Enrolments");

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBackground(MainFrame.BG);
        content.setBorder(new EmptyBorder(10, 14, 14, 14));
        root.add(content, BorderLayout.CENTER);

        // ── Form ──────────────────────────────────────────────
        JPanel formPanel = titledPanel("Enrol a Student", new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        enrolIdField    = field(0);
        studentIdField  = field(0);
        offeringIdField = field(0);
        dateField       = field(0);
        statusField     = field(0);

        // Two-column form layout (5 rows × 2 pairs of label+field)
        String[] row1Labels = {"Enrolment ID:", "Student ID:"};
        JTextField[] row1Fields = {enrolIdField, studentIdField};
        String[] row2Labels = {"Offering ID:", "Date (YYYY-MM-DD):"};
        JTextField[] row2Fields = {offeringIdField, dateField};

        for (int i = 0; i < 2; i++) {
            gc.gridx = i * 2;     gc.gridy = 0; gc.weightx = 0.12;
            formPanel.add(label(row1Labels[i]), gc);
            gc.gridx = i * 2 + 1; gc.weightx = 0.38;
            formPanel.add(row1Fields[i], gc);
        }
        for (int i = 0; i < 2; i++) {
            gc.gridx = i * 2;     gc.gridy = 1; gc.weightx = 0.12;
            formPanel.add(label(row2Labels[i]), gc);
            gc.gridx = i * 2 + 1; gc.weightx = 0.38;
            formPanel.add(row2Fields[i], gc);
        }

        gc.gridx = 0; gc.gridy = 2; gc.weightx = 0.12;
        formPanel.add(label("Status:"), gc);
        gc.gridx = 1; gc.weightx = 0.38;
        formPanel.add(statusField, gc);

        JButton addBtn = primaryBtn("Enrol Student");
        gc.gridx = 3; gc.gridy = 2;
        gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.EAST;
        formPanel.add(addBtn, gc);

        content.add(formPanel, BorderLayout.NORTH);

        // ── Filter row ────────────────────────────────────────
        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filterRow.setBackground(MainFrame.BG);
        filterField = field(16);
        JButton filterStudentBtn  = secondaryBtn("Filter by Student ID");
        JButton filterOfferingBtn = secondaryBtn("Filter by Offering ID");
        JButton showAllBtn        = secondaryBtn("Show All");
        filterRow.add(label("Filter ID:"));
        filterRow.add(filterField);
        filterRow.add(filterStudentBtn);
        filterRow.add(filterOfferingBtn);
        filterRow.add(showAllBtn);

        // ── Table ─────────────────────────────────────────────
        tableModel = new DefaultTableModel(
                new String[]{"Enrolment ID", "Student ID", "Offering ID", "Date", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);

        JPanel tableSection = new JPanel(new BorderLayout(0, 6));
        tableSection.setBackground(MainFrame.BG);
        tableSection.add(filterRow,          BorderLayout.NORTH);
        tableSection.add(styledTable(table), BorderLayout.CENTER);
        content.add(tableSection, BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────
        addBtn.addActionListener(e           -> addEnrolment());
        filterStudentBtn.addActionListener(e  -> filterByStudent());
        filterOfferingBtn.addActionListener(e -> filterByOffering());
        showAllBtn.addActionListener(e        -> refreshTable());

        refreshTable();
    }

    private void addEnrolment() {
        String eid    = enrolIdField.getText().trim();
        String sid    = studentIdField.getText().trim();
        String oid    = offeringIdField.getText().trim();
        String date   = dateField.getText().trim();
        String status = statusField.getText().trim();

        if (eid.isEmpty() || sid.isEmpty() || oid.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enrolment ID, Student ID and Offering ID are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.addEnrolment(new Enrolment(eid, sid, oid, date, status));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Student enrolled successfully!");
    }

    private void filterByStudent() {
        String id = filterField.getText().trim();
        tableModel.setRowCount(0);
        for (Enrolment e : controller.findByStudent(id))
            tableModel.addRow(new Object[]{e.getEnrolmentId(), e.getStudentId(), e.getOfferingId(), e.getEnrolmentDate(), e.getStatus()});
    }

    private void filterByOffering() {
        String id = filterField.getText().trim();
        tableModel.setRowCount(0);
        for (Enrolment e : controller.findByOffering(id))
            tableModel.addRow(new Object[]{e.getEnrolmentId(), e.getStudentId(), e.getOfferingId(), e.getEnrolmentDate(), e.getStatus()});
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Enrolment e : controller.getAllEnrolments())
            tableModel.addRow(new Object[]{e.getEnrolmentId(), e.getStudentId(), e.getOfferingId(), e.getEnrolmentDate(), e.getStatus()});
    }

    private void clearFields() {
        for (JTextField f : new JTextField[]{enrolIdField, studentIdField, offeringIdField, dateField, statusField})
            f.setText("");
    }
}