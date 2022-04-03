import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class ReturnBook extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField idField, dateField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public ReturnBook() {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // Issue ID input

        JLabel labelId = new JLabel("Issue ID");
        labelId.setFont(new Font("Arial", Font.PLAIN, 24));
        labelId.setBounds(295, 80, 270, 70);
        panel.add(labelId);

        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 24));
        idField.setBounds(205, 150, 270, 70);
        panel.add(idField);

        // Return date input

        JLabel labelDate = new JLabel("Return date");
        labelDate.setFont(new Font("Arial", Font.PLAIN, 24));
        labelDate.setBounds(585, 80, 270, 70);
        panel.add(labelDate);

        dateField = new JTextField();
        dateField.setFont(new Font("Arial", Font.PLAIN, 24));
        dateField.setBounds(515, 150, 270, 70);
        panel.add(dateField);

        // Submit button

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 24));
        submit.setBounds(360, 265, 270, 70);
        submit.setForeground(new Color(247, 247, 247));
        submit.setBackground(new Color(240, 173, 78));

        submit.addActionListener(e -> {

            // Input validation

            if (Methods.isNotNumber(idField.getText()) || Methods.isNotDate(dateField.getText())) {
                Methods.dialogMessage(" Input field can not be empty  |  Incorrect input data type ", false, "Input validation message");
                return;
            }

            // Execution continues if the entries are correct

            int issueID = Integer.parseInt(idField.getText());
            String returnDate = dateField.getText();

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                // Check if the entered issueID exists

                PreparedStatement issueCheck = connection.prepareStatement("SELECT userID FROM issue WHERE issueID = ?");
                issueCheck.setInt(1, issueID);

                if (!issueCheck.executeQuery().next()) {
                    Methods.dialogMessage(" Non existing database input value ", false, "Input validation message");
                    return;
                }

                // Check if the user is late

                PreparedStatement lateCheck = connection.prepareStatement("SELECT userID FROM issue WHERE issueID = ? AND DATE_ADD(issueDate, INTERVAL period WEEK) < ?");
                lateCheck.setInt(1, issueID);
                lateCheck.setDate(2, java.sql.Date.valueOf(returnDate));

                boolean isLate = lateCheck.executeQuery().next();

                // Table update

                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE issue SET returnDate = ?, fine = ? WHERE issueID = ?");
                preparedStatement.setDate(1, java.sql.Date.valueOf(returnDate));
                preparedStatement.setBoolean(2, isLate);
                preparedStatement.setInt(3, issueID);
                preparedStatement.executeUpdate();

                Methods.dialogMessage(" Book has been successfully returned ", true, "Book returned");

                if (isLate) {
                    Methods.dialogMessage(" Fine for delay with 14 days payment deadline ", false, "Fine payment");
                }

                dispose();
            } catch (SQLException sqlException) {
                Methods.dialogMessage(" Return date can not be before issue date ", false, "Input validation message");
            }
        });

        panel.add(submit);
    }
}