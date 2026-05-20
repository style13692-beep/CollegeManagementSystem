package com.cms.view;

import com.cms.controller.EnrolmentController;
import com.cms.model.Enrolment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EnrolmentView extends JFrame {
    private EnrolmentController controller = new EnrolmentController();
    private JTextField enrolIdField, studentIdField, offeringIdField, dateField, statusField, filterField;
    private JTable table;
    private DefaultTableModel tableModel;

    public EnrolmentView() {
        setTitle("Manage Enrolments");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Enrolment"));

        enrolIdField = new JTextField();
        studentIdField = new JTextField();
        offeringIdField = new JTextField();
        dateField = new JTextField();
        statusField = new JTextField();

        formPanel.add(new JLabel("Enrolment ID:"));  formPanel.add(enrolIdField);
        formPanel.add(new JLabel("Student ID:"));    formPanel.add(studentIdField);
        formPanel.add(new JLabel("Offering ID:"));   formPanel.add(offeringIdField);
        formPanel.add(new JLabel("Date (YYYY-MM-DD):")); formPanel.add(dateField);
        formPanel.add(new JLabel("Status:"));        formPanel.add(statusField);

        JButton addBtn = new JButton("Enroll Student");
        formPanel.add(new JLabel()); formPanel.add(addBtn);

        add(formPanel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterField = new JTextField(15);
        JButton filterStudentBtn = new JButton("Filter by Student ID");
        JButton filterOfferingBtn = new JButton("Filter by Offering ID");
        JButton showAllBtn = new JButton("Show All");
        filterPanel.add(new JLabel("Filter ID:"));
        filterPanel.add(filterField);
        filterPanel.add(filterStudentBtn);
        filterPanel.add(filterOfferingBtn);
        filterPanel.add(showAllBtn);
        add(filterPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new String[]{"Enrolment ID", "Student ID", "Offering ID", "Date", "Status"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addEnrolment());
        filterStudentBtn.addActionListener(e -> filterByStudent());
        filterOfferingBtn.addActionListener(e -> filterByOffering());
        showAllBtn.addActionListener(e -> refreshTable());

        refreshTable();
    }

    private void addEnrolment() {
        String eid = enrolIdField.getText().trim();
        String sid = studentIdField.getText().trim();
        String oid = offeringIdField.getText().trim();
        String date = dateField.getText().trim();
        String status = statusField.getText().trim();

        if (eid.isEmpty() || sid.isEmpty() || oid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enrolment ID, Student ID and Offering ID are required.");
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
        for (Enrolment e : controller.findByStudent(id)) {
            tableModel.addRow(new Object[]{e.getEnrolmentId(), e.getStudentId(), e.getOfferingId(), e.getEnrolmentDate(), e.getStatus()});
        }
    }

    private void filterByOffering() {
        String id = filterField.getText().trim();
        tableModel.setRowCount(0);
        for (Enrolment e : controller.findByOffering(id)) {
            tableModel.addRow(new Object[]{e.getEnrolmentId(), e.getStudentId(), e.getOfferingId(), e.getEnrolmentDate(), e.getStatus()});
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Enrolment e : controller.getAllEnrolments()) {
            tableModel.addRow(new Object[]{e.getEnrolmentId(), e.getStudentId(), e.getOfferingId(), e.getEnrolmentDate(), e.getStatus()});
        }
    }

    private void clearFields() {
        enrolIdField.setText(""); studentIdField.setText("");
        offeringIdField.setText(""); dateField.setText(""); statusField.setText("");
    }
}