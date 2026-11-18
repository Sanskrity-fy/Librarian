package Function;
import javax.swing.*;
import Connect.Connect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Update extends JFrame {
    private JTextField idField;
    private JTextField typeField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField countField;
    private JTextField publishHouseField;
    
    public Update() {
        setTitle("修改图书");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));

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
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("数量:"));
        countField = new JTextField();
        panel.add(countField);

        panel.add(new JLabel("出版社:"));
        publishHouseField = new JTextField();
        panel.add(publishHouseField);

        JButton updateButton = new JButton("修改");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
        panel.add(updateButton);
        panel.add(cancelButton);
        add(panel);
    }

    private void updateBook() {
        String id = idField.getText();
        String type = typeField.getText();
        String name = nameField.getText();
        String prince = priceField.getText();
        String count = countField.getText();
        String publishHouse = publishHouseField.getText();

        try (Connection connection = Connect.getConnection()) {
            String sql = "UPDATE book SET type=?, name=?, prince=?, count=?, publish_house=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, type);
                statement.setString(2, name);
                statement.setString(3, prince);
                statement.setString(4, count);
                statement.setString(5, publishHouse);
                statement.setString(6, id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Book updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No book found with the given ID.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating book: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Update().setVisible(true);
            }
        });
    }
}