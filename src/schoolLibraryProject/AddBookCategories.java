package schoolLibraryProject;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AddBookCategories extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBookCategories frame = new AddBookCategories();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddBookCategories() {
		setTitle("Add Book Categories");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Category Name");
		lblNewLabel.setBounds(22, 33, 81, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(21, 48, 384, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Add Category");
		btnNewButton.setBounds(263, 87, 142, 28);
		contentPane.add(btnNewButton);

		// Add ActionListener for the button
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String categoryName = textField.getText().trim();
				if (!categoryName.isEmpty()) {
					addCategoryToDatabase(categoryName);
				} else {
					JOptionPane.showMessageDialog(null, "Please enter a category name.");
				}
			}
		});
	}

	/**
	 * Method to add category to the database.
	 */
	private void addCategoryToDatabase(String categoryName) {
		// SQL query to insert category
		String query = "INSERT INTO bookCategories (name) VALUES (?)";

		try (Connection connection = DatabaseConnection.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			
			// Set the category name in the query
			preparedStatement.setString(1, categoryName);
			
			// Execute the query
			int rowsInserted = preparedStatement.executeUpdate();
			
			if (rowsInserted > 0) {
				JOptionPane.showMessageDialog(this, "Category added successfully!");
				textField.setText(""); // Clear the text field
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error adding category to database.");
			e.printStackTrace();
		}
	}
}
