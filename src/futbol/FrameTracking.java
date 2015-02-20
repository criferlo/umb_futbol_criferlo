package futbol;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.jogamp.newt.event.MouseEvent;

public class FrameTracking extends JFrame implements KeyListener {

	/**
	 * 
	 */
	protected int KeyCode = 0;
	protected String KeyText = "";
	private static final long serialVersionUID = 1L;
	
	

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		KeyCode = arg0.getKeyCode();
		KeyText = KeyEvent.getKeyText(arg0.getKeyCode());
		System.out.println("Key Pressed: " + KeyText);

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent e) {
	    int x=e.getX();
	    int y=e.getY();
	    System.out.println(x+","+y);//these co-ords are relative to the component
	}

}
