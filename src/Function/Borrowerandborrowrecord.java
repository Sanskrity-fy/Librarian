package Function;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Connect.Connect; // 假设这是你的数据库连接类
import View.UserInfo;  // 假设这是包含getId()方法的类

public class Borrowerandborrowrecord extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private String matchedId = UserInfo.getId(); 

    public Borrowerandborrowrecord() {
        setTitle("借阅记录");
        setSize(600, 450); // 稍微增大窗体高度以容纳按钮
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置表格列名
        String[] columnNames = {"书名", "借阅日期", "归还日期"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // 添加表格到滚动窗格中
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 创建取消按钮
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前窗体
            }
        });

        // 将按钮添加到面板中
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 可选：设置面板中的布局

        // 将面板添加到窗体的底部
        add(buttonPanel, BorderLayout.SOUTH);

        // 从数据库中检索数据并显示在表格中
        retrieveAndDisplayData();

        // 显示窗体
        setVisible(true);
    }

    private void retrieveAndDisplayData() {
        // 使用连接类来获取数据库连接
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT Book_name, B_date, R_date FROM borrow WHERE B_id = ?")) {

            // 设置查询参数
            pstmt.setString(1, matchedId);

            // 执行查询并处理结果集
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String bookName = rs.getString("Book_name");
                    String borrowDate = rs.getString("B_date");
                    String returnDate = rs.getString("R_date");
                    model.addRow(new Object[]{bookName, borrowDate, returnDate});
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "从数据库中检索数据时出错: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 不需要在main方法中再次获取matchedId，因为它已经在构造函数中通过UserInfo.getId()获取了
        SwingUtilities.invokeLater(() -> new Borrowerandborrowrecord());
    }
}