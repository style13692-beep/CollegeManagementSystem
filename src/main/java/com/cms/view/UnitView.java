package com.cms.view;

import com.cms.controller.UnitController;
import com.cms.model.Unit;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static com.cms.view.ViewHelper.*;

public class UnitView extends JFrame {

    private UnitController controller = new UnitController();
    private JTextField codeField, nameField, creditsField, descField, prereqField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;

    public UnitView() {
        setTitle("Manage Units");
        setSize(760, 580);
        setLocationRelativeTo(null);

        JPanel root = buildRoot(this, "📚  Manage Units");

        // ── content area (NORTH form + CENTER table section)
        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBackground(MainFrame.BG);
        content.setBorder(new EmptyBorder(10, 14, 14, 14));
        root.add(content, BorderLayout.CENTER);

        // ── Form ──────────────────────────────────────────────
        JPanel formPanel = titledPanel("Add Unit", new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        codeField    = field(0);
        nameField    = field(0);
        creditsField = field(0);
        descField    = field(0);
        prereqField  = field(0);

        String[] labels = {"Unit Code:", "Unit Name:", "Credits:", "Description:", "Prerequisites:"};
        JTextField[] fields = {codeField, nameField, creditsField, descField, prereqField};

        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i; gc.weightx = 0.18;
            formPanel.add(label(labels[i]), gc);
            gc.gridx = 1; gc.weightx = 0.82;
            formPanel.add(fields[i], gc);
        }

        JButton addBtn = primaryBtn("Add Unit");
        gc.gridx = 1; gc.gridy = labels.length; gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE; gc.anchor = GridBagConstraints.EAST;
        formPanel.add(addBtn, gc);

        content.add(formPanel, BorderLayout.NORTH);

        // ── Search bar ────────────────────────────────────────
        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchRow.setBackground(MainFrame.BG);
        searchField = field(18);
        JButton searchBtn = secondaryBtn("Search by Name");
        JButton sortBtn   = secondaryBtn("Sort by Code");
        searchRow.add(label("Search:"));
        searchRow.add(searchField);
        searchRow.add(searchBtn);
        searchRow.add(sortBtn);

        // ── Table ─────────────────────────────────────────────
        tableModel = new DefaultTableModel(
                new String[]{"Code", "Name", "Credits", "Description", "Prerequisites"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scroll = styledTable(table);

        JPanel tableSection = new JPanel(new BorderLayout(0, 6));
        tableSection.setBackground(MainFrame.BG);
        tableSection.add(searchRow, BorderLayout.NORTH);
        tableSection.add(scroll,    BorderLayout.CENTER);
        content.add(tableSection, BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────
        addBtn.addActionListener(e    -> addUnit());
        searchBtn.addActionListener(e -> searchUnit());
        sortBtn.addActionListener(e   -> { controller.sortByCode(); refreshTable(); });

        refreshTable();
    }

    private void addUnit() {
        try {
            String code   = codeField.getText().trim();
            String name   = nameField.getText().trim();
            int credits   = Integer.parseInt(creditsField.getText().trim());
            String desc   = descField.getText().trim();
            String prereq = prereqField.getText().trim();

            if (code.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Code and Name are required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.addUnit(new Unit(code, name, credits, desc, prereq));
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Unit added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Credits must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchUnit() {
        Unit found = controller.searchByName(searchField.getText().trim());
        if (found != null)
            JOptionPane.showMessageDialog(this, "Found: " + found.getUnitCode() + " – " + found.getUnitName());
        else
            JOptionPane.showMessageDialog(this, "No unit found with that name.", "Not found", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Unit u : controller.getAllUnits())
            tableModel.addRow(new Object[]{u.getUnitCode(), u.getUnitName(), u.getCredits(), u.getDescription(), u.getPrerequisites()});
    }

    private void clearFields() {
        for (JTextField f : new JTextField[]{codeField, nameField, creditsField, descField, prereqField})
            f.setText("");
    }
}