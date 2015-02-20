package futbol;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;

import java.awt.Color;

import comba.datos.dao.*;
import comba.datos.entidades.*;

public class FutbolMPanel {

	// criferlo
	static Usuario usuarioSeleccionado = null;

	public static Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	// end criferlo

	public static boolean multipleTargetRunning = true;

	public static Game appGame;
	private static SerialPort serialPortIzquierda;
	private static SerialPort serialPortDerecha;
	private boolean serialPortConnectedIzquierda = false;
	private boolean serialPortConnectedDerecha = false;
	private LanzaderaCom lanzadera1;
	private LanzaderaCom lanzadera2;

	public static VideoCapture captureCam1;
	public static VideoCapture captureCam2;

	public static Vector3f targetPosition;

	public static PrintWriter fileOut;

	private JFrame frmUmbMquinaFtbol;
	final JFileChooser fc = new JFileChooser();

	private static ArrayList<Rutina> listaRutinas = new ArrayList<Rutina>();
	final static DefaultListModel<String> listModelRutinas = new DefaultListModel<String>();

	public static ArrayList<Rutina> getListaRutinas() {
		return listaRutinas;
	}

	public static void setListaRutinas(ArrayList<Rutina> listaRutinas) {
		FutbolMPanel.listaRutinas = listaRutinas;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FutbolMPanel window = new FutbolMPanel();
					window.frmUmbMquinaFtbol.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FutbolMPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		ballTracking.calculateCam1Parameters();
		ballTracking.calculateCam2Parameters();

		captureCam1 = new VideoCapture();
		captureCam1.open(0);
		captureCam2 = new VideoCapture();
		captureCam2.open(1);

		lanzadera1 = new LanzaderaCom();
		lanzadera2 = new LanzaderaCom();

		frmUmbMquinaFtbol = new JFrame();
		frmUmbMquinaFtbol.setTitle("UMB Máquina Fútbol");
		frmUmbMquinaFtbol.setBounds(100, 100, 736, 669);
		frmUmbMquinaFtbol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUmbMquinaFtbol.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 12, 690, 616);
		frmUmbMquinaFtbol.getContentPane().add(tabbedPane);

		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Datos principales", null, panel_5, null);
		panel_5.setLayout(null);

