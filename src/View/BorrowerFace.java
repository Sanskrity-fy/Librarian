package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.*;

import Function.*;

public class BorrowerFace extends JFrame {
	 private JButton queryButton;
	 private JButton recordButton;
	 private JButton delayButton;
	 private JButton borrowButton;
	 public BorrowerFace() {
	        // 设置窗体属性
	        setTitle("借阅者界面");
	        setSize(1000, 800);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setBackground(Color.LIGHT_GRAY); // 设置窗体背景颜色
	        Font buttonFont = new Font("Serif", Font.BOLD, 28);
	        Color buttonColor = new Color(139, 69, 19); 
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(0, 2, 10, 10)); // 设置网格布局，并添加间距
	        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 添加边框间距
	        queryButton = new JButton("查询和借阅图书");
	        queryButton.setFont(buttonFont);
	        queryButton.setBackground(buttonColor);
	        queryButton.setForeground(Color.WHITE); // 设置字体颜色为白色
	        queryButton.setPreferredSize(new Dimension(200, 50)); // 宽度200，高度50
	        // 为按钮添加动作监听器
	        queryButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // 按钮被点击时执行的代码
	            	Queryborrower newFrame = new Queryborrower();
	                newFrame.setVisible(true);
	                Queryborrower.createAndShowGUI();
	            }
	        });
	        recordButton = new JButton("借阅记录");
	        recordButton.setFont(buttonFont);
	        recordButton.setBackground(buttonColor);
	        recordButton.setForeground(Color.WHITE); // 设置字体颜色为白色
	        recordButton.setPreferredSize(new Dimension(200, 50)); // 宽度200，高度50
	        // 为按钮添加动作监听器
	        recordButton.addActionListener(e -> {
	        	 Borrowerandborrowrecord record = new  Borrowerandborrowrecord();
	            record.setVisible(true);
	        });
	        delayButton = new JButton("延期还书");
	        delayButton.setFont(buttonFont);
	        delayButton.setBackground(buttonColor);
	        delayButton.setForeground(Color.WHITE); // 设置字体颜色为白色
	        delayButton.setPreferredSize(new Dimension(200, 50)); // 宽度200，高度50
	        // 为按钮添加动作监听器
	        delayButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // 按钮被点击时执行的代码
	                JOptionPane.showMessageDialog(null, "Button clicked!");
	            }
	        });
	        borrowButton = new JButton("代借图书");
	        borrowButton.setFont(buttonFont);
			borrowButton.setBackground(buttonColor);	       
	        borrowButton.setForeground(Color.WHITE); // 设置字体颜色为白色
	        borrowButton.setPreferredSize(new Dimension(200, 50)); // 宽度200，高度50
	        // 为按钮添加动作监听器
	        borrowButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // 按钮被点击时执行的代码
	            	Borrowbook newFrame = new Borrowbook();
	                newFrame.setVisible(true);
	            }
	        });
	        panel.add(queryButton);
	        panel.add(delayButton);
	        panel.add(recordButton);
	        panel.add(borrowButton);
	        add(panel);
	        setVisible(true);
	}
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                BorrowerFace borrowerFace = new BorrowerFace();
	                borrowerFace.setVisible(true);
	                // 可以在这里加载借阅者的初始借阅记录等数据
	            }
	        });
	    }
}