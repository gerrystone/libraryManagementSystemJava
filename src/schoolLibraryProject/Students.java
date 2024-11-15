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

public class Students extends JFrame {

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
                Students frame = new Students();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Students() {
        setTitle("Students");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 759, 460);

        // Set up content pane
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenuItem studentsListMenuItem = new JMenuItem("Students List");
        menuBar.add(studentsListMenuItem);

        JMenuItem addStudentsMenuItem = new JMenuItem("Add Students");
        menuBar.add(addStudentsMenuItem);

        // Table for displaying students
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Load students when the frame loads
        loadStudents();

        // Refresh students list when "Students List" menu item is clicked
        studentsListMenuItem.addActionListener(e -> loadStudents());

        // Open AddStudents form when "Add Students" menu item is clicked
        addStudentsMenuItem.addActionListener(e -> {
            AddStudents addStudentsForm = new AddStudents();
            addStudentsForm.setVisible(true);
        });
    }

    /**
     * Load students from the database into the table.
     */
    public void loadStudents() {
        // Set table column headers
        tableModel.setColumnIdentifiers(new String[]{"Full Name", "Registration Number", "Course"});
        tableModel.setRowCount(0); // Clear any existing rows

        String query = "SELECT full_name, reg_number, course FROM students";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Add rows to the table for each student
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String regNumber = resultSet.getString("reg_number");
                String course = resultSet.getString("course");

                tableModel.addRow(new Object[]{fullName, regNumber, course});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
