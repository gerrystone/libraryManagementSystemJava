package schoolLibraryProject;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Books extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Books frame = new Books();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Books() {
        setTitle("Library Management - Books");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        // Set up the content pane with BorderLayout
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Create MenuBar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Add Menu Items
        JMenuItem booksListMenuItem = new JMenuItem("Books List");
        menuBar.add(booksListMenuItem);

        JMenuItem addBooksMenuItem = new JMenuItem("Add Books");
        menuBar.add(addBooksMenuItem);

        JMenuItem addBookCategoryMenuItem = new JMenuItem("Add Book Category");
        menuBar.add(addBookCategoryMenuItem);

        JMenuItem viewCategoriesMenuItem = new JMenuItem("View Book Categories");
        menuBar.add(viewCategoriesMenuItem);

        // Initialize table with empty model
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // ActionListener to show Books List in table
        booksListMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadBooks();
            }
        });

        // ActionListener to show Book Categories in table
        viewCategoriesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadCategories();
            }
        });

        // ActionListener to open Add Books form
        addBooksMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddBooks addBooksForm = new AddBooks(Books.this); // Pass Books instance
                addBooksForm.setVisible(true);
            }
        });
        // ActionListener to open Add Book Category form
        addBookCategoryMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddBookCategories addBookCategoryForm = new AddBookCategories();
                addBookCategoryForm.setVisible(true);
            }
        });

        // Load books by default when the frame is initialized
        loadBooks();
    }

    /**
     * Load Books into the table.
     */
    public void loadBooks() {
        // Clear the table
        tableModel.setColumnIdentifiers(new String[]{"Title", "Author", "ISBN", "Category", "Quantity"});
        tableModel.setRowCount(0);  // Clear previous data

        String query = "SELECT title, author, isbn, category, quantity FROM books";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                String category = resultSet.getString("category");
                int quantity = resultSet.getInt("quantity");

                tableModel.addRow(new Object[]{title, author, isbn, category, quantity});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading books from the database.");
        }
    }

    /**
     * Load Categories into the table.
     */
    private void loadCategories() {
        // Clear the table
        tableModel.setColumnIdentifiers(new String[]{"Category Name"});
        tableModel.setRowCount(0);  // Clear previous data

        String query = "SELECT name FROM bookCategories";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String categoryName = resultSet.getString("name");
                tableModel.addRow(new Object[]{categoryName});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories from the database.");
        }
    }
}
