package com.cms.view;

import com.cms.controller.EnrolmentController;
import com.cms.controller.StudentController;
import com.cms.controller.UnitOfferingController;
import com.cms.model.Enrolment;
import com.cms.model.Student;
import com.cms.model.UnitOffering;
import javax.swing.*;
import java.awt.*;

public class ReportView extends JFrame {
    private EnrolmentController enrolmentController = new EnrolmentController();
    private StudentController studentController = new StudentController();
    private UnitOfferingController offeringController = new UnitOfferingController();

    public ReportView() {
        setTitle("Reports");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton enrolReportBtn = new JButton("Enrolment Report");
        JButton classReportBtn = new JButton("Class Allocation Report");
        btnPanel.add(enrolReportBtn);
        btnPanel.add(classReportBtn);
        add(btnPanel, BorderLayout.NORTH);

        enrolReportBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Enrolment Report ===\n\n");
            for (Student s : studentController.getAllStudents()) {
                sb.append("Student: ").append(s.getName()).append(" (").append(s.getStudentId()).append(")\n");
                for (Enrolment en : enrolmentController.findByStudent(s.getStudentId())) {
                    sb.append("  -> Offering: ").append(en.getOfferingId())
                            .append(" | Date: ").append(en.getEnrolmentDate())
                            .append(" | Status: ").append(en.getStatus()).append("\n");
                }
                sb.append("\n");
            }
            reportArea.setText(sb.toString());
        });

        classReportBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Class Allocation Report ===\n\n");
            for (UnitOffering o : offeringController.getAllOfferings()) {
                sb.append("Offering: ").append(o.getOfferingId())
                        .append(" | Unit: ").append(o.getUnitCode())
                        .append(" | Instructor: ").append(o.getInstructorId()).append("\n");
                for (Enrolment en : enrolmentController.findByOffering(o.getOfferingId())) {
                    sb.append("  -> Student ID: ").append(en.getStudentId())
                            .append(" | Status: ").append(en.getStatus()).append("\n");
                }
                sb.append("\n");
            }
            reportArea.setText(sb.toString());
        });
    }
}