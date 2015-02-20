package futbol;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import java.awt.event.*;

public class PuntosInciales implements Runnable {

	private int stateMouseClick;
	private int xCam1Init1;
	private int yCam1Init1;
	private int xCam1Init2;
	private int yCam1Init2;

	private int xCam2Init1;
	private int yCam2Init1;
	private int xCam2Init2;
	private int yCam2Init2;

	private int cammera = 1;

	PuntosInciales(int _cammera) {
		cammera = _cammera;
	}

	public void run() {

		stateMouseClick = 0;

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Imshow showFrame1 = new Imshow("Frame1");
		showFrame1.getFrameLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (cammera == 1) {
					if (stateMouseClick == 0) {
						xCam1Init1 = e.getX();
						yCam1Init1 = e.getY();
						ballTracking.cam1pointAx = xCam1Init1;
						ballTracking.cam1pointAy = yCam1Init1;
						System.out.println("cam1pointAx: " + ballTracking.cam1pointAx);
						System.out.println("cam1pointAy: " + ballTracking.cam1pointAy);
						
					}
					if (stateMouseClick == 1) {
						xCam1Init2 = e.getX();
						yCam1Init2 = e.getY();
						ballTracking.cam1pointBx = xCam1Init2;
						ballTracking.cam1pointBy = yCam1Init2;
						System.out.println("cam1pointBx: " + ballTracking.cam1pointBx);
						System.out.println("cam1pointBy: " + ballTracking.cam1pointBy);
						//ballTracking.calculateCam1Parameters();
					}
					stateMouseClick++;
				} else if (cammera == 2) {
					if (stateMouseClick == 0) {
						xCam2Init1 = e.getX();
						yCam2Init1 = e.getY();
						ballTracking.cam2pointAx = xCam2Init1;
						ballTracking.cam2pointAy = yCam2Init1;
						System.out.println("cam2pointAx: " + ballTracking.cam2pointAx);
						System.out.println("cam2pointAy: " + ballTracking.cam2pointAy);
					}
					if (stateMouseClick == 1) {
						xCam2Init2 = e.getX();
						yCam2Init2 = e.getY();
						ballTracking.cam2pointBx = xCam2Init2;
						ballTracking.cam2pointBy = yCam2Init2;
						System.out.println("cam2pointBx: " + ballTracking.cam2pointBx);
						System.out.println("cam2pointBy: " + ballTracking.cam2pointBy);
						
						//ballTracking.calculateCam2Parameters();
					}
					stateMouseClick++;
				}

			}
		});

		if (!FutbolMPanel.captureCam1.isOpened()) {
			System.out.println("ERROR ACQUIRING VIDEO FEED\n");
			return;
		}

		Mat frame = new Mat();

		while (true) {

			if (cammera == 1) {
				FutbolMPanel.captureCam1.read(frame);
				Core.circle(frame, new Point(xCam1Init1, yCam1Init1), (int) 5, new Scalar(0, 255, 0, 0), 2);

				Core.circle(frame, new Point(xCam1Init2, yCam1Init2), (int) 5, new Scalar(0, 255, 0, 0), 2);
				if (!showFrame1.showImage(frame)) {
					break;
				}
			} else if (cammera == 2) {
				FutbolMPanel.captureCam2.read(frame);
				Core.circle(frame, new Point(xCam2Init1, yCam2Init1), (int) 5, new Scalar(0, 255, 0, 0), 2);

				Core.circle(frame, new Point(xCam2Init2, yCam2Init2), (int) 5, new Scalar(0, 255, 0, 0), 2);
				if (!showFrame1.showImage(frame)) {
					break;
				}
			}

		}
		System.out.println("END");

	}

}
