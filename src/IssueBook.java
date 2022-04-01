import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class IssueBook extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField bookField, userField, dateField, periodField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public IssueBook() {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // User ID input

        JLabel labelUser = new JLabel("User ID");
        labelUser.setFont(new Font("Arial", Font.PLAIN, 24));
        labelUser.setBounds(295, 20, 270, 70);
        panel.add(labelUser);

        userField = new JTextField();
        userField.setFont(new Font("Arial", Font.PLAIN, 24));
        userField.setBounds(205, 90, 270, 70);
        panel.add(userField);

        // Book ID input

        JLabel labelBook = new JLabel("Book ID");
        labelBook.setFont(new Font("Arial", Font.PLAIN, 24));
        labelBook.setBounds(605, 20, 270, 70);
        panel.add(labelBook);

        bookField = new JTextField();
        bookField.setFont(new Font("Arial", Font.PLAIN, 24));
        bookField.setBounds(515, 90, 270, 70);
        panel.add(bookField);

        // Issue date input

        JLabel labelDate = new JLabel("Issue date");
        labelDate.setFont(new Font("Arial", Font.PLAIN, 24));
        labelDate.setBounds(285, 165, 270, 70);
        panel.add(labelDate);

        dateField = new JTextField();
        dateField.setFont(new Font("Arial", Font.PLAIN, 24));
        dateField.setBounds(205, 235, 270, 70);
        panel.add(dateField);

        // Period input

        JLabel labelPeriod = new JLabel("Period");
        labelPeriod.setFont(new Font("Arial", Font.PLAIN, 24));
        labelPeriod.setBounds(615, 165, 270, 70);
        panel.add(labelPeriod);

        periodField = new JTextField();
        periodField.setFont(new Font("Arial", Font.PLAIN, 24));
        periodField.setBounds(515, 235, 270, 70);
        panel.add(periodField);

        // Submit button

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 24));
        submit.setBounds(360, 355, 270, 70);
        submit.setForeground(new Color(247, 247, 247));
        submit.setBackground(new Color(240, 173, 78));

        submit.addActionListener(e -> {
            int bookID = Integer.parseInt(bookField.getText());
            int userID = Integer.parseInt(userField.getText());
            String issueDate = dateField.getText();
            int period = Integer.parseInt(periodField.getText());

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO issue (userID, bookID, issueDate, period) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, bookID);
                preparedStatement.setDate(3, java.sql.Date.valueOf(issueDate));
                preparedStatement.setInt(4, period);
                preparedStatement.executeUpdate();

                JLabel message = new JLabel(" Book has been successfully issued ");
                message.setFont(new Font("Arial", Font.PLAIN, 18));
                message.setForeground(new Color(92, 184, 92));
                JOptionPane.showOptionDialog(null, message, "Book issued", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

                dispose();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        panel.add(submit);
    }
}