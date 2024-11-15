package schoolLibraryProject;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AddUsers extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton addUserButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AddUsers frame = new AddUsers();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public AddUsers() {
        setTitle("Add Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 747, 395);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setBounds(10, 24, 100, 14);
        contentPane.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(10, 39, 671, 28);
        contentPane.add(fullNameField);
        fullNameField.setColumns(10);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(10, 92, 100, 14);
        contentPane.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(10, 105, 671, 28);
        contentPane.add(emailField);
        emailField.setColumns(10);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 159, 100, 14);
        contentPane.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(10, 175, 671, 28);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 227, 100, 14);
        contentPane.add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setBounds(10, 242, 671, 28);
        contentPane.add(passwordField);
        passwordField.setColumns(10);

        addUserButton = new JButton("Add User");
        addUserButton.setBounds(511, 299, 170, 28);
        contentPane.add(addUserButton);

        // Add ActionListener to handle adding the user
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
    }

    /**
     * Add a new user to the database.
     */
    private void addUser() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate inputs
        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SQL query to insert user into the database
        String query = "INSERT INTO users (full_name, email, username, password, user_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, 0); // Default user_type to 0 (Librarian)

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the user.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Clear all input fields.
     */
    private void clearFields() {
        fullNameField.setText("");
        emailField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }
}
