package futbol;

import java.io.FileOutputStream;
import java.util.Date;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import comba.datos.dao.IntentoDao;
import comba.datos.dao.TipoblancoDao;
import comba.datos.entidades.Intento;
import comba.datos.entidades.Tipoblanco;
import comba.datos.entidades.Usuario;

public class ballTracking {

	private static final boolean debugCammeras = false;

	private static final double cam1angleXOffset = 7f;
	private static final double cam1angleZOffset = -1.39f;
	private static final double cam1angleAB = 43f;

	private static final double cam2angleXOffset = 7f;
	private static final double cam2angleZOffset = 3.89f;
	private static final double cam2angleAB = 51f;

	private static final double cam1and2toFloordist = 2.9f;

	private static double cam1angleZ;
	private static double cam1angleX;

	private static double cam2angleZ;
	private static double cam2angleX;

	public static double cam1pointAx = 1f;
	public static double cam1pointAy = 50f;
	public static double cam1pointBx = 499f;
	public static double cam1pointBy = 109f;

	public static double cam2pointAx = 9f;
	public static double cam2pointAy = 97f;
	public static double cam2pointBx = 633f;
	public static double cam2pointBy = 33f;

	private static final double distCam1Cam2 = 9.3f;
	private static final double camToPantallaDist = 6.47f;

	private static final double cam1distXtoA = -0.14f;

	// Paremeters calibration
	private static double cam1pixInit1 = 36f;
	private static double cam1pixInit2 = 604f;

	private static double cam2pixInit1 = 25f;
	private static double cam2pixInit2 = 631f;

	private static double alfa1, alfa2;

	private static double pixAngleRatioCam1;
	private static double pixAngleRatioCam2;

	private static double cam1pixY;

	private static double ballXposTemp;
	public static double ballXpos;
	private static double ballYposTemp;
	public static double ballYpos;
	private static double ballZposTemp;
	public static double ballZpos;

	public static boolean shoted;
	public static double difference, differenceX, differenceZ;

	private static Mat colorMask = new Mat();
	private static Mat colorMask2 = new Mat();
	private static Mat hsvImg = new Mat();
	private static Mat circles = new Mat();
	
	private static Imshow imshowCam1;
	private static Imshow imshowCam1th;
	private static Imshow imshowCam2;
	private static Imshow imshowCam2th;

	public static double getCam1pixInit1() {
		return cam1pixInit1;
	}

	public static void setCam1pixInit1(double cam1pixInit1) {
		ballTracking.cam1pixInit1 = cam1pixInit1;
	}

	public static double getCam1pixInit2() {
		return cam1pixInit2;
	}

	public static void setCam1pixInit2(double cam1pixInit2) {
		ballTracking.cam1pixInit2 = cam1pixInit2;
	}

	public static double getCam2pixInit1() {
		return cam2pixInit1;
	}

	public static void setCam2pixInit1(double cam2pixInit1) {
		ballTracking.cam2pixInit1 = cam2pixInit1;
	}

	public static double getCam2pixInit2() {
		return cam2pixInit2;
	}

	public static void setCam2pixInit2(double cam2pixInit2) {
		ballTracking.cam2pixInit2 = cam2pixInit2;
	}

	public static double getxPos() {
		return ballXpos;
	}

	public static double getyPos() {
		return cam1pixY;
	}

	public static void setyPos(double yPos) {
		ballTracking.cam1pixY = yPos;
	}

