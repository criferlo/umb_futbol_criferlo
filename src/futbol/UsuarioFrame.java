package futbol;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.toedter.calendar.JDayChooser;

import javax.swing.JComboBox;
import javax.swing.JButton;

import com.toedter.components.JSpinField;

import comba.datos.dao.TipoinstitucionDao;
import comba.datos.dao.TiposexoDao;
import comba.datos.dao.UsuarioDao;
import comba.datos.entidades.Tipociudad;
import comba.datos.entidades.Tipoinstitucion;
import comba.datos.entidades.Tiposexo;
import comba.datos.entidades.Usuario;

import javax.swing.JSlider;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

public class UsuarioFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtIdentificacion;
	private JTextField txtNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioFrame frame = new UsuarioFrame();
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
	public UsuarioFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Agregar usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIdentificacin = new JLabel("Identificaci\u00F3n");
		lblIdentificacin.setBounds(43, 38, 79, 14);
		contentPane.add(lblIdentificacin);
		
		txtIdentificacion = new JTextField();
		txtIdentificacion.setBounds(133, 38, 256, 20);
		contentPane.add(txtIdentificacion);
		txtIdentificacion.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(43, 69, 50, 14);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(133, 66, 256, 20);
		contentPane.add(txtNombre);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha nacimiento");
		lblFechaNacimiento.setBounds(43, 94, 90, 14);
		contentPane.add(lblFechaNacimiento);
		
		final JDateChooser dteFecha = new JDateChooser();
		dteFecha.setBounds(133, 91, 256, 20);
		contentPane.add(dteFecha);
		
		JLabel lblEstatura = new JLabel("Estatura(cm)");
		lblEstatura.setBounds(43, 161, 79, 14);
		contentPane.add(lblEstatura);
		
		JLabel lblPeso = new JLabel("Peso(k)");
		lblPeso.setBounds(43, 202, 79, 14);
		contentPane.add(lblPeso);
		
		JLabel lblInstitucin = new JLabel("Instituci\u00F3n");
		lblInstitucin.setBounds(43, 257, 79, 14);
		contentPane.add(lblInstitucin);
		
		final JComboBox cmbInstitucion = new JComboBox();
		cmbInstitucion.setBounds(133, 254, 256, 20);
		contentPane.add(cmbInstitucion);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(43, 122, 79, 14);
		contentPane.add(lblSexo);
		
		final JComboBox cmbSexo = new JComboBox();
		cmbSexo.setBounds(133, 119, 185, 20);
		contentPane.add(cmbSexo);
		
		final JSlider slider = new JSlider();
		slider.setBounds(133, 150, 256, 44);
		contentPane.add(slider);
		
		final JSlider slider_1 = new JSlider();
		slider_1.setBounds(133, 202, 256, 41);
		contentPane.add(slider_1);
		
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					UsuarioDao usudao = new UsuarioDao();
					Usuario usu = new Usuario();
					usu.setCedula(txtIdentificacion.getText().toString());
					usu.setEstatura((long)slider.getValue());
					usu.setFecha_nacimiento(dteFecha.getDate());
					usu.setFecha_registro_at(new Date());
					usu.setNombre_completo(txtNombre.getText().toString());
					usu.setPeso((long)slider_1.getValue());
					usu.setTipoInstitucion((Tipoinstitucion) cmbInstitucion.getSelectedItem());
					TiposexoDao ts = new TiposexoDao();
					if(cmbSexo.getSelectedIndex()==0){
						
						usu.setTipoSexo((Tiposexo) ts.seleccionarUno((long)1));
					}
					else{
						usu.setTipoSexo((Tiposexo) ts.seleccionarUno((long)2));
					}
					
					usudao.crear(usu);
					FutbolMPanel.inicializarTablas();
					dispose();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		});
		btnGuardar.setBounds(300, 285, 89, 23);
		contentPane.add(btnGuardar);
		
		
		
		//cargar datos
		
		cmbSexo.addItem("MASCULINO");
		cmbSexo.addItem("FEMENINO");
		
		slider.setMinimum(0);
		slider.setMaximum(200);
		
		slider_1.setMinimum(30);
		slider_1.setMaximum(120);
		
		slider.setPaintLabels(true);
		slider_1.setPaintLabels(true);
		
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(30);
		slider.setMinorTickSpacing(2);
		
		slider_1.setPaintTicks(true);
		slider_1.setMajorTickSpacing(30);
		slider_1.setMinorTickSpacing(2);
		
		//cargar instituciones
		TipoinstitucionDao insdao = new TipoinstitucionDao();
		try {
			List<Object> ins = insdao.seleccionarTodos();
			
			DefaultComboBoxModel<Tipoinstitucion> modelo = new DefaultComboBoxModel<Tipoinstitucion>();
			
			for(Object obj:ins){
				Tipoinstitucion t =(Tipoinstitucion)obj;
				modelo.addElement(t);
			}
						
			cmbInstitucion.setModel(modelo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
