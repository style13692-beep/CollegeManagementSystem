package com.cms.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.cms.view.MainFrame.*;

/**
 * Shared factory methods so every view looks consistent without repeating code.
 */
public class ViewHelper {

    /** A standard action button (filled accent). */
    public static JButton primaryBtn(String label) {
        JButton b = new JButton(label);
        b.setFont(BTN_FONT);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(8, 18, 8, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(ACCENT_HV); }
            public void mouseExited (MouseEvent e) { b.setBackground(ACCENT); }
        });
        return b;
    }

    /** A secondary / outline-style button. */
    public static JButton secondaryBtn(String label) {
        JButton b = new JButton(label);
        b.setFont(BTN_FONT);
        b.setBackground(PANEL_BG);
        b.setForeground(ACCENT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                new EmptyBorder(6, 14, 6, 14)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(235, 240, 255)); }
            public void mouseExited (MouseEvent e) { b.setBackground(PANEL_BG); }
        });
        return b;
    }

    /** Styled text field. */
    public static JTextField field(int cols) {
        JTextField f = cols > 0 ? new JTextField(cols) : new JTextField();
        f.setFont(LABEL_FONT);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(4, 6, 4, 6)
        ));
        return f;
    }

    /** Titled section panel. */
    public static JPanel titledPanel(String title, LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(PANEL_BG);
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_C, 1), title,
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 12), ACCENT
        );
        p.setBorder(BorderFactory.createCompoundBorder(tb, new EmptyBorder(6, 8, 8, 8)));
        return p;
    }

    /** Styled JLabel. */
    public static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(LABEL_FONT);
        l.setForeground(TEXT);
        return l;
    }

    /** Apply styling to a JTable and return it inside a JScrollPane. */
    public static JScrollPane styledTable(JTable table) {
        table.setFont(LABEL_FONT);
        table.setRowHeight(24);
        table.setGridColor(BORDER_C);
        table.setBackground(PANEL_BG);
        table.setSelectionBackground(new Color(210, 220, 245));
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);

        // Alternating row colours
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setFont(LABEL_FONT);
                setBorder(new EmptyBorder(2, 6, 2, 6));
                if (!sel) {
                    setBackground(row % 2 == 0 ? PANEL_BG : new Color(238, 236, 230));
                }
                return this;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 12));
        header.setBackground(ACCENT);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_C, 1));
        sp.getViewport().setBackground(PANEL_BG);
        return sp;
    }

    /** Wrap a JFrame's content pane in a padded root panel with a coloured header. */
    public static JPanel buildRoot(JFrame frame, String title) {
        frame.setBackground(MainFrame.BG);

        // header bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ACCENT);
        header.setBorder(new EmptyBorder(10, 16, 10, 16));
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Georgia", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        header.add(lbl, BorderLayout.WEST);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.add(header, BorderLayout.NORTH);
        frame.setContentPane(root);
        return root;
    }
}