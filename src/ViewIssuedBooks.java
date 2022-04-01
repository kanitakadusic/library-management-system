import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class ViewIssuedBooks extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public ViewIssuedBooks(boolean admin, String userID) {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
            PreparedStatement preparedStatement;

            if (admin) {
                preparedStatement = connection.prepareStatement("SELECT issue.issueID, books.name, books.bookID, users.username, users.userID, issue.issueDate, issue.period FROM ((books INNER JOIN issue ON books.bookID = issue.bookID) INNER JOIN users ON issue.userID = users.userID) WHERE issue.returnDate IS NULL");
            } else {
                preparedStatement = connection.prepareStatement("SELECT issue.issueID, books.name, books.bookID, issue.issueDate, issue.period FROM ((books INNER JOIN issue ON books.bookID = issue.bookID) INNER JOIN users ON issue.userID = users.userID) WHERE users.userID = ? AND issue.returnDate IS NULL");
                preparedStatement.setString(1, userID);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(0, 1, 992, 505);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Book");
            if (admin) tableModel.addColumn("User");
            tableModel.addColumn("Issue date");
            tableModel.addColumn("Period");

            JTable table = new JTable(tableModel);
            table.setFont(new Font("Arial", Font.PLAIN, 24));
            table.setRowHeight(50);
            table.setGridColor(new Color(211, 211, 211));
            table.setIntercellSpacing(new Dimension(20, 0));

            JTableHeader header = table.getTableHeader();
            header.setFont(new Font("Arial", Font.PLAIN, 24));
            header.setPreferredSize(new Dimension(scrollPane.getWidth(), 60));
            header.setBackground(new Color(91, 192, 222));

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

            TableColumnModel columnModel = table.getColumnModel();

            if (admin) {
                columnModel.getColumn(0).setPreferredWidth(90);
                columnModel.getColumn(1).setPreferredWidth(290);
                columnModel.getColumn(2).setPreferredWidth(220);
                columnModel.getColumn(3).setPreferredWidth(210);
                columnModel.getColumn(4).setPreferredWidth(190);

                while (resultSet.next()) {
                    Object[] row = new Object[5];

                    row[0] = resultSet.getString("issue.issueID");
                    row[1] = resultSet.getString("books.name") + " | " + resultSet.getString("books.bookId");
                    row[2] = resultSet.getString("users.username") + " | " + resultSet.getString("users.userID");
                    row[3] = resultSet.getString("issue.issueDate");
                    row[4] = resultSet.getString("issue.period");

                    tableModel.addRow(row);
                }
            } else {
                columnModel.getColumn(0).setPreferredWidth(100);
                columnModel.getColumn(1).setPreferredWidth(420);
                columnModel.getColumn(2).setPreferredWidth(250);
                columnModel.getColumn(3).setPreferredWidth(220);

                while (resultSet.next()) {
                    Object[] row = new Object[4];

                    row[0] = resultSet.getString("issue.issueID");
                    row[1] = resultSet.getString("books.name") + " | " + resultSet.getString("books.bookId");
                    row[2] = resultSet.getString("issue.issueDate");
                    row[3] = resultSet.getString("issue.period");

                    tableModel.addRow(row);
                }
            }

            scrollPane.getViewport().add(table);
            panel.add(scrollPane);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}