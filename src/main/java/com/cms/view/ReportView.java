package com.cms.view;

import com.cms.controller.EnrolmentController;
import com.cms.controller.StudentController;
import com.cms.controller.UnitOfferingController;
import com.cms.model.Enrolment;
import com.cms.model.Student;
import com.cms.model.UnitOffering;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.cms.view.ViewHelper.*;
import static com.cms.view.MainFrame.*;

public class ReportView extends JFrame {

    private EnrolmentController    enrolmentController = new EnrolmentController();
    private StudentController      studentController   = new StudentController();
    private UnitOfferingController offeringController  = new UnitOfferingController();

    public ReportView() {
        setTitle("Reports");
        setSize(740, 560);
        setLocationRelativeTo(null);

        JPanel root = buildRoot(this, "📊  Reports");

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(10, 14, 14, 14));
        root.add(content, BorderLayout.CENTER);

        // ── Button bar ────────────────────────────────────────
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnBar.setBackground(BG);
        JButton enrolBtn = primaryBtn("Enrolment Report");
        JButton classBtn = primaryBtn("Class Allocation Report");
        JButton clearBtn = secondaryBtn("Clear");
        btnBar.add(enrolBtn);
        btnBar.add(classBtn);
        btnBar.add(clearBtn);
        content.add(btnBar, BorderLayout.NORTH);

        // ── Text area ─────────────────────────────────────────
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 13));
        reportArea.setBackground(PANEL_BG);
        reportArea.setForeground(TEXT);
        reportArea.setLineWrap(false);
        reportArea.setBorder(new EmptyBorder(8, 10, 8, 10));
        reportArea.setText("Select a report type above to generate output...\n");

        JScrollPane scroll = new JScrollPane(reportArea);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_C, 1));
        content.add(scroll, BorderLayout.CENTER);

        // ── Status bar ────────────────────────────────────────
        JLabel statusBar = new JLabel(" Ready");
        statusBar.setFont(new Font("Tahoma", Font.PLAIN, 11));
        statusBar.setForeground(new Color(120, 118, 112));
        statusBar.setBorder(new EmptyBorder(4, 6, 4, 6));
        content.add(statusBar, BorderLayout.SOUTH);

        // ── Actions ───────────────────────────────────────────
        enrolBtn.addActionListener(e -> {
            statusBar.setText(" Generating enrolment report…");
            StringBuilder sb = new StringBuilder();
            sb.append("╔══════════════════════════════════════════╗\n");
            sb.append("║          ENROLMENT REPORT                ║\n");
            sb.append("╚══════════════════════════════════════════╝\n\n");
            for (Student s : studentController.getAllStudents()) {
                sb.append("  Student: ").append(s.getName())
                        .append("  [").append(s.getStudentId()).append("]\n");
                sb.append("  ").append("─".repeat(44)).append("\n");
                var enrols = enrolmentController.findByStudent(s.getStudentId());
                if (enrols.isEmpty()) {
                    sb.append("    (no enrolments)\n");
                } else {
                    for (Enrolment en : enrols) {
                        sb.append(String.format("    %-18s  date: %-12s  status: %s%n",
                                en.getOfferingId(), en.getEnrolmentDate(), en.getStatus()));
                    }
                }
                sb.append("\n");
            }
            reportArea.setText(sb.toString());
            reportArea.setCaretPosition(0);
            statusBar.setText(" Enrolment report generated — " + studentController.getAllStudents().size() + " student(s).");
        });

        classBtn.addActionListener(e -> {
            statusBar.setText(" Generating class allocation report…");
            StringBuilder sb = new StringBuilder();
            sb.append("╔══════════════════════════════════════════╗\n");
            sb.append("║       CLASS ALLOCATION REPORT            ║\n");
            sb.append("╚══════════════════════════════════════════╝\n\n");
            for (UnitOffering o : offeringController.getAllOfferings()) {
                sb.append(String.format("  Offering: %-10s  Unit: %-10s  Instructor: %s%n",
                        o.getOfferingId(), o.getUnitCode(), o.getInstructorId()));
                sb.append("  ").append("─".repeat(44)).append("\n");
                var enrols = enrolmentController.findByOffering(o.getOfferingId());
                if (enrols.isEmpty()) {
                    sb.append("    (no students enrolled)\n");
                } else {
                    for (Enrolment en : enrols) {
                        sb.append(String.format("    student: %-14s  status: %s%n",
                                en.getStudentId(), en.getStatus()));
                    }
                }
                sb.append("\n");
            }
            reportArea.setText(sb.toString());
            reportArea.setCaretPosition(0);
            statusBar.setText(" Class allocation report generated — " + offeringController.getAllOfferings().size() + " offering(s).");
        });

        clearBtn.addActionListener(e -> {
            reportArea.setText("Select a report type above to generate output...\n");
            statusBar.setText(" Ready");
        });
    }
}