		JButton btnAgregarInstitucionCris = new JButton("+");
		btnAgregarInstitucionCris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new InstitucionFrame().setVisible(true);

			}
		});
		btnAgregarInstitucionCris.setBounds(513, 75, 41, 23);
		panel_5.add(btnAgregarInstitucionCris);

		tblInstitucionesCris = new JTable();
		tblInstitucionesCris.setBounds(85, 109, 514, 93);
		panel_5.add(tblInstitucionesCris);

		final JComboBox cmbInstitucionesCris = new JComboBox();
		cmbInstitucionesCris.setBounds(85, 76, 162, 20);
		panel_5.add(cmbInstitucionesCris);

		txtBuscarInstitucionesCris = new JTextField();
		txtBuscarInstitucionesCris.setBounds(257, 76, 136, 20);
		panel_5.add(txtBuscarInstitucionesCris);
		txtBuscarInstitucionesCris.setColumns(10);

		JLabel lblJugadores = new JLabel("Instituciones");
		lblJugadores.setBounds(85, 51, 106, 14);
		panel_5.add(lblJugadores);

		JButton btnEliminarInstitucionCris = new JButton("-");
		btnEliminarInstitucionCris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// by criferlo
				TipoinstitucionDao tid = new TipoinstitucionDao();
				int seleccionado = tblInstitucionesCris.getSelectedRow();
				String sele = tblInstitucionesCris.getValueAt(seleccionado, 0).toString();

				try {
					tid.eliminar(tid.seleccionarUno(Long.valueOf(sele)));
					inicializarTablas();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// end criferlo
			}
		});
		btnEliminarInstitucionCris.setBounds(558, 75, 41, 23);
		panel_5.add(btnEliminarInstitucionCris);

		JButton btnBuscarInstitucionesCris = new JButton("Buscar");
		btnBuscarInstitucionesCris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// nombre
				if (cmbInstitucionesCris.getSelectedIndex() == 0) {
					// by criferlo
					TipoinstitucionDao tid = new TipoinstitucionDao();
					List<Tipoinstitucion> lista = tid.seleccionarPorNombre(txtBuscarInstitucionesCris.getText().toString());
					DefaultTableModel modelo = new DefaultTableModel();

					modelo.addColumn("ID");
					modelo.addColumn("INSTITUCION");
					modelo.addColumn("CIUDAD");

					for (Object o : lista) {
						Object[] data = new Object[3];
						Tipoinstitucion tp = (Tipoinstitucion) o;
						data[0] = tp.getId();
						data[1] = tp.getNombre();
						data[2] = tp.getTipoCiudad().getNombre();
						modelo.addRow(data);
					}

					tblInstitucionesCris.setModel(modelo);
				}// ciudad
				else if (cmbInstitucionesCris.getSelectedIndex() == 1) {
					// by criferlo
					TipoinstitucionDao tid = new TipoinstitucionDao();
					List<Tipoinstitucion> lista = tid.seleccionarPorCiudad(txtBuscarInstitucionesCris.getText().toString());
					DefaultTableModel modelo = new DefaultTableModel();

					modelo.addColumn("ID");
					modelo.addColumn("INSTITUCION");
					modelo.addColumn("CIUDAD");

					for (Object o : lista) {
						Object[] data = new Object[3];
						Tipoinstitucion tp = (Tipoinstitucion) o;
						data[0] = tp.getId();
						data[1] = tp.getNombre();
						data[2] = tp.getTipoCiudad().getNombre();
						modelo.addRow(data);
					}

					tblInstitucionesCris.setModel(modelo);
					// end criferlo
				}

			}
		});
		btnBuscarInstitucionesCris.setBounds(403, 75, 89, 23);
		panel_5.add(btnBuscarInstitucionesCris);

		final JComboBox cmbJugadoresCris = new JComboBox();
		cmbJugadoresCris.setBounds(85, 281, 162, 20);
		panel_5.add(cmbJugadoresCris);

		txtBuscarCris = new JTextField();
		txtBuscarCris.setColumns(10);
		txtBuscarCris.setBounds(257, 281, 136, 20);
		panel_5.add(txtBuscarCris);

		JButton btnBuscarCris = new JButton("Buscar");
		btnBuscarCris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// nombre
					if (cmbJugadoresCris.getSelectedIndex() == 0) {
						// by criferlo
						UsuarioDao tid = new UsuarioDao();
						List<Object> lista = tid.seleccionarPorNombre(txtBuscarCris.getText().toString());
						DefaultTableModel modelo = new DefaultTableModel();

						modelo.addColumn("ID");
						modelo.addColumn("CEDULA");
						modelo.addColumn("NOMBRE");

						for (Object o : lista) {
							Object[] data = new Object[3];
							Usuario tp = (Usuario) o;
							data[0] = tp.getId();
							data[1] = tp.getCedula();
							data[2] = tp.getNombre_completo();
							modelo.addRow(data);
						}

						tblJugadoresCris.setModel(modelo);

					}// ciudad
					else if (cmbJugadoresCris.getSelectedIndex() == 1) {
						// by criferlo
						UsuarioDao tid = new UsuarioDao();
						List<Object> lista = tid.seleccionarPorCedula(txtBuscarCris.getText().toString());
						DefaultTableModel modelo = new DefaultTableModel();

						modelo.addColumn("ID");
						modelo.addColumn("CEDULA");
						modelo.addColumn("NOMBRE");

						for (Object o : lista) {
							Object[] data = new Object[3];
							Usuario tp = (Usuario) o;
							data[0] = tp.getId();
							data[1] = tp.getCedula();
							data[2] = tp.getNombre_completo();
							modelo.addRow(data);
						}

						tblJugadoresCris.setModel(modelo);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		btnBuscarCris.setBounds(403, 280, 89, 23);
		panel_5.add(btnBuscarCris);

		JButton btnAgregarJugadorCris = new JButton("+");
		btnAgregarJugadorCris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UsuarioFrame().setVisible(true);
			}
		});
		btnAgregarJugadorCris.setBounds(513, 280, 41, 23);
		panel_5.add(btnAgregarJugadorCris);

		JButton btnEliminarJugadorCris = new JButton("-");
		btnEliminarJugadorCris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// by criferlo
				UsuarioDao tid = new UsuarioDao();
				int seleccionado = tblJugadoresCris.getSelectedRow();
				String sele = tblJugadoresCris.getValueAt(seleccionado, 0).toString();

				try {
					tid.eliminar(tid.seleccionarUno(Long.valueOf(sele)));
					inicializarTablas();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// end criferlo

			}
		});
		btnEliminarJugadorCris.setBounds(558, 280, 41, 23);
		panel_5.add(btnEliminarJugadorCris);

		tblJugadoresCris = new JTable();
		tblJugadoresCris.setBounds(85, 314, 514, 93);
		panel_5.add(tblJugadoresCris);

		JLabel lblInstituciones = new JLabel("Jugadores");
		lblInstituciones.setBounds(85, 260, 106, 14);
		panel_5.add(lblInstituciones);

		JButton btnSeleccionarJugadorCris = new JButton("Seleccionar Jugador");
		btnSeleccionarJugadorCris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// by criferlo
					UsuarioDao tid = new UsuarioDao();
					int seleccionado = tblJugadoresCris.getSelectedRow();
					String sele = tblJugadoresCris.getValueAt(seleccionado, 0).toString();

					usuarioSeleccionado = (Usuario) tid.seleccionarUno(Long.valueOf(sele));
					textField.setText(usuarioSeleccionado.getNombre_completo());

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// end criferlo

			}
		});
		btnSeleccionarJugadorCris.setBounds(437, 416, 162, 23);
		panel_5.add(btnSeleccionarJugadorCris);

		// by criferlo
		cmbInstitucionesCris.addItem("Buscar por nombre");
		cmbInstitucionesCris.addItem("Buscar por ciudad");

		cmbJugadoresCris.addItem("Buscar por nombre");
		cmbJugadoresCris.addItem("Buscar por identificaci�n");

		JLabel lblJugadorSeleccionado = new JLabel("Jugador seleccionado");
		lblJugadorSeleccionado.setBounds(85, 483, 119, 14);
		panel_5.add(lblJugadorSeleccionado);

		textField = new JTextField();
		textField.setBounds(245, 481, 354, 17);
		panel_5.add(textField);
		textField.setColumns(10);

		JLabel lblAtencinConEl = new JLabel("Atenci\u00F3n: con el Jugador seleccionado se grabar\u00E1 las pruebas en base de datos");
		lblAtencinConEl.setBounds(80, 514, 519, 14);
		panel_5.add(lblAtencinConEl);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Rutinas", null, panel_2, null);
		panel_2.setLayout(null);

		final JList<String> listRutinas = new JList<String>(listModelRutinas);
		listRutinas.setBounds(12, 37, 293, 360);
		panel_2.add(listRutinas);

		JLabel lblRutinas = new JLabel("Rutinas: No, (repeticiones): ");
		lblRutinas.setBounds(12, 12, 226, 15);
		panel_2.add(lblRutinas);

		JButton btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(12, 416, 117, 25);
		panel_2.add(btnEjecutar);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(317, 46, 117, 25);
		panel_2.add(btnAgregar);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(317, 83, 117, 25);
		panel_2.add(btnEditar);

		JButton btnSubir = new JButton("Subir");
		btnSubir.setBounds(317, 116, 117, 25);
		panel_2.add(btnSubir);

		JButton btnBajar = new JButton("Bajar");
		btnBajar.setBounds(317, 151, 117, 25);
		panel_2.add(btnBajar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(317, 188, 117, 25);
		panel_2.add(btnEliminar);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(317, 320, 117, 25);
		panel_2.add(btnGuardar);

		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(317, 357, 117, 25);
		panel_2.add(btnAbrir);
		btnAbrir.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showOpenDialog(frmUmbMquinaFtbol);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					// Read from disk using FileInputStream
					FileInputStream f_in;
					try {
						f_in = new FileInputStream(file);

						// Read object using ObjectInputStream
						ObjectInputStream obj_in = new ObjectInputStream(f_in);

						// Read an object
						listaRutinas = (ArrayList<Rutina>) obj_in.readObject();

						obj_in.close();

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}

				} else {
					System.out.println("File canceled");
				}

				imprimirRutinas();

			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showSaveDialog(frmUmbMquinaFtbol);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					try {
						FileOutputStream f_out = new FileOutputStream(file);

						ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

						obj_out.writeObject(listaRutinas);
						obj_out.close();

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					System.out.println("File canceled");
				}

			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = listRutinas.getSelectedIndex();
				listaRutinas.remove(index);
				imprimirRutinas();
			}
		});
		btnBajar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rutina tempRutina1;
				Rutina tempRutina2;

				int index = listRutinas.getSelectedIndex();
				if (index == listaRutinas.size() - 1)
					return;

				tempRutina1 = listaRutinas.get(index);
				tempRutina2 = listaRutinas.get(index + 1);

				listaRutinas.set(index, tempRutina2);
				listaRutinas.set(index + 1, tempRutina1);

				imprimirRutinas();

				listRutinas.setSelectedIndex(index + 1);

			}
		});
		btnSubir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rutina tempRutina1;
				Rutina tempRutina2;

				int index = listRutinas.getSelectedIndex();
				if (index == 0)
					return;

				tempRutina1 = listaRutinas.get(index);
				tempRutina2 = listaRutinas.get(index - 1);

				listaRutinas.set(index, tempRutina2);
				listaRutinas.set(index - 1, tempRutina1);

				imprimirRutinas();

				listRutinas.setSelectedIndex(index - 1);

			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RutinaPanel().setVisible(true, listaRutinas.get(listRutinas.getSelectedIndex()));
			}
		});
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RutinaPanel().setVisible(true);
			}
		});

		panel_1 = new JPanel();
		tabbedPane.addTab("Configuración", null, panel_1, null);
		panel_1.setLayout(null);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setBounds(141, 186, 117, 25);
		panel_1.add(btnIniciar);

		JButton btnLanzar = new JButton("Lanzar");
		btnLanzar.setBounds(12, 186, 117, 25);
		panel_1.add(btnLanzar);

		JButton btnParar = new JButton("Parar");
		btnParar.setBounds(270, 186, 117, 25);
		panel_1.add(btnParar);

		JPanel panel = new JPanel();
		panel.setBounds(12, 223, 328, 113);
		panel_1.add(panel);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setLayout(null);

		JLabel lblLanzadoraDeBalones = new JLabel("Lanzadora de balones izquierda:");
		lblLanzadoraDeBalones.setBounds(12, 12, 289, 15);
		panel.add(lblLanzadoraDeBalones);

		JLabel lblPuertoSerial = new JLabel("Puerto serial: ");
		lblPuertoSerial.setBounds(14, 39, 138, 15);
		panel.add(lblPuertoSerial);

		comboBoxSerialPortIzquierda = new JComboBox<String>();
		comboBoxSerialPortIzquierda.setBounds(117, 34, 147, 24);
		panel.add(comboBoxSerialPortIzquierda);

		btnConectarIzquierda = new JButton("Conectar");
		btnConectarIzquierda.setBounds(12, 66, 138, 25);
		panel.add(btnConectarIzquierda);

		chckbxActivaLanzadera1 = new JCheckBox("Activa");
		chckbxActivaLanzadera1.setBounds(172, 66, 129, 23);
		panel.add(chckbxActivaLanzadera1);

		JButton btnTest = new JButton("Open F.");
		btnTest.setBounds(352, 223, 117, 25);
		panel_1.add(btnTest);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_4.setBounds(12, 348, 328, 113);
		panel_1.add(panel_4);

		JLabel lblLanzadoraDeBalones_1 = new JLabel("Lanzadora de balones derecha:");
		lblLanzadoraDeBalones_1.setBounds(12, 12, 289, 15);
		panel_4.add(lblLanzadoraDeBalones_1);

		JLabel label_1 = new JLabel("Puerto serial: ");
		label_1.setBounds(14, 39, 138, 15);
		panel_4.add(label_1);

		comboBoxSerialPortDerecha = new JComboBox<String>();
		comboBoxSerialPortDerecha.setBounds(117, 34, 147, 24);
		panel_4.add(comboBoxSerialPortDerecha);

		btnConectarDerecha = new JButton("Conectar");
		btnConectarDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (serialPortConnectedDerecha == false) {
					serialPortDerecha = new SerialPort((String) comboBoxSerialPortDerecha.getSelectedItem());
					try {
						serialPortDerecha.openPort();// Open serial port
						System.out.println("Derecha Serial port opend");
						serialPortDerecha.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						serialPortConnectedDerecha = true;

						btnConectarDerecha.setText("Desconectar");
						lanzadera2.setSerialPort(serialPortDerecha);

					} catch (SerialPortException ex) {
						System.out.println(ex);
					}
				} else {
					try {
						serialPortDerecha.closePort();
						System.out.println("Derecha serial port closed");
						serialPortConnectedDerecha = false;
						btnConectarDerecha.setText("Conectar");

					} catch (SerialPortException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnConectarDerecha.setBounds(12, 66, 138, 25);
		panel_4.add(btnConectarDerecha);

		chckbxActivaLanzadera2 = new JCheckBox("Activa");
		chckbxActivaLanzadera2.setBounds(172, 68, 129, 23);
		panel_4.add(chckbxActivaLanzadera2);

		final JButton btnMotiontracking = new JButton("MotionTracking");
		btnMotiontracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				motionThread.start();
				btnMotiontracking.setText("Started");
			}
		});
		btnMotiontracking.setBounds(12, 468, 167, 25);
		panel_1.add(btnMotiontracking);

		JButton btnGetPos = new JButton("Get Pos");
		btnGetPos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pos x: " + ballTracking.getxPos());
				System.out.println("Pos y: " + ballTracking.getyPos());
				// appGame.setTubo(ballTracking.getxPos());
			}
		});
		btnGetPos.setBounds(191, 468, 117, 25);
		panel_1.add(btnGetPos);

		textFieldFileName = new JTextField();
		textFieldFileName.setText("archivo");
		textFieldFileName.setBounds(447, 360, 114, 19);
		panel_1.add(textFieldFileName);
		textFieldFileName.setColumns(10);

		textFieldPrueba = new JTextField();
		textFieldPrueba.setText("pecho");
		textFieldPrueba.setBounds(447, 391, 114, 19);
		panel_1.add(textFieldPrueba);
		textFieldPrueba.setColumns(10);

		JButton btnTerminar = new JButton("Cerrar F.");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileOut.close();
			}
		});
		btnTerminar.setBounds(352, 484, 117, 25);
		panel_1.add(btnTerminar);

		textFieldNombre1 = new JTextField();
		textFieldNombre1.setText("nombre");
		textFieldNombre1.setBounds(447, 325, 114, 19);
		panel_1.add(textFieldNombre1);
		textFieldNombre1.setColumns(10);

		JLabel lblNombre_1 = new JLabel("Nombre");
		lblNombre_1.setBounds(359, 321, 70, 15);
		panel_1.add(lblNombre_1);

		JLabel lblNewLabel = new JLabel("File name");
		lblNewLabel.setBounds(359, 362, 70, 15);
		panel_1.add(lblNewLabel);

		JLabel lblPrueba = new JLabel("Prueba");
		lblPrueba.setBounds(359, 393, 70, 15);
		panel_1.add(lblPrueba);

		JButton btnTuneTracking = new JButton("Tune Tracking");
		btnTuneTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TuneTracking tuneTracking = new TuneTracking();
				tuneTracking.setVisible(true);
			}
		});
		btnTuneTracking.setBounds(12, 505, 167, 25);
		panel_1.add(btnTuneTracking);

		JLabel lblAnguloCam = new JLabel("cam2AngleX:");
		lblAnguloCam.setBounds(12, 78, 117, 15);
		panel_1.add(lblAnguloCam);

		labelCam2angleX = new JLabel("0");
		labelCam2angleX.setBounds(135, 78, 70, 15);
		panel_1.add(labelCam2angleX);

		JLabel lblCamanglex = new JLabel("cam2AngleZ:");
		lblCamanglex.setBounds(12, 105, 117, 15);
		panel_1.add(lblCamanglex);

		labelCam2angleZ = new JLabel("0");
		labelCam2angleZ.setBounds(135, 105, 70, 15);
		panel_1.add(labelCam2angleZ);

		JLabel lblBallxpos = new JLabel("ballXpos:");
		lblBallxpos.setBounds(352, 12, 117, 15);
		panel_1.add(lblBallxpos);

		labelBallXpos = new JLabel("0");
		labelBallXpos.setBounds(475, 12, 70, 15);
		panel_1.add(labelBallXpos);

		JLabel lblBallypos = new JLabel("ballYpos:");
		lblBallypos.setBounds(352, 39, 117, 15);
		panel_1.add(lblBallypos);

		labelBallYPos = new JLabel("0");
		labelBallYPos.setBounds(475, 39, 70, 15);
		panel_1.add(labelBallYPos);

		JLabel lblVelAnimacin = new JLabel("Vel. animación: ");
		lblVelAnimacin.setBounds(171, 132, 122, 15);
		panel_1.add(lblVelAnimacin);

		textFieldVelocidadAnimacion = new JTextField();
		textFieldVelocidadAnimacion.setText("10");
		textFieldVelocidadAnimacion.setBounds(288, 130, 114, 19);
		panel_1.add(textFieldVelocidadAnimacion);
		textFieldVelocidadAnimacion.setColumns(10);

		JLabel lblA = new JLabel("a");
		lblA.setBounds(240, 12, 26, 15);
		panel_1.add(lblA);

		JLabel lblB = new JLabel("b");
		lblB.setBounds(240, 39, 26, 15);
		panel_1.add(lblB);

		labelA = new JLabel("0");
		labelA.setBounds(288, 12, 70, 15);
		panel_1.add(labelA);

		labelB = new JLabel("0");
		labelB.setBounds(287, 39, 70, 15);
		panel_1.add(labelB);

		JLabel lblCamanglex_1 = new JLabel("cam1AngleX:");
		lblCamanglex_1.setBounds(12, 12, 117, 15);
		panel_1.add(lblCamanglex_1);

		JLabel lblCamanglez = new JLabel("cam1AngleZ:");
		lblCamanglez.setBounds(12, 39, 117, 15);
		panel_1.add(lblCamanglez);

		labelCam1angleZ = new JLabel("0");
		labelCam1angleZ.setBounds(135, 39, 70, 15);
		panel_1.add(labelCam1angleZ);

		labelCam1angleX = new JLabel("0");
		labelCam1angleX.setBounds(135, 12, 70, 15);
		panel_1.add(labelCam1angleX);

		JLabel lblBallzpos = new JLabel("ballZpos:");
		lblBallzpos.setBounds(352, 66, 117, 15);
		panel_1.add(lblBallzpos);

		labelBallZPos = new JLabel("0");
		labelBallZPos.setBounds(475, 66, 70, 15);
		panel_1.add(labelBallZPos);

		rdbtnJugadorVirtual = new JRadioButton("Jugador virtual");
		rdbtnJugadorVirtual.setSelected(true);
		rdbtnJugadorVirtual.setBounds(12, 128, 149, 23);
		panel_1.add(rdbtnJugadorVirtual);

		rdbtnTarget = new JRadioButton("Blanco");
		rdbtnTarget.setBounds(12, 155, 149, 23);
		panel_1.add(rdbtnTarget);

		ButtonGroup group1 = new ButtonGroup();
		group1.add(rdbtnJugadorVirtual);
		group1.add(rdbtnTarget);

		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					fileOut = new PrintWriter(new FileWriter(textFieldFileName.getText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Save the Galileo List
				fileOut.println("Datos:");

			}
		});
		btnConectarIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (serialPortConnectedIzquierda == false) {
					serialPortIzquierda = new SerialPort((String) comboBoxSerialPortIzquierda.getSelectedItem());
					try {
						serialPortIzquierda.openPort();// Open serial port
						System.out.println("Izquierda serial port opend");
						serialPortIzquierda.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						serialPortConnectedIzquierda = true;

						btnConectarIzquierda.setText("Desconectar");
						lanzadera1.setSerialPort(serialPortIzquierda);

					} catch (SerialPortException ex) {
						System.out.println(ex);
					}
				} else {
					try {
						serialPortIzquierda.closePort();
						System.out.println("Izquierda serial port closed");
						serialPortConnectedIzquierda = false;
						btnConectarIzquierda.setText("Conectar");

					} catch (SerialPortException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appGame.playerStop = true;
			}
		});
		btnLanzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ballTracking.shoted = true;

				try {
					if (chckbxActivaLanzadera1.isSelected())
						lanzadera1.shot();
					if (chckbxActivaLanzadera2.isSelected())
						lanzadera2.shot();

					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (rdbtnJugadorVirtual.isSelected()) {
					float speed = Float.parseFloat(textFieldVelocidadAnimacion.getText()) / 10;
					appGame.player1.anim("correr1", speed);
				}

				if (rdbtnTarget.isSelected()) {
					targetPosition = new Vector3f(0f, (float) (Math.random() * 1.7f + 0.2f), (float) (Math.random() * 7f + 1f));
					appGame.blueTarget = true;
					appGame.updateTarget = true;
				}

			}
		});
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameThread.start();
			}
		});

		System.out.println("The ports: ");
		String[] portNames = SerialPortList.getPortNames();
		for (int i = 0; i < portNames.length; i++) {
			System.out.println(portNames[i]);
			comboBoxSerialPortIzquierda.addItem(portNames[i]);
			comboBoxSerialPortDerecha.addItem(portNames[i]);
		}

		inicializarTablas();

		// fin by criferlo

	}

	public static void inicializarTablas() {
		TipoinstitucionDao ins = new TipoinstitucionDao();

		List<Object> lista;
		try {
			lista = ins.seleccionarTodos();
			DefaultTableModel modelo = new DefaultTableModel();

			modelo.addColumn("ID");
			modelo.addColumn("INSTITUCION");
			modelo.addColumn("CIUDAD");

			for (Object o : lista) {
				Object[] data = new Object[3];
				Tipoinstitucion tp = (Tipoinstitucion) o;
				data[0] = tp.getId();
				data[1] = tp.getNombre();
				data[2] = tp.getTipoCiudad().getNombre();
				modelo.addRow(data);
			}

			tblInstitucionesCris.setModel(modelo);

			// tabla jugadores
			UsuarioDao usudao = new UsuarioDao();
			List<Object> listaUsu = usudao.seleccionarTodos();
			DefaultTableModel modeloUsu = new DefaultTableModel();

			modeloUsu.addColumn("ID");
			modeloUsu.addColumn("CEDULA");
			modeloUsu.addColumn("NOMBRE COMPLETO");

			for (Object o : listaUsu) {
				Object[] data = new Object[3];
				Usuario u = (Usuario) o;
				data[0] = u.getId();
				data[1] = u.getCedula();
				data[2] = u.getNombre_completo();
				modeloUsu.addRow(data);
			}

			tblJugadoresCris.setModel(modeloUsu);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// Game engine thread
	Thread gameThread = new Thread(new Runnable() {
		@Override
		public void run() {
			appGame = new Game();
			appGame.setShowSettings(false);
			AppSettings settings = new AppSettings(true);
			settings.put("Width", 3072);
			settings.put("Height", 716);

			settings.put("Width", 3072 / 2);
			settings.put("Height", 716 / 2);

			settings.put("Title", "Entrenador de fútbol UMB");
			settings.put("VSync", true);
			// Anti-Aliasing
			settings.put("Samples", 4);

			// No pause when focus is lost
			appGame.setPauseOnLostFocus(false);
			appGame.setSettings(settings);

			appGame.start(); // start the game
		}

	});
	private JComboBox<String> comboBoxSerialPortIzquierda;
	private JButton btnConectarIzquierda;
	private JComboBox<String> comboBoxSerialPortDerecha;
	private JButton btnConectarDerecha;

	public static void imprimirRutinas() {

		listModelRutinas.clear();
		String rep;
		for (int i = 0; i < listaRutinas.size(); i++) {
			rep = Integer.toString(listaRutinas.get(i).getRepeticiones());
			listModelRutinas.addElement((i + 1) + ", (" + rep + "): " + listaRutinas.get(i).getNombre());
		}

	}

	// Game engine thread
	Thread motionThread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				ballTracking.tracking();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	});
	private JTextField textFieldFileName;
	public static JTextField textFieldPrueba;
	public static JTextField textFieldNombre1;
	public static JLabel labelCam2angleX;
	public static JPanel panel_1;
	public static JLabel labelCam2angleZ;
	public static JLabel labelBallXpos;
	public static JLabel labelBallYPos;
	private JTextField textFieldVelocidadAnimacion;
	public static JLabel labelB;
	public static JLabel labelA;
	public static JLabel labelCam1angleZ;
	public static JLabel labelCam1angleX;
	public static JLabel labelBallZPos;
	public static JRadioButton rdbtnJugadorVirtual;
	public static JRadioButton rdbtnTarget;

	// Game engine thread
	Thread multipleTarget = new Thread(new Runnable() {

		@Override
		public void run() {
			multipleTargetRunning = true;

			while (multipleTargetRunning) {

				ballTracking.shoted = true;

				try {
					if (chckbxActivaLanzadera1.isSelected())
						lanzadera1.shot();
					if (chckbxActivaLanzadera2.isSelected())
						lanzadera2.shot();

					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (rdbtnJugadorVirtual.isSelected()) {
					float speed = Float.parseFloat(textFieldVelocidadAnimacion.getText()) / 10;
					appGame.player1.anim("correr1", speed);
				}

				if (rdbtnTarget.isSelected()) {
					targetPosition = new Vector3f(0f, (float) (Math.random() * 1.7f + 0.2f), (float) (Math.random() * 7f + 1f));
					appGame.blueTarget = true;
					appGame.updateTarget = true;
				}

				while (ballTracking.shoted) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	});
	private static JCheckBox chckbxActivaLanzadera1;
	private static JCheckBox chckbxActivaLanzadera2;
	private static JTable tblInstitucionesCris;
	private JTextField txtBuscarInstitucionesCris;
	private JTextField txtBuscarCris;
	private static JTable tblJugadoresCris;
	private JTextField textField;
}
