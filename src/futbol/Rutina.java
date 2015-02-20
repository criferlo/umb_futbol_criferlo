package futbol;

public class Rutina implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;
	
	// Jugador virtual
	private float velocidadJugadorV;
		
	// Lanzaderader
	private float direccion;
	private float elevacion;
	private float velDerecha;
	private float velIzquierda;
	
	private int repeticiones;
	

	public int getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public float getVelocidadJugadorV() {
		return velocidadJugadorV;
	}
	public void setVelocidadJugadorV(float velocidadJugadorV) {
		this.velocidadJugadorV = velocidadJugadorV;
	}
	public float getDireccion() {
		return direccion;
	}
	public void setDireccion(float direccion) {
		this.direccion = direccion;
	}
	public float getElevacion() {
		return elevacion;
	}
	public void setElevacion(float elevacion) {
		this.elevacion = elevacion;
	}
	public float getVelDerecha() {
		return velDerecha;
	}
	public void setVelDerecha(float velDerecha) {
		this.velDerecha = velDerecha;
	}
	public float getVelIzquierda() {
		return velIzquierda;
	}
	public void setVelIzquierda(float velIzquierda) {
		this.velIzquierda = velIzquierda;
	}
	
	
	

}
