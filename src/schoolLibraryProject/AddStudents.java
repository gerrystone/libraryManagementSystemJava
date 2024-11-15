package schoolLibraryProject;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddStudents extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField regNumberField;
    private JComboBox<String> courseComboBox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AddStudents frame = new AddStudents();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public AddStudents() {
        setTitle("Add Student");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 753, 314);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel nameLabel = new JLabel("Student Name");
        nameLabel.setBounds(24, 22, 137, 14);
        contentPane.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(24, 37, 677, 28);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel regNumberLabel = new JLabel("Registration Number");
        regNumberLabel.setBounds(24, 88, 137, 14);
        contentPane.add(regNumberLabel);

        regNumberField = new JTextField();
        regNumberField.setBounds(24, 105, 677, 28);
        contentPane.add(regNumberField);
        regNumberField.setColumns(10);

        JLabel courseLabel = new JLabel("Course");
        courseLabel.setBounds(24, 157, 137, 14);
        contentPane.add(courseLabel);

        courseComboBox = new JComboBox<>();
        courseComboBox.setBounds(24, 172, 677, 28);
        contentPane.add(courseComboBox);

        // Add example courses to the comboBox
        courseComboBox.addItem("Computer Science");
        courseComboBox.addItem("Information Technology");
        courseComboBox.addItem("Business Administration");

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(612, 211, 89, 28);
        contentPane.add(saveButton);

        // Add action listener to handle saving the student
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveStudent();
            }
        });
    }

    /**
     * Save the student to the database.
     */
    private void saveStudent() {
        String fullName = nameField.getText().trim();
        String regNumber = regNumberField.getText().trim();
        String course = (String) courseComboBox.getSelectedItem();

        // Validate inputs
        if (fullName.isEmpty() || regNumber.isEmpty() || course == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO students (full_name, reg_number, course) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, regNumber);
            preparedStatement.setString(3, course);

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the student.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Clear all input fields.
     */
    private void clearFields() {
        nameField.setText("");
        regNumberField.setText("");
        courseComboBox.setSelectedIndex(-1);
    }
}
