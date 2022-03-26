import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class ChangePassword extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public ChangePassword(String userName) {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // Enter new password

        JLabel label = new JLabel("Enter new password");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setBounds(385, 80, 270, 70);
        panel.add(label);

        passwordField = new JTextField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordField.setBounds(205, 150, 575, 70);
        panel.add(passwordField);

        // Submit button

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 24));
        submit.setBounds(360, 265, 270, 70);
        submit.setForeground(new Color(247, 247, 247));
        submit.setBackground(new Color(217, 83, 79));

        submit.addActionListener(e -> {
            String password = passwordField.getText();

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                preparedStatement.setString(1, password);
                preparedStatement.setString(2, userName);
                preparedStatement.executeUpdate();

                JLabel message = new JLabel(" Password has been successfully changed ");
                message.setFont(new Font("Arial", Font.PLAIN, 18));
                message.setForeground(new Color(92, 184, 92));
                JOptionPane.showOptionDialog(null, message, "Password changed", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

                dispose();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        panel.add(submit);
    }
}