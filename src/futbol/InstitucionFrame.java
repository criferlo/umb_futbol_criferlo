package futbol;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import comba.datos.dao.TipociudadDao;
import comba.datos.dao.TipoinstitucionDao;
import comba.datos.entidades.Tipociudad;
import comba.datos.entidades.Tipoinstitucion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InstitucionFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomInstitu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstitucionFrame frame = new InstitucionFrame();
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
	public InstitucionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Agregar Instituci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Guardar");
		
		btnNewButton.setBounds(285, 112, 89, 23);
		contentPane.add(btnNewButton);
		
		txtNomInstitu = new JTextField();
		txtNomInstitu.setBounds(187, 50, 187, 20);
		contentPane.add(txtNomInstitu);
		txtNomInstitu.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre de instituci\u00F3n");
		lblNewLabel.setBounds(67, 53, 110, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblCiuad = new JLabel("Ciudad");
		lblCiuad.setBounds(67, 84, 110, 14);
		contentPane.add(lblCiuad);
		
		
		//by criferlo
		final JComboBox<Tipociudad> cmbCiudad = new JComboBox();
		cmbCiudad.setBounds(187, 81, 187, 20);
		contentPane.add(cmbCiudad);
		
		TipociudadDao tp = new TipociudadDao();
		List<Object> ciudades=tp.seleccionarTodos();
		
		
		DefaultComboBoxModel<Tipociudad> modelo = new DefaultComboBoxModel<Tipociudad>();
		
		for(Object obj:ciudades){
			Tipociudad t =(Tipociudad)obj;
			modelo.addElement(t);
		}
		
		
		cmbCiudad.setModel(modelo);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//by criferlo
				Tipoinstitucion t = new Tipoinstitucion();
				t.setNombre(txtNomInstitu.getText().toString());
				Tipociudad c = (Tipociudad) cmbCiudad.getSelectedItem();
				
				t.setTipoCiudad(c);
				
				TipoinstitucionDao dao = new TipoinstitucionDao();
				try {
					if(!txtNomInstitu.getText().toString().equals("")){
						dao.crear(t);
					}
					dispose();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				FutbolMPanel.inicializarTablas();
				
				//end criferlo
				
			}
		});
		
		//end criferlo
		
	}

	
	
}
