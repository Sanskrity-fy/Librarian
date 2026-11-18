package Function;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;

public class BookTypeManager extends JFrame {
    private DatabaseHandler dbHandler = new DatabaseHandler();
    private DefaultTableModel tableModel;
    private JTable table;

    public BookTypeManager() {
        setTitle("图书分类管理");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Initialize table model and table
        tableModel = new DefaultTableModel(new String[]{"类型"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Populate table with data
        try {
            Set<String> uniqueTypes = dbHandler.getUniqueBookTypes();
            for (String type : uniqueTypes) {
                tableModel.addRow(new Object[]{type});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Create buttons for CRUD operations
        JPanel buttonPanel = new JPanel();
        
        JButton addButton = new JButton("增加");
        JButton editButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");
        JButton cancelButton = new JButton("取消");

        addButton.addActionListener(new AddButtonListener());
        editButton.addActionListener(new EditButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);

        // Select row listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });
    }
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField input = new JTextField(20);
            JPanel panel = new JPanel();
            panel.add(new JLabel("新类型:"));
            panel.add(input);

            int result = JOptionPane.showConfirmDialog(null, panel, "增加类型", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String newType = input.getText().trim();
                try {
                    dbHandler.insertBookType(newType);
                    tableModel.addRow(new Object[]{newType});
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String oldType = (String) tableModel.getValueAt(selectedRow, 0);
                JTextField input = new JTextField(oldType, 20);
                JPanel panel = new JPanel();
                panel.add(new JLabel("类型:"));
                panel.add(input);

                int result = JOptionPane.showConfirmDialog(null, panel, "修改类型", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String newType = input.getText().trim();
                    try {
                        dbHandler.updateBookType(oldType, newType);
                        tableModel.setValueAt(newType, selectedRow, 0);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String type = (String) tableModel.getValueAt(selectedRow, 0);
                int result = JOptionPane.showConfirmDialog(null, "你确定要删除类型： " + type + "?", "删除类型", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        dbHandler.deleteBookType(type);
                        tableModel.removeRow(selectedRow);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookTypeManager frame = new BookTypeManager();
            frame.setVisible(true);
        });
    }
}