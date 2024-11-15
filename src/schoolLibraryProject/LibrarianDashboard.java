package schoolLibraryProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class LibrarianDashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDashboard frame = new AdminDashboard();
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
	public LibrarianDashboard() {
		setTitle("Library Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 452);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(64, 0, 128));
		panel.setBounds(0, 0, 742, 44);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Librarian Dashboard");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 722, 22);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		panel.add(lblNewLabel);
		
		JPanel bookspanel = new JPanel();
		bookspanel.setBounds(101, 67, 150, 150);
		contentPane.add(bookspanel);
		bookspanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Books");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 125, 150, 14);
		bookspanel.add(lblNewLabel_1);
		
		JPanel studentsPanel = new JPanel();
		studentsPanel.setBounds(313, 67, 150, 150);
		contentPane.add(studentsPanel);
		studentsPanel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Students");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(0, 125, 150, 14);
		studentsPanel.add(lblNewLabel_2);
		
		JPanel transactionsPanel = new JPanel();
		transactionsPanel.setBounds(549, 67, 150, 150);
		contentPane.add(transactionsPanel);
		transactionsPanel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Transactions");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(0, 125, 150, 14);
		transactionsPanel.add(lblNewLabel_4);
	}
}
