package Function;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import Connect.Connect;

public class Borrowbook extends JFrame {
    private JTextField bookNameField;
    private JTextField borrowerNameField;
    private JTextField borrowerIdField;
    private JPasswordField borrowerPasswordField; // New password field
    private JLabel resultLabel;

    public Borrowbook() {
        setTitle("借书");
        setSize(500, 350); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        JLabel bookNameLabel = new JLabel("书名:");
        bookNameField = new JTextField(35);

        JLabel borrowerNameLabel = new JLabel("借阅者姓名:");
        borrowerNameField = new JTextField(35);

        JLabel borrowerIdLabel = new JLabel("借阅者ID:");
        borrowerIdField = new JTextField(35);

        JLabel borrowerPasswordLabel = new JLabel("借阅者密码:");
        borrowerPasswordField = new JPasswordField(35); // New password field

        JButton borrowButton = new JButton("借阅");
        JButton cancelButton = new JButton("取消");
        resultLabel = new JLabel("");
        resultLabel.setForeground(Color.RED);
        add(bookNameLabel);
        add(bookNameField);
        add(borrowerNameLabel);
        add(borrowerNameField);
        add(borrowerIdLabel);
        add(borrowerIdField);
        add(borrowerPasswordLabel);
        add(borrowerPasswordField); // Add password field to layout
        add(borrowButton);
        add(resultLabel);
        add(cancelButton);

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook(bookNameField.getText(), borrowerNameField.getText(), borrowerIdField.getText(), new String(borrowerPasswordField.getPassword()));
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
    }

    private void borrowBook(String bookName, String borrowerName, String borrowerId, String borrowerPassword) {
        String checkBorrowerSql = "SELECT * FROM borrower WHERE id = ? AND name = ? AND password = ?"; // Updated query to include password
        String sql = "SELECT id, count FROM book WHERE name = ?";
        String insertSql = "INSERT INTO borrow (B_name, B_id, Book_name, B_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = Connect.getConnection();
             PreparedStatement checkPstmt = connection.prepareStatement(checkBorrowerSql);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             PreparedStatement insertPstmt = connection.prepareStatement(insertSql)) {

            checkPstmt.setString(1, borrowerId);
            checkPstmt.setString(2, borrowerName);
            checkPstmt.setString(3, borrowerPassword); // Set password parameter
            ResultSet rsBorrower = checkPstmt.executeQuery();

            if (!rsBorrower.next()) {
                resultLabel.setText("用户名或密码错误，或用户不存在。");
                return;
            }

            pstmt.setString(1, bookName);
            ResultSet rsBook = pstmt.executeQuery();

            if (rsBook.next()) {
                int bookId = rsBook.getInt("id");
                int count = rsBook.getInt("count");

                if (count > 0) {
                    String updateSql = "UPDATE book SET count = count - 1 WHERE id = ?";
                    try (PreparedStatement updatePstmt = connection.prepareStatement(updateSql)) {
                        updatePstmt.setInt(1, bookId);
                        int rowsAffected = updatePstmt.executeUpdate();

                        if (rowsAffected > 0) {
                            // Get current date and time
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String currentDateTime = sdf.format(new Date());
                            // Insert borrow record
                            insertPstmt.setString(1, borrowerName);
                            insertPstmt.setString(2, borrowerId);
                            insertPstmt.setString(3, bookName);
                            insertPstmt.setString(4, currentDateTime);
                            int insertRowsAffected = insertPstmt.executeUpdate();
                            if (insertRowsAffected > 0) {
                                resultLabel.setText("借阅成功！");
                            } else {
                                resultLabel.setText("借阅记录插入失败，请重试。");
                            }
                        } else {
                            resultLabel.setText("借阅失败，请重试。");
                        }
                    }
                } else {
                    resultLabel.setText("该书已无库存，无法借阅。");
                }
            } else {
                resultLabel.setText("未找到该书。");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultLabel.setText("数据库错误：" + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Borrowbook().setVisible(true);
            }
        });
    }
}