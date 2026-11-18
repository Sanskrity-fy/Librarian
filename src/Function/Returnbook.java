package Function;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Returnbook {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection connection;
    private PreparedStatement pstmt1, pstmt2, pstmt3; // 添加 pstmt3 用于查询

    public Returnbook() {
        try {
            // 设置数据库连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lirarian", "root", "yuhang0821");
            // 初始化窗体
            frame = new JFrame("归还图书");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(550, 300);
            frame.setSize(600, 450);
            frame.setLayout(new BorderLayout());

            // 初始化表格
            String[] columnNames = {"序号", "借阅者ID", "借阅者名字", "书名", "借阅日期", "归还日期"};
            tableModel = new DefaultTableModel(columnNames, 0);
            table = new JTable(tableModel);
            loadTableData(null); // 初始加载全部数据

            // 添加滚动窗格
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            // 添加借阅者ID输入框和查询按钮
            JPanel panelTop = new JPanel(new FlowLayout());
            JTextField borrowerIdField = new JTextField(20);
            JButton searchButton = new JButton("查询");

            panelTop.add(new JLabel("借阅者ID:"));
            panelTop.add(borrowerIdField);
            panelTop.add(searchButton);

            frame.add(panelTop, BorderLayout.NORTH);

            // 添加归还和取消按钮
            JPanel panelBottom = new JPanel();
            JButton returnButton = new JButton("归还选择图书");
            JButton cancelButton = new JButton("取消");

            returnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    returnBook();
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); // 关闭窗体
                }
            });

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String borrowerId = borrowerIdField.getText().trim();
                    loadTableData(borrowerId.isEmpty() ? null : borrowerId);
                }
            });

            panelBottom.add(returnButton);
            panelBottom.add(cancelButton);
            frame.add(panelBottom, BorderLayout.SOUTH);

            frame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadTableData(String borrowerId) {
        try (PreparedStatement pstmt = connection.prepareStatement(borrowerId == null ?
                "SELECT id, B_id, B_name, Book_name, B_date, R_date FROM borrow WHERE R_date IS NULL" :
                "SELECT id, B_id, B_name, Book_name, B_date, R_date FROM borrow WHERE B_id = ? AND R_date IS NULL")) {
            if (borrowerId != null) {
                pstmt.setInt(1, Integer.parseInt(borrowerId));
            }
            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0); // 清空表格数据
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("B_id"),
                        rs.getString("B_name"),
                        rs.getString("Book_name"),
                        rs.getString("B_date"),
                        rs.getString("R_date") == null ? "" : rs.getString("R_date")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void returnBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int borrowId = (int) tableModel.getValueAt(selectedRow, 0);

            try (PreparedStatement pstmt1 = connection.prepareStatement("UPDATE borrow SET R_date = ? WHERE id = ?");
                 PreparedStatement pstmt2 = connection.prepareStatement("UPDATE book SET count = count + 1 WHERE id = (SELECT B_id FROM borrow WHERE id = ?)")) {

                pstmt1.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                pstmt1.setInt(2, borrowId);
                pstmt1.executeUpdate();

                pstmt2.setInt(1, borrowId); // 这里应该是根据 borrow 表中的 B_id 来更新 book 表，但这样写存在 SQL 注入风险，实际中应优化
                pstmt2.executeUpdate();

                JOptionPane.showMessageDialog(frame, "归还成功");
                loadTableData(null); // 重新加载全部数据，或者根据当前输入框内容加载

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "请选择图书进行归还");
        }
    }

    public static void main(String[] args) {
        new Returnbook();
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

}