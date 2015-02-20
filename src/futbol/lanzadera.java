package futbol;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class lanzadera extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lanzadera frame = new lanzadera();
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
	public lanzadera() {
		setTitle("Lanzadera");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_2 = new JLabel("Direccion:");
		label_2.setBounds(24, 60, 90, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("Elevación:");
		label_3.setBounds(24, 87, 90, 15);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("Vel. Izquierda:");
		label_4.setBounds(24, 114, 118, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("Vel. Derecha:");
		label_5.setBounds(24, 141, 118, 15);
		contentPane.add(label_5);
		
		textField = new JTextField();
		textField.setBounds(160, 58, 114, 19);
		contentPane.add(textField);
		textField.setText("0");
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(160, 112, 114, 19);
		contentPane.add(textField_1);
		textField_1.setText("0");
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(160, 139, 114, 19);
		contentPane.add(textField_2);
		textField_2.setText("0");
		textField_2.setColumns(10);
		
		JLabel label_6 = new JLabel("Aplicar");
		label_6.setBounds(292, 29, 70, 15);
		contentPane.add(label_6);
		
		JButton button_1 = new JButton("Ok");
		button_1.setBounds(286, 55, 60, 25);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Ok");
		button_2.setBounds(286, 109, 60, 25);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("Ok");
		button_3.setBounds(286, 136, 60, 25);
		contentPane.add(button_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(160, 85, 114, 19);
		contentPane.add(textField_3);
		textField_3.setText("0");
		textField_3.setColumns(10);
		
		JButton button_4 = new JButton("Ok");
		button_4.setBounds(286, 82, 60, 25);
		contentPane.add(button_4);
		
		JButton button_5 = new JButton("CTRL-C");
		button_5.setBounds(24, 180, 117, 25);
		contentPane.add(button_5);
		
		JButton button_6 = new JButton("Parada de E.");
		button_6.setBounds(24, 223, 157, 25);
		contentPane.add(button_6);
	}
}
