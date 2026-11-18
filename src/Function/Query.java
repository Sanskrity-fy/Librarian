package Function;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Query extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/lirarian";
    private static final String USER = "root";
    private static final String PASSWORD = "yuhang0821";
    private JTextField idField;
    private JTextField typeField;
    private JTextField nameField;
    private JTextField princeField;
    private JTextField countField;
    private JTextField publishHouseField;
    public  void Update(int id, String type, String name, double prince, int count, String publishHouse) {
        setTitle("修改图书信息");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLayout(new GridLayout(7, 2, 10, 10));
 
        add(new JLabel("ID:"));
        idField = new JTextField(String.valueOf(id), 5);
        idField.setEditable(false);
        add(idField); 
        add(new JLabel("类别:"));
        typeField = new JTextField(type, 10);
        add(typeField); 
        add(new JLabel("书名:"));
        nameField = new JTextField(name, 20);
        add(nameField);
        add(new JLabel("价格:"));
        princeField = new JTextField(String.valueOf(prince), 10);
        add(princeField); 
        add(new JLabel("数量:"));
        countField = new JTextField(String.valueOf(count), 5);
        add(countField);
        add(new JLabel("出版社:"));
        publishHouseField = new JTextField(publishHouse, 20);
        setLayout(new GridLayout(7, 2, 10, 10));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//居中显示
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        add(publishHouseField);
        JButton updateButton = new JButton("修改");
        JButton cancelButton = new JButton("取消");
        add(updateButton);
        add(cancelButton); 
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("图书信息管理");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(550, 300);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JLabel searchLabel = new JLabel("查找:");
        JTextField searchField = new JTextField(20);
        JComboBox<String> searchComboBox = new JComboBox<>(new String[]{"id", "type", "name", "publish_house"});
        JButton searchButton = new JButton("查找");
        JButton deleteButton = new JButton("删除");
        JButton updateButton = new JButton("修改");
        JButton cancelButton = new JButton("取消");
        JButton addButton = new JButton("添加");
        inputPanel.add(searchLabel);
        inputPanel.add(searchComboBox);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(deleteButton);
        inputPanel.add(updateButton);
        inputPanel.add(addButton);
        inputPanel.add(cancelButton);
        // Create table to display results
        JTable resultTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("类别");
        tableModel.addColumn("书名");
        tableModel.addColumn("价格");
        tableModel.addColumn("数量");
        tableModel.addColumn("出版社");
        resultTable.setModel(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //选择一行进行操作  
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the window
            }
        });

        // Add action listener to search button
        searchButton.addActionListener(e -> {
            String searchColumn = (String) searchComboBox.getSelectedItem();
            String searchValue = searchField.getText();
            displayBooks(searchColumn, searchValue, tableModel);
        });

        // Add action listener to delete button
        deleteButton.addActionListener(e -> {
            int selectedRow = resultTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                deleteBook(id);
                displayBooks((String) searchComboBox.getSelectedItem(), searchField.getText(), tableModel); // Refresh table
            } else {
                JOptionPane.showMessageDialog(frame, "请先选择一行进行删除", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Make frame visible
        frame.setVisible(true);
     // Add action listener to update button
        updateButton.addActionListener(e -> {
            int selectedRow = resultTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String type = (String) tableModel.getValueAt(selectedRow, 1);
                String name = (String) tableModel.getValueAt(selectedRow, 2);
                double prince = (double) tableModel.getValueAt(selectedRow, 3);
                int count = (int) tableModel.getValueAt(selectedRow, 4);
                String publishHouse = (String) tableModel.getValueAt(selectedRow, 5);
                // 创建一个Query对象实例，并调用其Update方法
                Query query = new Query();
                query.Update(id, type, name, prince, count, publishHouse);
                query.setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(frame, "请先选择一行进行修改", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
        addButton.addActionListener(e -> {
            Add add = new Add();
            add.setVisible(true);
        });
    }

    private static void displayBooks(String searchColumn, String searchValue, DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear table contents

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(
                     "SELECT id, type, name, prince, count, publish_house FROM book WHERE " + searchColumn + " LIKE ?"
             )) {
            pstmt.setString(1, "%" + searchValue + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("name"),
                            rs.getDouble("prince"), 
                            rs.getInt("count"),
                            rs.getString("publish_house")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error querying database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void deleteBook(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement("DELETE FROM book WHERE id = ?")) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted == 0) {
                JOptionPane.showMessageDialog(null, "没有找到要删除的图书", "删除失败", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "图书已成功删除", "删除成功", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting book: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateBook() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(
                     "UPDATE book SET type = ?, name = ?, prince = ?, count = ?, publish_house = ? WHERE id = ?"
             )) {
            pstmt.setString(1, typeField.getText());
            pstmt.setString(2, nameField.getText());
            pstmt.setDouble(3, Double.parseDouble(princeField.getText()));
            pstmt.setInt(4, Integer.parseInt(countField.getText()));
            pstmt.setString(5, publishHouseField.getText());
            pstmt.setInt(6, Integer.parseInt(idField.getText()));
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                JOptionPane.showMessageDialog(this, "更新失败，未找到指定的图书。", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "图书信息已修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // 关闭窗口
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "更新图书时出错: " + ex.getMessage(), "数据库错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}