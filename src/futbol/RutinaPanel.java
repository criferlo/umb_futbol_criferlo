package futbol;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class RutinaPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldVelJugVirtual;
	private JTextField textFieldDireccion;
	private JTextField textFieldElevacion;
	private JTextField textFieldVelDerecha;
	private JTextField textFieldVelIzquierda;
	private JTextField textFieldRepeticiones;
	private Rutina editRutina;
	private JButton btnAgregar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RutinaPanel frame = new RutinaPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setVisible(boolean b, Rutina _editRutina) {
		editRutina = _editRutina;
		System.out.println("To edit");
		btnAgregar.setText("Aplicar");
		// TODO:
		imprimirDatos();
		
        super.setVisible(b);
    }

	/**
	 * Create the frame.
	 */
	public RutinaPanel() {
		setTitle("Nueva rutina");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 362, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreDeLa = new JLabel("Nombre de la rutina:");
		lblNombreDeLa.setBounds(12, 12, 164, 15);
		contentPane.add(lblNombreDeLa);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(12, 39, 316, 19);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(12, 70, 316, 134);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblVelocidad = new JLabel("Velocidad:");
		lblVelocidad.setBounds(12, 39, 144, 15);
		panel.add(lblVelocidad);
		
		textFieldVelJugVirtual = new JTextField();
		textFieldVelJugVirtual.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldVelJugVirtual.setText("0");
		textFieldVelJugVirtual.setBounds(190, 37, 114, 19);
		panel.add(textFieldVelJugVirtual);
		textFieldVelJugVirtual.setColumns(10);
		
		JRadioButton rdbtnJugadorVirtual = new JRadioButton("Jugador virtual");
		rdbtnJugadorVirtual.setBounds(12, 8, 149, 23);
		panel.add(rdbtnJugadorVirtual);
		
		JRadioButton rdbtnBlancoAleatoreo = new JRadioButton("Blanco aleatoreo");
		rdbtnBlancoAleatoreo.setBounds(12, 73, 149, 23);
		panel.add(rdbtnBlancoAleatoreo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(12, 216, 316, 154);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblLanzadera = new JLabel("Lanzadera");
		lblLanzadera.setBounds(12, 12, 118, 15);
		panel_1.add(lblLanzadera);
		
		JLabel lblDireccin = new JLabel("Dirección:");
		lblDireccin.setBounds(12, 39, 70, 15);
		panel_1.add(lblDireccin);
		
		JLabel lblElevacin = new JLabel("Elevación:");
		lblElevacin.setBounds(12, 66, 91, 15);
		panel_1.add(lblElevacin);
		
		JLabel lblVelDerecha = new JLabel("Velocidad:");
		lblVelDerecha.setBounds(12, 93, 118, 15);
		panel_1.add(lblVelDerecha);
		
		JLabel lblVelIzquierda = new JLabel("Efecto:");
		lblVelIzquierda.setBounds(12, 120, 118, 15);
		panel_1.add(lblVelIzquierda);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setBounds(190, 37, 114, 19);
		panel_1.add(textFieldDireccion);
		textFieldDireccion.setText("0");
		textFieldDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldDireccion.setColumns(10);
		
		textFieldElevacion = new JTextField();
		textFieldElevacion.setText("0");
		textFieldElevacion.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldElevacion.setColumns(10);
		textFieldElevacion.setBounds(190, 64, 114, 19);
		panel_1.add(textFieldElevacion);
		
		textFieldVelDerecha = new JTextField();
		textFieldVelDerecha.setText("0");
		textFieldVelDerecha.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldVelDerecha.setColumns(10);
		textFieldVelDerecha.setBounds(190, 91, 114, 19);
		panel_1.add(textFieldVelDerecha);
		
		textFieldVelIzquierda = new JTextField();
		textFieldVelIzquierda.setText("0");
		textFieldVelIzquierda.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldVelIzquierda.setColumns(10);
		textFieldVelIzquierda.setBounds(190, 118, 114, 19);
		panel_1.add(textFieldVelIzquierda);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnAgregar.getText().equals("Agregar")){
					guardarRutina();
				}else{
					actualizarRutina();
				}
				FutbolMPanel.imprimirRutinas();
				dispose();
			}
		});
		btnAgregar.setBounds(12, 404, 117, 25);
		contentPane.add(btnAgregar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(211, 404, 117, 25);
		contentPane.add(btnCancelar);
		
		JLabel lblRepeticiones = new JLabel("Repeticiones:");
		lblRepeticiones.setBounds(12, 382, 117, 15);
		contentPane.add(lblRepeticiones);
		
		textFieldRepeticiones = new JTextField();
		textFieldRepeticiones.setText("0");
		textFieldRepeticiones.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldRepeticiones.setColumns(10);
		textFieldRepeticiones.setBounds(123, 380, 114, 19);
		contentPane.add(textFieldRepeticiones);
	}
	
	private void guardarRutina(){
		Rutina nuevaRutina = new Rutina();
		
		nuevaRutina.setNombre(textFieldNombre.getText());
		nuevaRutina.setVelocidadJugadorV(Float.parseFloat(textFieldVelJugVirtual.getText()));
		
		nuevaRutina.setDireccion(Float.parseFloat(textFieldDireccion.getText()));
		nuevaRutina.setElevacion(Float.parseFloat(textFieldElevacion.getText()));
		nuevaRutina.setVelDerecha(Float.parseFloat(textFieldVelDerecha.getText()));
		nuevaRutina.setVelIzquierda(Float.parseFloat(textFieldVelIzquierda.getText()));
		nuevaRutina.setRepeticiones(Integer.parseInt(textFieldRepeticiones.getText()));
		
		FutbolMPanel.getListaRutinas().add(nuevaRutina);
	}
	
	private void imprimirDatos(){
		textFieldNombre.setText(editRutina.getNombre());
		textFieldVelJugVirtual.setText(Float.toString(editRutina.getVelocidadJugadorV()));
		
		textFieldDireccion.setText(Float.toString(editRutina.getDireccion()));
		textFieldElevacion.setText(Float.toString(editRutina.getElevacion()));
		textFieldVelDerecha.setText(Float.toString(editRutina.getVelDerecha()));
		textFieldVelIzquierda.setText(Float.toString(editRutina.getVelIzquierda()));
		
		textFieldRepeticiones.setText(Integer.toString(editRutina.getRepeticiones()));
	}
	private void actualizarRutina(){
		
		editRutina.setNombre(textFieldNombre.getText());
		editRutina.setVelocidadJugadorV(Float.parseFloat(textFieldVelJugVirtual.getText()));
		
		editRutina.setDireccion(Float.parseFloat(textFieldDireccion.getText()));
		editRutina.setElevacion(Float.parseFloat(textFieldElevacion.getText()));
		editRutina.setVelDerecha(Float.parseFloat(textFieldVelDerecha.getText()));
		editRutina.setVelIzquierda(Float.parseFloat(textFieldVelIzquierda.getText()));
		editRutina.setRepeticiones(Integer.parseInt(textFieldRepeticiones.getText()));
	}
}
