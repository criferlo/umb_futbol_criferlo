package ballTracking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class FrameTracking extends JFrame implements KeyListener{

	/**
	 * 
	 */
	protected int KeyCode =0;
	protected String  KeyText="";
	private static final long serialVersionUID = 1L;

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub
		KeyCode = arg0.getKeyCode();
		KeyText = KeyEvent.getKeyText(arg0.getKeyCode() );
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



}
