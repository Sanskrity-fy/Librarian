package Function;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Connect.Connect;

public class Add extends JFrame {
    private JTextField idField;
    private JTextField typeField;
    private JTextField nameField;
    private JTextField princeField;
    private JTextField countField;
    private JTextField publishHouseField;

    public Add() {
        setTitle("增加图书");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // 创建面板并设置布局
        JPanel panel = new JPanel(new GridLayout(7, 2));
        // 添加标签和文本字段到面板
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("类别:"));
        typeField = new JTextField();
        panel.add(typeField);

        panel.add(new JLabel("名字:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("价格:"));
        princeField = new JTextField();
        panel.add(princeField);

        panel.add(new JLabel("数量:"));
        countField = new JTextField();
        panel.add(countField);

        panel.add(new JLabel("出版社:"));
        publishHouseField = new JTextField();
        panel.add(publishHouseField);

        // 添加提交按钮到面板
        JButton submitButton = new JButton("添加");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
        panel.add(submitButton);
        panel.add(cancelButton);
        // 添加窗体内容
        add(panel);
    }

    private void addBook() {
        // 从文本字段获取用户输入
        String id = idField.getText();
        String type = typeField.getText();
        String name = nameField.getText();
        String princeStr = princeField.getText();
        String countStr = countField.getText();
        String publishHouse = publishHouseField.getText();

        // 验证输入
        if (id.isEmpty() || name.isEmpty() || princeStr.isEmpty() || countStr.isEmpty() || publishHouse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入所有信息");
            return;
        }
        double prince;
        try {
            prince = Double.parseDouble(princeStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid prnice format.");
            return;
        }

        // 尝试将数量转换为int类型
        int count;
        try {
            count = Integer.parseInt(countStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid count format.");
            return;
        }

        // 连接到数据库并插入数据
        try (Connection connection = Connect.getConnection()) {
            String sql = "INSERT INTO book (id, type, name, prince, count, publish_house) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                statement.setString(2, type);
                statement.setString(3, name);
                statement.setDouble(4, prince);
                statement.setInt(5, count);
                statement.setString(6, publishHouse);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "添加成功!");
                    // 可选：清空文本字段或关闭窗体
                    // idField.setText("");
                    // ...（清空其他字段）
                    dispose(); // 关闭窗体
                } else {
                    JOptionPane.showMessageDialog(this, "添加失败");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding book: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Add().setVisible(true);
            }
        });
    }
}