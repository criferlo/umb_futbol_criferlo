package futbol;

import jssc.SerialPort;
import jssc.SerialPortException;

public class LanzaderaCom {
	private int delayCom = 50;

	private SerialPort serialPort;

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	void setAngle(int angle) {
		try {
			serialPort.writeBytes("angle\r".getBytes());
			Thread.sleep(delayCom);
			serialPort.writeBytes((Integer.toString(angle) + "\r").getBytes());
		} catch (SerialPortException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	void setElevation(int elevation) {
		try {
			serialPort.writeBytes("elevation\r".getBytes());
			Thread.sleep(delayCom);
			serialPort.writeBytes((Integer.toString(elevation) + "\r").getBytes());
		} catch (SerialPortException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	void setSpeed_l(int speed_l) {
		try {
			serialPort.writeBytes("speed_l\r".getBytes());
			Thread.sleep(delayCom);
			serialPort.writeBytes((Integer.toString(speed_l) + "\r").getBytes());
		} catch (SerialPortException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	void setSpeed_r(int speed_r) {
		try {
			serialPort.writeBytes("speed_r\r".getBytes());
			Thread.sleep(delayCom);
			serialPort.writeBytes((Integer.toString(speed_r) + "\r").getBytes());
		} catch (SerialPortException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	void stop() {
		try {
			serialPort.writeBytes("stop\r".getBytes());
			Thread.sleep(delayCom);
		} catch (SerialPortException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	void shot() {
		try {
			if (serialPort != null)
				serialPort.writeBytes("shot\r".getBytes());
			Thread.sleep(delayCom);
		} catch (SerialPortException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	void sendCtrlC() {
		try {
			serialPort.writeByte((byte) 0x03);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

}
