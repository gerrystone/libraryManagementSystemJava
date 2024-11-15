package schoolLibraryProject;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AddBooks extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JComboBox<String> comboBox;
    private Books booksInstance; 
   
    public AddBooks(Books booksInstance) {
    	this.booksInstance = booksInstance;
        setTitle("Add Books");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 554, 441);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Book Title");
        lblNewLabel.setBounds(24, 22, 80, 14);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("ISBN Number");
        lblNewLabel_1.setBounds(24, 85, 80, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Author");
        lblNewLabel_2.setBounds(24, 148, 80, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Category");
        lblNewLabel_3.setBounds(24, 214, 80, 14);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Quantity");
        lblNewLabel_4.setBounds(24, 274, 80, 14);
        contentPane.add(lblNewLabel_4);

        textField = new JTextField();
        textField.setBounds(24, 36, 495, 28);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(24, 100, 495, 28);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(24, 161, 495, 28);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        comboBox = new JComboBox<>();
        comboBox.setBounds(24, 227, 495, 28);
        contentPane.add(comboBox);

        textField_3 = new JTextField();
        textField_3.setBounds(24, 288, 495, 28);
        contentPane.add(textField_3);
        textField_3.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(430, 345, 89, 28);
        contentPane.add(btnSave);

        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(323, 348, 89, 28);
        contentPane.add(btnReset);

        // Load categories into the comboBox when the form is initialized
        loadCategories();

        // Add ActionListener to the Save button
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBook();
            }
        });
    }

    
    private void loadCategories() {
		String query = "SELECT name FROM bookCategories";

		try (Connection connection = DatabaseConnection.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(query);
		     ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				// Add each category name to the comboBox
				String categoryName = resultSet.getString("name");
				comboBox.addItem(categoryName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
   
    
    private void saveBook() {
        String title = textField.getText().trim();
        String isbn = textField_1.getText().trim();
        String author = textField_2.getText().trim();
        String category = (String) comboBox.getSelectedItem();
        String quantityText = textField_3.getText().trim();

        // Validate inputs
        if (title.isEmpty() || isbn.isEmpty() || author.isEmpty() || category == null || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
            return;
        }

        // SQL query to insert book details
        String query = "INSERT INTO books (title, isbn, author, category, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, isbn);
            preparedStatement.setString(3, author);
            preparedStatement.setString(4, category);
            preparedStatement.setInt(5, quantity);

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                booksInstance.loadBooks();
                clearFields(); // Clear the fields after successful save
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving book to the database.");
            e.printStackTrace();
        }
    }

    /**
     * Method to clear the fields
     */
    private void clearFields() {
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        comboBox.setSelectedIndex(-1);
        textField_3.setText("");
    }
}
