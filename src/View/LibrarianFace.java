package View;

import Function.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianFace extends JFrame {
    private JButton queryButton;
    private JButton recordButton;
    private JButton borrowButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton booktypeButton;
    private JButton printButton;

    public LibrarianFace() {
        // 设置窗体属性
        setTitle("管理员界面");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.LIGHT_GRAY); // 设置窗体背景颜色
        // 设置按钮的字体和颜色
        Font buttonFont = new Font("Serif", Font.BOLD, 28);
        Color buttonColor = new Color(139, 69, 19); // 棕色
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10)); // 设置网格布局，并添加间距
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 添加边框间距
        queryButton = new JButton("图书信息管理");
        queryButton.setFont(buttonFont);
        queryButton.setBackground(buttonColor);
        queryButton.setForeground(Color.WHITE); // 设置字体颜色为白色
        queryButton.setPreferredSize(new Dimension(200, 50)); // 宽度200，高度50
        queryButton.addActionListener(e -> {
            Query query = new Query();
            query.setVisible(true);
            Query.createAndShowGUI();
        });
        recordButton = new JButton("借阅记录");
        recordButton.setFont(buttonFont);
        recordButton.setBackground(buttonColor);
        recordButton.setForeground(Color.WHITE);
        recordButton.setPreferredSize(new Dimension(200, 50));
        recordButton.addActionListener(e -> {
            Borrowrecord borrowrecord = new Borrowrecord();
            borrowrecord.setVisible(true);
        });

        borrowButton = new JButton("借阅归还");
        borrowButton.setFont(buttonFont);
        borrowButton.setBackground(buttonColor);
        borrowButton.setForeground(Color.WHITE);
        borrowButton.setPreferredSize(new Dimension(200, 50));
        borrowButton.addActionListener(e -> {
            Returnbook returnBook = new Returnbook();
            returnBook.setVisible(true);
        });

        addButton = new JButton("添加图书");
        addButton.setFont(buttonFont);
        addButton.setBackground(buttonColor);
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(200, 50));
        addButton.addActionListener(e -> {
            Add add = new Add();
            add.setVisible(true);
        });

        booktypeButton = new JButton("图书分类管理功能");
        booktypeButton.setFont(buttonFont);
        booktypeButton.setBackground(buttonColor);
        booktypeButton.setForeground(Color.WHITE);
        booktypeButton.setPreferredSize(new Dimension(200, 50));
        booktypeButton.addActionListener(e -> {
            BookTypeManager booktype = new BookTypeManager();
            booktype.setVisible(true);
        });
        printButton = new JButton("借阅记录打印");
        printButton.setFont(buttonFont);
        printButton.setBackground(buttonColor);
        printButton.setForeground(Color.WHITE);
        printButton.setPreferredSize(new Dimension(200, 50));
        printButton.addActionListener(e -> {
        	JOptionPane.showMessageDialog(null, "Button clicked!");
        });

        // 添加一个占位按钮，用于可能的未来扩展
        panel.add(queryButton);
        panel.add(recordButton);
        panel.add(borrowButton);
        panel.add(addButton);
        panel.add(booktypeButton);
        panel.add(printButton);
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibrarianFace librarianFace = new LibrarianFace();
            librarianFace.setVisible(true);
        });
    }
}