package Function;
//BorrowRecordsWindow.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Connect.Connect;

public class Borrowrecord extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchIdField; // 新增ID搜索字段
    private JButton searchButton; // 新增查询按钮
    private JButton cancelButton; // 取消按钮

    public Borrowrecord() {
        setTitle("借阅记录");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中
        // 创建表格列名
        String[] columnNames = {"序号", "借阅者ID", "借阅者名字", "借阅图书名字", "借阅日期", "归还日期"};
        tableModel = new DefaultTableModel(columnNames, 0); // 0行，列名已给出
        table = new JTable(tableModel);
        // 将表格放入滚动窗格中
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        // 创建一个面板用于放置按钮和搜索字段
        JPanel buttonPanel = new JPanel();
        searchIdField = new JTextField(10); 
        searchButton = new JButton("查询");
        cancelButton = new JButton("取消");
        // 为查询按钮添加事件监听器
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBorrowRecord(searchIdField.getText());
            }
        });
        // 为取消按钮添加事件监听器
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前窗体
            }
        });
        // 将组件添加到面板
        buttonPanel.add(new JLabel("借阅者ID:"));
        buttonPanel.add(searchIdField);
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        // 将按钮面板添加到窗体的底部
        add(buttonPanel, BorderLayout.NORTH);
    }
    private void searchBorrowRecord(String B_id) {
        String sql;
        if (B_id == null || B_id.trim().isEmpty()) {
            sql = "SELECT * FROM borrow"; // 当ID为空时，查询所有记录
        } else {
            sql = "SELECT * FROM borrow WHERE B_id = ?"; // 使用参数化查询防止SQL注入
        }

        boolean found = false; // 添加一个标志位来记录是否找到了记录

        try (Connection connection = Connect.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (!B_id.trim().isEmpty()) {
                // 只有当ID不为空时才设置查询参数
                stmt.setInt(1, Integer.parseInt(B_id));
            }
            // 执行查询
            ResultSet rs = stmt.executeQuery();
            // 清空表格模型中的数据
            tableModel.setRowCount(0);
            // 加载查询结果到表格中
            while (rs.next()) {
                found = true; // 找到记录，设置标志位
                Object[] row = {
                    rs.getInt("id"),
                    rs.getInt("B_id"),
                    rs.getString("B_name"),
                    rs.getString("Book_name"),
                    rs.getString("B_date"),
                    rs.getString("R_date")
                };
                tableModel.addRow(row);
            }
            // 检查是否找到了记录
            if (!B_id.trim().isEmpty() && !found) {
                JOptionPane.showMessageDialog(this, "未找到ID为 " + B_id + " 的借阅记录。", "查询结果", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的ID（整数）。", "输入错误", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询借阅记录时出错：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Borrowrecord().setVisible(true);
            }
        });
    }
}