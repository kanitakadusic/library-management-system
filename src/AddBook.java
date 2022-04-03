import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class AddBook extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField nameField, genreField, priceField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public AddBook() {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // Name input

        JLabel labelName = new JLabel("Name");
        labelName.setFont(new Font("Arial", Font.PLAIN, 24));
        labelName.setBounds(150, 80, 270, 70);
        panel.add(labelName);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 24));
        nameField.setBounds(50, 150, 270, 70);
        panel.add(nameField);

        // Genre input

        JLabel labelGenre = new JLabel("Genre");
        labelGenre.setFont(new Font("Arial", Font.PLAIN, 24));
        labelGenre.setBounds(460, 80, 270, 70);
        panel.add(labelGenre);

        genreField = new JTextField();
        genreField.setFont(new Font("Arial", Font.PLAIN, 24));
        genreField.setBounds(360, 150, 270, 70);
        panel.add(genreField);

        // Price input

        JLabel labelPrice = new JLabel("Price");
        labelPrice.setFont(new Font("Arial", Font.PLAIN, 24));
        labelPrice.setBounds(775, 80, 270, 70);
        panel.add(labelPrice);

        priceField = new JTextField();
        priceField.setFont(new Font("Arial", Font.PLAIN, 24));
        priceField.setBounds(670, 150, 270, 70);
        panel.add(priceField);

        // Submit button

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 24));
        submit.setBounds(205, 265, 575, 70);
        submit.setForeground(new Color(247, 247, 247));
        submit.setBackground(new Color(92, 184, 92));

        submit.addActionListener(e -> {

            // Input validation

            if (nameField.getText().isEmpty() || genreField.getText().isEmpty() || Methods.isNotNumber(priceField.getText())) {
                Methods.dialogMessage(" Input field can not be empty  |  Incorrect price data type ", false, "Input validation message");
                return;
            }

            // Execution continues if the entries are correct

            String name = nameField.getText();
            String genre = genreField.getText();
            float price = Float.parseFloat(priceField.getText());

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (name, genre, price) VALUES (?, ?, ?)");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, genre);
                preparedStatement.setFloat(3, price);
                preparedStatement.executeUpdate();

                Methods.dialogMessage(" Book has been successfully added ", true, "Book added");

                dispose();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });

        panel.add(submit);
    }
}