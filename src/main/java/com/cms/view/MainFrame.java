package com.cms.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("College Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("College Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton unitBtn = new JButton("Manage Units");
        JButton offeringBtn = new JButton("Manage Unit Offerings");
        JButton instructorBtn = new JButton("Manage Instructors");
        JButton studentBtn = new JButton("Manage Students");
        JButton enrolmentBtn = new JButton("Manage Enrolments");
        JButton reportBtn = new JButton("View Reports");

        buttonPanel.add(unitBtn);
        buttonPanel.add(offeringBtn);
        buttonPanel.add(instructorBtn);
        buttonPanel.add(studentBtn);
        buttonPanel.add(enrolmentBtn);
        buttonPanel.add(reportBtn);

        add(buttonPanel, BorderLayout.CENTER);

        unitBtn.addActionListener(e -> new UnitView().setVisible(true));
        offeringBtn.addActionListener(e -> new UnitOfferingView().setVisible(true));
        instructorBtn.addActionListener(e -> new InstructorView().setVisible(true));
        studentBtn.addActionListener(e -> new StudentView().setVisible(true));
        enrolmentBtn.addActionListener(e -> new EnrolmentView().setVisible(true));
        reportBtn.addActionListener(e -> new ReportView().setVisible(true));
    }
}