	public static void tracking() throws InterruptedException {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Circle circleCam1 = new Circle();
		Mat frameCam1 = new Mat();
		Mat maskCam1 = new Mat();
		Mat threshedImageCam1 = new Mat();

		Circle circleCam2 = new Circle();
		Mat frameCam2 = new Mat();
		Mat maskCam2 = new Mat();
		Mat threshedImageCam2 = new Mat();


		if (debugCammeras) {
			imshowCam1 = new Imshow("Cámara 1");
			imshowCam1th = new Imshow("Cámara 1 threshold");
			imshowCam2 = new Imshow("Cámara 2");
			imshowCam2th = new Imshow("Cámara 2 threshold");
		}

		// final VideoCapture vc = new VideoCapture();
		// final boolean result = vc.open("video.avi");
		// System.out.println(result);

		if (!FutbolMPanel.captureCam1.isOpened()) {
			System.out.println("ERROR ACQUIRING VIDEO FEED\n");
			return;
		}

		maskCam1 = Highgui.imread("maskCam1.png");

		maskCam2 = Highgui.imread("maskCam2.png");

		while (true) {

			FutbolMPanel.captureCam1.read(frameCam1);
			FutbolMPanel.captureCam2.read(frameCam2);

			getCircle(circleCam1, frameCam1, threshedImageCam1, maskCam1);
			getCircle(circleCam2, frameCam2, threshedImageCam2, maskCam2);

			getAgnleCam1(circleCam1);
			getAgnleCam2(circleCam2);
			
			// Angulos alfa en radianes
			alfa1 = (90 + cam1angleZ) * Math.PI / 180;
			alfa2 = (90 - cam2angleZ) * Math.PI / 180;

			ballYposTemp = ((distCam1Cam2 * Math.sin(alfa1) * Math.sin(alfa2)) / Math.sin(alfa1 + alfa2));
			ballYpos = camToPantallaDist - ballYposTemp;

			ballXposTemp = ballYposTemp / Math.tan(alfa1);
			ballXpos = ballXposTemp + cam1distXtoA;
			ballXpos = ballXpos * 1.04f;

			ballZposTemp = (Math.tan(((cam1angleX + cam2angleX) / 2) * Math.PI / 180) * ballYposTemp);
			ballZpos = cam1and2toFloordist - ballZposTemp - 0.69;
			ballZpos = ballZpos * 1.5f;

			if (ballZpos <= 0)
				ballZpos = 0;

			//if ((circleCam1.detected == true) && circleCam2.detected == true) {
			if (true) {



				FutbolMPanel.labelCam1angleX.setText(Double.toString(cam1angleX));
				FutbolMPanel.labelCam1angleZ.setText(Double.toString(cam1angleZ));
				FutbolMPanel.labelCam2angleX.setText(Double.toString(cam2angleX));
				FutbolMPanel.labelCam2angleZ.setText(Double.toString(cam2angleZ));
				FutbolMPanel.labelBallXpos.setText(Double.toString(ballXpos));
				FutbolMPanel.labelBallYPos.setText(Double.toString(ballYpos));
				FutbolMPanel.labelBallZPos.setText(Double.toString(ballZpos));
				FutbolMPanel.panel_1.repaint();

				if(FutbolMPanel.appGame != null)
					FutbolMPanel.appGame.updateTubo = true;

				if ((ballYpos <= 0.35) && (FutbolMPanel.appGame != null)) {

					if (shoted == true) {


						FutbolMPanel.appGame.updateTubo = true;

						if (FutbolMPanel.rdbtnJugadorVirtual.isSelected()) {
							System.out.println("ballXpos: " + ballXpos);
							difference = ballXpos - FutbolMPanel.appGame.playerPosX;
							FutbolMPanel.appGame.playerStop = true;
						}

						if (FutbolMPanel.rdbtnTarget.isSelected()) {
							differenceX = ballXpos - FutbolMPanel.targetPosition.z;
							differenceZ = ballZpos - FutbolMPanel.targetPosition.y;
							difference = Math.sqrt(differenceX*differenceX + differenceZ*differenceZ);
							if(difference <0.7f){
								FutbolMPanel.appGame.greenTarget = true;
							}
						}

						System.out.println("Difference: " + difference);

						if (FutbolMPanel.fileOut != null)
							FutbolMPanel.fileOut.println(FutbolMPanel.textFieldNombre1.getText() + "," + difference + ","
									+ FutbolMPanel.textFieldPrueba.getText());

						shoted = false;
						
						try{
							//criferlo
							Usuario usu = FutbolMPanel.getUsuarioSeleccionado();
							
							if(usu!=null){
								IntentoDao intentodao = new IntentoDao();
								Intento inte = new Intento();
								
								inte.setPrecision((float) difference);
								
								inte.setFecha_at(new Date());
								
								TipoblancoDao tidao = new TipoblancoDao();
								Tipoblanco obj ;
								
								if(FutbolMPanel.rdbtnJugadorVirtual.isSelected()){
									obj = (Tipoblanco) tidao.seleccionarUno((long)1);
								}
								else{//virtual
									obj = (Tipoblanco) tidao.seleccionarUno((long)2);
								}
								
								inte.setTipoBlanco(obj);
								
								inte.setUsuario(usu);
								intentodao.crear(inte);
							}
							else{
								System.out.println("No ha seleccionado un usuario");
							}
							
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
						
						//end criferlo

					}
				}

			}

			Core.circle(frameCam1, new Point(circleCam1.x, circleCam1.y), (int) circleCam1.r, new Scalar(0, 255, 0, 0), 2);
			Core.circle(frameCam2, new Point(circleCam2.x, circleCam2.y), (int) circleCam2.r, new Scalar(0, 255, 0, 0), 2);

			if (debugCammeras) {
				imshowCam1.showImage(frameCam1);
				imshowCam2.showImage(frameCam2);
				imshowCam1th.showImage(threshedImageCam1);
				imshowCam2th.showImage(threshedImageCam2);
			}
		}

	}

	public static void calculateCam1Parameters() {
		double cam1DifABpix = Math.sqrt((cam1pointAx - cam1pointBx) * (cam1pointAx - cam1pointBx) + (cam1pointAy - cam1pointBy) * (cam1pointAy - cam1pointBy));
		System.out.println("cam1DifABpix: " + cam1DifABpix);

		pixAngleRatioCam1 = cam1DifABpix / cam1angleAB;
		System.out.println("pixAngleRatioCam1: " + pixAngleRatioCam1);

	}

	public static void calculateCam2Parameters() {

		double cam2DifABpix = Math.sqrt((cam2pointAx - cam2pointBx) * (cam2pointAx - cam2pointBx) + (cam2pointAy - cam2pointBy) * (cam2pointAy - cam2pointBy));
		System.out.println("cam2DifABpix: " + cam2DifABpix);

		pixAngleRatioCam2 = cam2DifABpix / cam2angleAB;
		System.out.println("pixAngleRatioCam2: " + pixAngleRatioCam2);

	}

	public static void getCircle(Circle circle, Mat frameIn, Mat threshedImage, Mat mask) {

		Imgproc.blur(frameIn, frameIn, new Size(5, 5));
		
		Imgproc.cvtColor(frameIn, hsvImg, Imgproc.COLOR_BGR2HSV);
		
		//Core.bitwise_and(hsvImg, mask, hsvImg);
		
		// Para el naranja
		Core.inRange(hsvImg, new Scalar(0, 70, 119), new Scalar(31, 255, 255), colorMask);
		// Para el purpura
		Core.inRange(hsvImg, new Scalar(168, 110, 100), new Scalar(181, 234, 255), colorMask2);

		Core.bitwise_or(colorMask, colorMask2, colorMask);
		
		Imgproc.blur(colorMask, colorMask, new Size(15, 15));
		
		Imgproc.threshold(colorMask, threshedImage, 75, 255, Imgproc.THRESH_BINARY);
		
		//Imgproc.HoughCircles(threshedImage, circles, Imgproc.CV_HOUGH_GRADIENT, 0.1f, colorMask.rows() / 4, 500, 10, 8, 15);
		
		Imgproc.blur(threshedImage, threshedImage, new Size(5, 5));	
		
		Imgproc.HoughCircles(threshedImage, circles, Imgproc.CV_HOUGH_GRADIENT, 2f, colorMask.rows() / 4, 100, 20, 2, 15);

		 //System.out.println("mat: " + circles.dump());

		if (circles.get(0, 0) != null) {
			circle.x = circles.get(0, 0)[0];
			circle.y = circles.get(0, 0)[1];
			circle.r = circles.get(0, 0)[2];
			circle.detected = true;

		} else {
			circle.detected = false;
		}

	}

	public static void getAgnleCam1(Circle circle) {

		double a = Math.sqrt((cam1pointBx - circle.x) * (cam1pointBx - circle.x) + (cam1pointBy - circle.y) * (cam1pointBy - circle.y)) / pixAngleRatioCam1;
		double b = Math.sqrt((cam1pointAx - circle.x) * (cam1pointAx - circle.x) + (cam1pointAy - circle.y) * (cam1pointAy - circle.y)) / pixAngleRatioCam1;
		double c = cam1angleAB;
		
		

		double angA = Math.acos((b * b + c * c - a * a) / (2 * b * c));
		double angB = Math.acos((a * a + c * c - b * b) / (2 * a * c));

		double d = (c * ((Math.sin(angA) * Math.sin(angB)) / Math.sin(angA + angB)));
		double e = (d / (Math.tan(angA)));

			
		cam1angleZ = -cam1angleZOffset - e;
		cam1angleX = cam1angleXOffset + d;
	}

	public static void getAgnleCam2(Circle circle) {

		double a = Math.sqrt((cam2pointBx - circle.x) * (cam2pointBx - circle.x) + (cam2pointBy - circle.y) * (cam2pointBy - circle.y)) / pixAngleRatioCam2;
		double b = Math.sqrt((cam2pointAx - circle.x) * (cam2pointAx - circle.x) + (cam2pointAy - circle.y) * (cam2pointAy - circle.y)) / pixAngleRatioCam2;
		double c = cam2angleAB;

		double angA = Math.acos((b * b + c * c - a * a) / (2 * b * c));
		double angB = Math.acos((a * a + c * c - b * b) / (2 * a * c));

		double d = (c * ((Math.sin(angA) * Math.sin(angB)) / Math.sin(angA + angB)));
		double e = (d / (Math.tan(angB)));

		cam2angleZ = cam2angleZOffset + e;
		cam2angleX = cam2angleXOffset + d;
	}

}
