import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class ViewBooks extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public ViewBooks() {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books");
            ResultSet resultSet = preparedStatement.executeQuery();

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(0, 1, 992, 505);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Genre");
            tableModel.addColumn("Price");

            JTable table = new JTable(tableModel);
            table.setFont(new Font("Arial", Font.PLAIN, 24));
            table.setRowHeight(50);
            table.setGridColor(new Color(211, 211, 211));
            table.setIntercellSpacing(new Dimension(20, 0));

            JTableHeader header = table.getTableHeader();
            header.setFont(new Font("Arial", Font.PLAIN, 24));
            header.setPreferredSize(new Dimension(scrollPane.getWidth(), 60));
            header.setBackground(new Color(91, 192, 222));

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100);
            columnModel.getColumn(1).setPreferredWidth(420);
            columnModel.getColumn(2).setPreferredWidth(250);
            columnModel.getColumn(3).setPreferredWidth(220);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

            while (resultSet.next()) {
                Object[] row = new Object[4];

                row[0] = resultSet.getString("books.bookID");
                row[1] = resultSet.getString("books.name");
                row[2] = resultSet.getString("books.genre");
                row[3] = resultSet.getString("books.price");

                tableModel.addRow(row);
            }

            scrollPane.getViewport().add(table);
            panel.add(scrollPane);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}