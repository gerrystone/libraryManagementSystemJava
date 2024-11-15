package schoolLibraryProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AdminDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminDashboard frame = new AdminDashboard();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public AdminDashboard() {
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

        JLabel lblNewLabel = new JLabel("Admin Dashboard");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(10, 11, 722, 22);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        panel.add(lblNewLabel);

        // Books Panel
        JPanel bookspanel = createClickablePanel("Books", 101, 67, Color.LIGHT_GRAY, new Color(200, 200, 200), Books.class);
        contentPane.add(bookspanel);

        // Students Panel
       JPanel studentsPanel = createClickablePanel("Students", 313, 67, Color.LIGHT_GRAY, new Color(200, 200, 200), Students.class);
       contentPane.add(studentsPanel);

        // Users Panel
       JPanel userPanel = createClickablePanel("Users", 549, 67, Color.LIGHT_GRAY, new Color(200, 200, 200), Users.class);
       contentPane.add(userPanel);

        // Transactions Panel
       // JPanel transactionsPanel = createClickablePanel("Transactions", 101, 254, Color.LIGHT_GRAY, new Color(200, 200, 200), TransactionsDashboard.class);
       // contentPane.add(transactionsPanel);

        // Additional Panels
        JPanel panel_1_1_2 = new JPanel();
        panel_1_1_2.setBounds(313, 254, 150, 150);
        contentPane.add(panel_1_1_2);

        JPanel panel_1_1_1_1 = new JPanel();
        panel_1_1_1_1.setBounds(549, 254, 150, 150);
        contentPane.add(panel_1_1_1_1);
    }

    private JPanel createClickablePanel(String label, int x, int y, Color defaultColor, Color hoverColor, Class<? extends JFrame> targetFrame) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, 150, 150);
        panel.setBackground(defaultColor);
        panel.setLayout(null);

        JLabel lbl = new JLabel(label);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setBounds(0, 125, 150, 14);
        panel.add(lbl);

        // Add MouseListener for interactivity
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    JFrame frame = targetFrame.getDeclaredConstructor().newInstance();
                    frame.setVisible(true); // Open the new frame
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(hoverColor); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(defaultColor); // Reset color on exit
            }
        });

        return panel;
    }

}
