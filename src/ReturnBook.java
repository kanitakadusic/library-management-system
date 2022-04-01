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
            int issueID = Integer.parseInt(idField.getText());
            String returnDate = dateField.getText();

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE issue SET returnDate = ? WHERE issueID = ?");
                preparedStatement.setDate(1, java.sql.Date.valueOf(returnDate));
                preparedStatement.setInt(2, issueID);
                preparedStatement.executeUpdate();

                JLabel message = new JLabel(" Book has been successfully returned ");
                message.setFont(new Font("Arial", Font.PLAIN, 18));
                message.setForeground(new Color(92, 184, 92));
                JOptionPane.showOptionDialog(null, message, "Book returned", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

                // Update fine column

                try {
                    PreparedStatement prepared = connection.prepareStatement("UPDATE issue SET fine = ? WHERE issueID = ?");

                    // Check if the user is late

                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT userID FROM issue WHERE issueID = ? AND DATE_ADD(issueDate, INTERVAL period WEEK) < ?");
                        statement.setInt(1, issueID);
                        statement.setDate(2, java.sql.Date.valueOf(returnDate));
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            JLabel messageFine = new JLabel(" Fine for delay with 14 days payment deadline ");
                            messageFine.setFont(new Font("Arial", Font.PLAIN, 18));
                            messageFine.setForeground(new Color(217, 83, 79));
                            JOptionPane.showOptionDialog(null, messageFine, "Fine payment", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

                            prepared.setBoolean(1, true);
                        } else {
                            prepared.setBoolean(1, false);
                        }
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }

                    prepared.setInt(2, issueID);
                    prepared.executeUpdate();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

                dispose();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        panel.add(submit);
    }
}