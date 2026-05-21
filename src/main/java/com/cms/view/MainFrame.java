package com.cms.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    // Simple prototype palette
    static final Color BG        = new Color(245, 243, 238);
    static final Color PANEL_BG  = new Color(255, 255, 252);
    static final Color ACCENT    = new Color(70, 100, 160);
    static final Color ACCENT_HV = new Color(50,  75, 130);
    static final Color TEXT      = new Color(35,  35,  40);
    static final Color BORDER_C  = new Color(200, 198, 192);

    static final Font TITLE_FONT  = new Font("Georgia", Font.BOLD, 20);
    static final Font LABEL_FONT  = new Font("Tahoma", Font.PLAIN, 13);
    static final Font BTN_FONT    = new Font("Tahoma", Font.BOLD,  13);

    public MainFrame() {
        setTitle("College Management System");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(BG);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(root);

        // ---- Header ----
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ACCENT);
        header.setBorder(new EmptyBorder(18, 24, 18, 24));
        JLabel title = new JLabel("College Management System", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        JLabel sub = new JLabel("Prototype v1.0", SwingConstants.CENTER);
        sub.setFont(new Font("Tahoma", Font.ITALIC, 11));
        sub.setForeground(new Color(200, 210, 230));
        header.add(sub, BorderLayout.SOUTH);
        root.add(header, BorderLayout.NORTH);

        // ---- Button Grid ----
        JPanel grid = new JPanel(new GridLayout(3, 2, 12, 12));
        grid.setBackground(BG);
        grid.setBorder(new EmptyBorder(24, 32, 24, 32));

        String[] labels = {
                "📚  Manage Units",
                "📅  Unit Offerings",
                "👨‍🏫  Manage Instructors",
                "🎓  Manage Students",
                "📝  Manage Enrolments",
                "📊  View Reports"
        };
        Runnable[] actions = {
                () -> new UnitView().setVisible(true),
                () -> new UnitOfferingView().setVisible(true),
                () -> new InstructorView().setVisible(true),
                () -> new StudentView().setVisible(true),
                () -> new EnrolmentView().setVisible(true),
                () -> new ReportView().setVisible(true)
        };

        for (int i = 0; i < labels.length; i++) {
            grid.add(makeNavBtn(labels[i], actions[i]));
        }

        root.add(grid, BorderLayout.CENTER);

        // ---- Footer ----
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(230, 228, 222));
        footer.setBorder(new EmptyBorder(6, 0, 6, 0));
        JLabel fl = new JLabel("CMS — College Management System  |  select a module above");
        fl.setFont(new Font("Tahoma", Font.PLAIN, 11));
        fl.setForeground(new Color(120, 118, 112));
        footer.add(fl);
        root.add(footer, BorderLayout.SOUTH);
    }

    private JButton makeNavBtn(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setBackground(PANEL_BG);
        btn.setForeground(TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(10, 14, 10, 14)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(235, 240, 255));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT, 1),
                        new EmptyBorder(10, 14, 10, 14)
                ));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(PANEL_BG);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_C, 1),
                        new EmptyBorder(10, 14, 10, 14)
                ));
            }
        });
        btn.addActionListener(e -> action.run());
        return btn;
    }

    /** Apply global UI defaults — call once from main before creating frames. */
    public static void applyGlobalStyle() {
        UIManager.put("Panel.background",       BG);
        UIManager.put("OptionPane.background",  BG);
        UIManager.put("Button.background",      PANEL_BG);
        UIManager.put("Button.foreground",      TEXT);
        UIManager.put("Table.gridColor",        BORDER_C);
        UIManager.put("Table.background",       PANEL_BG);
        UIManager.put("Table.alternateRowColor",new Color(238, 236, 230));
        UIManager.put("TableHeader.background", ACCENT);
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("ScrollPane.border",      BorderFactory.createLineBorder(BORDER_C, 1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            applyGlobalStyle();
            new MainFrame().setVisible(true);
        });
    }
}