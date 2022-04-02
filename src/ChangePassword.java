import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ChangePassword extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public ChangePassword(String userID) {
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
            String validationMessage = passwordValidationMessage(password);

            if (validationMessage.isEmpty()) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET password = ? WHERE userID = ?");
                    preparedStatement.setString(1, password);
                    preparedStatement.setString(2, userID);
                    preparedStatement.executeUpdate();

                    JLabel message = new JLabel(" Password has been successfully changed ");
                    message.setFont(new Font("Arial", Font.PLAIN, 18));
                    message.setForeground(new Color(92, 184, 92));
                    JOptionPane.showOptionDialog(null, message, "Password changed", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

                    dispose();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            } else {
                JLabel message = new JLabel(validationMessage);
                message.setFont(new Font("Arial", Font.PLAIN, 18));
                message.setForeground(new Color(217, 83, 79));
                JOptionPane.showOptionDialog(null, message, "Password validation message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
            }
        });

        panel.add(submit);
    }

    public static String passwordValidationMessage(String password) {
        StringBuilder message = new StringBuilder("<html><body>");

        HashMap<String, String> validation = new HashMap<>();
        validation.put("^(?=.*[a-z]).{1,}$", "add lowercase letter");
        validation.put("^(?=.*[A-Z]).{1,}$", "add uppercase letter");
        validation.put("^(?=.*[0-9]).{1,}$", "add number");
        validation.put("^(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{1,}$", "add special character");
        validation.put("^(?=\\S+$).{1,}$", "remove spaces");
        validation.put("^.{8,20}$", "limit 8-20 characters");

        for (String i : validation.keySet())
            if (!Pattern.compile(i).matcher(password).matches())
                message.append("&nbsp;•&nbsp;&nbsp;").append(validation.get(i)).append("<br>");

        message.append("</body></html>");

        if (message.toString().equals("<html><body></body></html>")) {
            return "";
        }

        return message.toString();
    }
}