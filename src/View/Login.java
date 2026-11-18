package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;
    private JButton loginButton;
    private JLabel statusLabel;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lirarian";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "yuhang0821";
    public Login() {
        setTitle("图书管理系统");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//居中窗体
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel userLabel = new JLabel("账号:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);
        userTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userTextField, gbc);

        JLabel passwordLabel = new JLabel("密码:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        String[] userTypes = {"borrower", "librarian"};
        userTypeComboBox = new JComboBox<>(userTypes);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 跨越两列
        add(userTypeComboBox, gbc);
        // 调整登录按钮的位置
        loginButton = new JButton("登录");
        gbc.gridx = 0; 
        gbc.gridy = 3; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // 使按钮居中
        add(loginButton, gbc);
        statusLabel = new JLabel("");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(statusLabel, gbc);
        loginButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String userId = userTextField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeComboBox.getSelectedItem();

        UserInfo userInfo = validateLogin(userId, password, userType);
        if (userInfo != null) {
            statusLabel.setText("登录成功!");
            // 显示用户信息（这里只是打印到控制台，实际应用中可以做更多处理）
            System.out.println("用户ID: " + userInfo.getId());
            System.out.println("用户名: " + userInfo.getName());
            System.out.println("用户类型: " + userInfo.getType());
            // 根据用户类型打开不同的窗体
            if ("borrower".equalsIgnoreCase(userInfo.getType())) {
                new BorrowerFace().setVisible(true);
            } else if ("librarian".equalsIgnoreCase(userInfo.getType())) {
                new LibrarianFace().setVisible(true);
            }
            // 关闭登录窗体
            dispose(); // 关闭当前登录窗体
        } else {
            showLoginErrorDialog("账号或密码错误！");
        }
    }
 
    // 创建一个方法来显示登录错误对话框
    private void showLoginErrorDialog(String message) {
        JDialog dialog = new JDialog((Frame) null, "登录错误", true); // 创建一个模态对话框
        dialog.setSize(300, 100);
        dialog.setLayout(new FlowLayout());
        dialog.add(new JLabel(message, SwingConstants.CENTER));
        JButton okButton = new JButton("确定");
        dialog.add(okButton, BorderLayout.SOUTH);
        okButton.addActionListener(event -> dialog.dispose()); // 点击确定按钮关闭对话框
        dialog.setLocationRelativeTo(this); // 对话框相对于登录窗体居中
        dialog.setVisible(true);
    }
    private UserInfo validateLogin(String userId, String password, String userType) {
        UserInfo userInfo = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM " + userType.toLowerCase() + " WHERE id = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        userInfo = new UserInfo(userId, name, userType);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userInfo;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginForm = new Login();
            loginForm.setVisible(true);
        });
    }
}