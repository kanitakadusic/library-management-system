import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class UserLogin extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField nameField;
    private final JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UserLogin frame = new UserLogin();
                frame.setTitle("Login");
                frame.setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public UserLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // Username input

        JLabel nameLabel = new JLabel("Username");
        nameLabel.setBackground(Color.BLACK);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        nameLabel.setBounds(285, 80, 270, 70);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 24));
        nameField.setBounds(205, 150, 270, 70);
        panel.add(nameField);

        // Password input

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordLabel.setBounds(595, 80, 270, 70);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordField.setBounds(515, 150, 270, 70);
        panel.add(passwordField);

        // Login button

        JButton button = new JButton("Login");
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setBounds(360, 265, 270, 70);
        button.setForeground(new Color(247, 247, 247));
        button.setBackground(new Color(2, 117, 216));

        button.addActionListener(e -> {
            String userName = nameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT userID, admin FROM users WHERE username = ? AND password = ?");
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    dispose();

                    if (resultSet.getBoolean("users.admin")) {
                        AdminHome home = new AdminHome(resultSet.getString("users.userID"));
                        home.setTitle("Welcome");
                        home.setVisible(true);
                    } else {
                        UserHome home = new UserHome(resultSet.getString("users.userID"));
                        home.setTitle("Welcome");
                        home.setVisible(true);
                    }
                } else {
                    JLabel message = new JLabel(" Invalid username or password ");
                    message.setFont(new Font("Arial", Font.PLAIN, 18));
                    message.setForeground(new Color(217, 83, 79));
                    JOptionPane.showOptionDialog(null, message, "Access denied", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        panel.add(button);
    }
}