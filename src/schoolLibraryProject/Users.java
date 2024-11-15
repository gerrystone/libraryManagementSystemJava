package schoolLibraryProject;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Users extends JFrame {

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
                Users frame = new Users();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Users() {
        setTitle("Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 768, 449);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout()); // Use BorderLayout for proper placement
        setContentPane(contentPane);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menu Items
        JMenuItem usersListMenuItem = new JMenuItem("User List");
        menuBar.add(usersListMenuItem);

        JMenuItem addNewUsersMenuItem = new JMenuItem("Add New Users");
        menuBar.add(addNewUsersMenuItem);

        // Initialize table and add to scroll pane
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Load users when the frame initializes
        loadUsers();

        // ActionListener for "User List"
        usersListMenuItem.addActionListener(e -> loadUsers());

        // ActionListener for "Add New Users"
        addNewUsersMenuItem.addActionListener(e -> {
            AddUsers addUsersForm = new AddUsers();
            addUsersForm.setVisible(true);
        });
    }

    /**
     * Load users from the database into the table.
     */
    private void loadUsers() {
        // Set table columns
        tableModel.setColumnIdentifiers(new String[]{"Full Name", "Email", "Username"});
        tableModel.setRowCount(0); // Clear existing rows

        String query = "SELECT full_name, email, username FROM users WHERE user_type=0";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Populate the table with user data
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                tableModel.addRow(new Object[]{fullName, email, username});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading users from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
