package leboroNPC;

public class Jugadores {
	private int codJugador;
	private String nombre;
	private String posicion;
	private int codEquipo;
	
	public Jugadores() {
		super();
	}

	public Jugadores(int codJugador, String nombre, String posicion, int codEquipo) {
		super();
		this.codJugador = codJugador;
		this.nombre = nombre;
		this.posicion = posicion;
		this.codEquipo = codEquipo;
	}

	public int getCodJugador() {
		return codJugador;
	}

	public void setCodJugador(int codJugador) {
		this.codJugador = codJugador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public int getCodEquipo() {
		return codEquipo;
	}

	public void setCodEquipo(int codEquipo) {
		this.codEquipo = codEquipo;
	}
	
}
