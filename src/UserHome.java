import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;

public class UserHome extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UserHome frame = new UserHome();
                frame.setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public UserHome() {}

    public UserHome(String userID) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // View books

        JButton viewBooks = new JButton("View books");
        viewBooks.setBackground(UIManager.getColor("Button.disabledForeground"));
        viewBooks.addActionListener(e -> {
            ViewBooks object = new ViewBooks();
            object.setTitle("All library books");
            object.setVisible(true);
        });

        viewBooks.setFont(new Font("Arial", Font.PLAIN, 24));
        viewBooks.setBounds(50, 40, 270, 70);
        viewBooks.setBackground(new Color(91, 192, 222));
        panel.add(viewBooks);

        // View issued books

        JButton viewIssuedBooks = new JButton("View issued books");
        viewIssuedBooks.setBackground(UIManager.getColor("Button.disabledForeground"));
        viewIssuedBooks.addActionListener(e -> {
            ViewIssuedBooks object = new ViewIssuedBooks(false, userID);
            object.setTitle("My issued books");
            object.setVisible(true);
        });

        viewIssuedBooks.setFont(new Font("Arial", Font.PLAIN, 24));
        viewIssuedBooks.setBounds(670, 40, 270, 70);
        viewIssuedBooks.setBackground(new Color(91, 192, 222));
        panel.add(viewIssuedBooks);

        // Issue book

        JButton issueBook = new JButton("Issue book");
        issueBook.setBackground(UIManager.getColor("Button.disabledForeground"));
        issueBook.addActionListener(e -> {
//            IssueBook object = new IssueBook();
//            object.setTitle("Issue book");
//            object.setVisible(true);
        });

        issueBook.setFont(new Font("Arial", Font.PLAIN, 24));
        issueBook.setBounds(205, 145, 270, 70);
        issueBook.setBackground(new Color(240, 173, 78));
        panel.add(issueBook);

        // Return book

        JButton returnBook = new JButton("Return book");
        returnBook.setBackground(UIManager.getColor("Button.disabledForeground"));
        returnBook.addActionListener(e -> {
//            ReturnBook object = new ReturnBook();
//            object.setTitle("Return book");
//            object.setVisible(true);
        });

        returnBook.setFont(new Font("Arial", Font.PLAIN, 24));
        returnBook.setBounds(515, 145, 270, 70);
        returnBook.setBackground(new Color(240, 173, 78));
        panel.add(returnBook);

        // Logout

        JButton logout = new JButton("Logout");
        logout.setBackground(UIManager.getColor("Button.disabledForeground"));
        logout.addActionListener(e -> {
            dispose();

            UserLogin object = new UserLogin();
            object.setTitle("Login");
            object.setVisible(true);
        });

        logout.setFont(new Font("Arial", Font.PLAIN, 24));
        logout.setBounds(50, 355, 425, 70);
        logout.setBackground(new Color(247, 247, 247));
        panel.add(logout);

        // Change password

        JButton changePassword = new JButton("Change password");
        changePassword.setBackground(UIManager.getColor("Button.disabledForeground"));
        changePassword.addActionListener(e -> {
            ChangePassword object = new ChangePassword(userID);
            object.setTitle("Change password");
            object.setVisible(true);
        });

        changePassword.setFont(new Font("Arial", Font.PLAIN, 24));
        changePassword.setBounds(515, 355, 425, 70);
        changePassword.setBackground(new Color(247, 247, 247));
        panel.add(changePassword);
    }
}