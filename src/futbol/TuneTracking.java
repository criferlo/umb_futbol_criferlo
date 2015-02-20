package futbol;

import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSlider;

import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import com.jogamp.newt.event.MouseEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

public class TuneTracking extends JFrame {

	private JPanel contentPane;
	private JSlider sliderHmin;
	private JSlider sliderSmin;
	private JSlider sliderVmin;
	private JLabel lblHmin;
	private JLabel lblSmin;
	private JLabel lblVmin;
	private JLabel lblHmax;
	private JLabel lblSmax;
	private JLabel lblVmax;
	private JSlider sliderHmax;
	private JSlider sliderSmax;
	private JSlider sliderVmax;
	private JButton btnStartTuning;

	private int stateMouseClick;
	private int xCam1Init1;
	private int yCam1Init1;
	private int xCam1Init2;
	private int yCam1Init2;
	
	VideoCapture capture;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TuneTracking frame = new TuneTracking();
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
	public TuneTracking() {
		setTitle("Tune Tracking");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 542, 403);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblHmin = new JLabel("H:");
		lblHmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println(x + "," + y);// these co-ords are relative to
												// the component

			}
		});
		lblHmin.setBounds(12, 39, 61, 15);
		contentPane.add(lblHmin);

		lblSmin = new JLabel("S:");
		lblSmin.setBounds(12, 57, 61, 15);
		contentPane.add(lblSmin);

		lblVmin = new JLabel("V:");
		lblVmin.setBounds(12, 75, 61, 15);
		contentPane.add(lblVmin);

		sliderHmin = new JSlider();
		sliderHmin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblHmin.setText("H: " + sliderHmin.getValue());
			}
		});
		sliderHmin.setMaximum(255);
		sliderHmin.setBounds(74, 39, 454, 16);
		contentPane.add(sliderHmin);

		sliderSmin = new JSlider();
		sliderSmin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblSmin.setText("S: " + sliderSmin.getValue());
			}
		});
		sliderSmin.setMaximum(255);
		sliderSmin.setBounds(74, 56, 454, 16);
		contentPane.add(sliderSmin);

		sliderVmin = new JSlider();
		sliderVmin.setMaximum(255);
		sliderVmin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblVmin.setText("V: " + sliderVmin.getValue());
			}
		});
		sliderVmin.setBounds(74, 74, 454, 16);
		contentPane.add(sliderVmin);

		lblHmax = new JLabel("H:");
		lblHmax.setBounds(12, 121, 61, 15);
		contentPane.add(lblHmax);

		lblSmax = new JLabel("S:");
		lblSmax.setBounds(12, 139, 61, 15);
		contentPane.add(lblSmax);

		lblVmax = new JLabel("V:");
		lblVmax.setBounds(12, 157, 61, 15);
		contentPane.add(lblVmax);

		sliderHmax = new JSlider();
		sliderHmax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblHmax.setText("H: " + sliderHmax.getValue());
			}
		});
		sliderHmax.setValue(255);
		sliderHmax.setMaximum(255);
		sliderHmax.setBounds(74, 121, 454, 16);
		contentPane.add(sliderHmax);

		sliderSmax = new JSlider();
		sliderSmax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblSmax.setText("S: " + sliderSmax.getValue());
			}
		});
		sliderSmax.setValue(255);
		sliderSmax.setMaximum(255);
		sliderSmax.setBounds(74, 138, 454, 15);
		contentPane.add(sliderSmax);

		sliderVmax = new JSlider();
		sliderVmax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblVmax.setText("V: " + sliderVmax.getValue());
			}
		});
		sliderVmax.setMaximum(255);
		sliderVmax.setBounds(74, 156, 454, 16);
		contentPane.add(sliderVmax);

		btnStartTuning = new JButton("Start Tuning");
		btnStartTuning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tuneThread.start();
			}
		});
		btnStartTuning.setBounds(12, 242, 162, 25);
		contentPane.add(btnStartTuning);

		JButton btnPuntosIniciales = new JButton("Puntos iniciales Cam1");
		btnPuntosIniciales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread p1 = new Thread(new PuntosInciales(1));
				p1.start();
			}
		});
		btnPuntosIniciales.setBounds(12, 293, 212, 25);
		contentPane.add(btnPuntosIniciales);
		
		JButton btnListActives = new JButton("List actives");
		btnListActives.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    Thread t = Thread.currentThread();
			     t.setName("Admin Thread");
			     // set thread priority to 1
			     t.setPriority(1);
			     
			     // prints the current thread
			     System.out.println("Thread = " + t);
			    
			     int count = Thread.activeCount();
			     System.out.println("currently active threads = " + count);
			    
			     Thread th[] = new Thread[count];
			     // returns the number of threads put into the array 
			     Thread.enumerate(th);
			    
			     // prints active threads
			     for (int i = 0; i < count; i++) {
			        System.out.println(i + ": " + th[i]);
			     }
			}
		});
		btnListActives.setBounds(349, 242, 117, 25);
		contentPane.add(btnListActives);
		
		btnPuntosInicialesCam = new JButton("Puntos iniciales Cam2");
		btnPuntosInicialesCam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread p1 = new Thread(new PuntosInciales(2));
				p1.start();
			}
		});
		btnPuntosInicialesCam.setBounds(12, 330, 212, 25);
		contentPane.add(btnPuntosInicialesCam);
		
		btnAMascara = new JButton("A mascara");
		btnAMascara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mat maskCam1 = new Mat();
				Mat maskCam2 = new Mat();
				
				FutbolMPanel.captureCam1.read(maskCam1);
				FutbolMPanel.captureCam2.read(maskCam2);
				
				Highgui.imwrite("maskCam1.png", maskCam1);
				Highgui.imwrite("maskCam2.png", maskCam2);
				
				
				
			}
		});
		btnAMascara.setBounds(251, 330, 117, 25);
		contentPane.add(btnAMascara);
	}

	// tuneThread
	Thread tuneThread = new Thread(new Runnable() {
		@Override
		public void run() {

			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Imshow Frame = new Imshow("Frame1");
			Imshow Frame2 = new Imshow("Frame1");

			final VideoCapture vc = new VideoCapture();
			final boolean result = vc.open("video.avi");

			if (!FutbolMPanel.captureCam2.isOpened()) {
				System.out.println("ERROR ACQUIRING VIDEO FEED\n");
				return;
			}

			Mat frame = new Mat();
			Mat hsvImg = new Mat();

			Mat colorMask = new Mat();

			Mat threshedImage = new Mat();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while (true) {

				FutbolMPanel.captureCam1.read(frame);
				Imgproc.blur(frame, frame, new Size(5, 5));

				Imgproc.cvtColor(frame, hsvImg, Imgproc.COLOR_BGR2HSV);

				Core.inRange(hsvImg, new Scalar(sliderHmin.getValue(),
						sliderSmin.getValue(), sliderVmin.getValue()),
						new Scalar(sliderHmax.getValue(),
								sliderSmax.getValue(), sliderVmax.getValue()),
						colorMask);

				Frame.showImage(frame);

				Frame2.showImage(colorMask);

			}

		}

	});

	// tuneThread
	Thread puntosIniciales = new Thread(new Runnable() {
		@Override
		public void run() {

			stateMouseClick = 0;

			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Imshow showFrame1 = new Imshow("Frame1");
			showFrame1.getFrameLabel().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (stateMouseClick == 0) {
						xCam1Init1 = e.getX();
						yCam1Init1 = e.getY();
						ballTracking.setCam1pixInit1(xCam1Init1);
						System.out.println("setCam1pixInit1: " + xCam1Init1);
					}
					if (stateMouseClick == 1) {
						xCam1Init2 = e.getX();
						yCam1Init2 = e.getY();
						ballTracking.setCam1pixInit2(xCam1Init2);
						System.out.println("setCam1pixInit2: " + xCam1Init2);
						ballTracking.calculateCam1Parameters();
					}
					stateMouseClick++;
				}
			});
			showFrame1.Window.addWindowListener( new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent we) {

	            }
	        } );
			

			capture = new VideoCapture();

			capture.open(0);

			if (!capture.isOpened()) {
				System.out.println("ERROR ACQUIRING VIDEO FEED\n");
				return;
			}

			Mat frame = new Mat();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while (true) {
				capture.read(frame);
				Core.circle(frame, new Point(xCam1Init1, yCam1Init1), (int) 5, new Scalar(0, 255, 0,
						0), 2);
				
				Core.circle(frame, new Point(xCam1Init2, yCam1Init2), (int) 5, new Scalar(0, 255, 0,
						0), 2);
				if(!showFrame1.showImage(frame)){
					break;
				}
			}
			System.out.println("END");

			capture.release();
			


		}
		

	});
	private JButton btnPuntosInicialesCam;
	private JButton btnAMascara;
}
