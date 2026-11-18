package Function;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import View.UserInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Queryborrower extends JFrame {

    // JDBC connection details
    private static final String URL = "jdbc:mysql://localhost:3306/lirarian";
    private static final String USER = "root";
    private static final String PASSWORD = "yuhang0821";

    // Variables to store selected book information
    private static int selectedBookId = -1;
    private static String selectedBookName = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("查询图书");
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
        JButton borrowButton = new JButton("借阅");
        JButton cancelButton = new JButton("取消");

        cancelButton.addActionListener(e -> frame.dispose()); // Close the window

        inputPanel.add(searchLabel);
        inputPanel.add(searchComboBox);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(borrowButton);
        inputPanel.add(cancelButton);

        // Disable borrow button initially
        borrowButton.setEnabled(false);

        // Create table to display results
        JTable resultTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("类别");
        tableModel.addColumn("书名");
        tableModel.addColumn("价钱");
        tableModel.addColumn("数量");
        tableModel.addColumn("出版社");
        resultTable.setModel(tableModel);

        // Add table selection listener to enable/disable borrow button
        resultTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = resultTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedBookId = (int) tableModel.getValueAt(selectedRow, 0);
                    selectedBookName = (String) tableModel.getValueAt(selectedRow, 2);
                    borrowButton.setEnabled(true);
                } else {
                    borrowButton.setEnabled(false);
                }
            }
        });

        // Add input panel and table to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultTable), BorderLayout.CENTER);

        // Add action listener to search button
        searchButton.addActionListener(e -> {
            String searchColumn = (String) searchComboBox.getSelectedItem();
            String searchValue = searchField.getText();
            displayBooks(searchColumn, searchValue, tableModel);
        });

        // Add action listener to borrow button
        borrowButton.addActionListener(e -> {
            if (selectedBookId != -1 && !selectedBookName.isEmpty()) {
                // Prompt user for borrower information
                String borrowerId = UserInfo.getId();
                String borrowerName = UserInfo.getName();

                if (borrowerId != null && !borrowerId.isEmpty() && borrowerName != null && !borrowerName.isEmpty()) {
                    try {
                        borrowBook(borrowerId, borrowerName, selectedBookId, selectedBookName);
                        JOptionPane.showMessageDialog(frame, "借阅成功！");
                        // Refresh table after borrowing
                        displayBooks((String) searchComboBox.getSelectedItem(), searchField.getText(), tableModel);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error borrowing book: " + ex.getMessage(), "Borrow Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Make frame visible
        frame.setVisible(true);
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
                            rs.getDouble("prince"), // Assuming price is of type DOUBLE in the database
                            rs.getInt("count"),
                            rs.getString("publish_house")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error querying database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void borrowBook(String borrowerId, String borrowerName, int bookId, String bookName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement borrowPstmt = connection.prepareStatement(
                     "INSERT INTO borrow (B_id, B_name, Book_name, B_date) VALUES (?, ?, ?, ?)"
             );
             PreparedStatement updateBookPstmt = connection.prepareStatement(
                     "UPDATE book SET count = count - 1 WHERE id = ?"
             )) {

            // Record borrow information
            borrowPstmt.setString(1, borrowerId);
            borrowPstmt.setString(2, borrowerName);
            borrowPstmt.setString(3, bookName);
            borrowPstmt.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            borrowPstmt.executeUpdate();

            // Update book count
            updateBookPstmt.setInt(1, bookId);
            updateBookPstmt.executeUpdate();

        }
    }
}