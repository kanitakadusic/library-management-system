import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class AddUser extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField usernameField, passwordField, adminField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public AddUser() {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // Username input

        JLabel labelUsername = new JLabel("Username");
        labelUsername.setFont(new Font("Arial", Font.PLAIN, 24));
        labelUsername.setBounds(125, 80, 270, 70);
        panel.add(labelUsername);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 24));
        usernameField.setBounds(50, 150, 270, 70);
        panel.add(usernameField);

        // Password input

        JLabel labelPassword = new JLabel("Password");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 24));
        labelPassword.setBounds(440, 80, 270, 70);
        panel.add(labelPassword);

        passwordField = new JTextField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordField.setBounds(360, 150, 270, 70);
        panel.add(passwordField);

        // Admin check

        JLabel labelAdmin = new JLabel("Admin");
        labelAdmin.setFont(new Font("Arial", Font.PLAIN, 24));
        labelAdmin.setBounds(765, 80, 270, 70);
        panel.add(labelAdmin);

        adminField = new JTextField();
        adminField.setFont(new Font("Arial", Font.PLAIN, 24));
        adminField.setBounds(670, 150, 270, 70);
        panel.add(adminField);

        // Submit button

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 24));
        submit.setBounds(205, 265, 575, 70);
        submit.setForeground(new Color(247, 247, 247));
        submit.setBackground(new Color(92, 184, 92));

        submit.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean admin = Boolean.parseBoolean(adminField.getText());

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, admin) VALUES (?, ?, ?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setBoolean(3, admin);
                preparedStatement.executeUpdate();

                JLabel message = new JLabel(" User has been successfully added ");
                message.setFont(new Font("Arial", Font.PLAIN, 18));
                message.setForeground(new Color(92, 184, 92));
                JOptionPane.showOptionDialog(null, message, "User added", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

                dispose();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        panel.add(submit);
    }
}