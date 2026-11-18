package Function;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Connect.Connect;

public class Delete extends JFrame {
    private JTextField idField;

    public Delete() {
        setTitle("删除图书");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        panel.add(deleteButton);
        panel.add(cancelButton);

        add(panel);
    }

    private void deleteBook() {
        String id = idField.getText();

        try (Connection connection = Connect.getConnection()) {
            String sql = "DELETE FROM book WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "删除成功!");
                    dispose(); // Optionally close the window after successful deletion
                } else {
                    JOptionPane.showMessageDialog(this, "要删除的书不存在");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting book: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Delete().setVisible(true);
            }
        });
    }
}

// Assume DatabaseConnection class is defined elsewhere, similar to the previous example