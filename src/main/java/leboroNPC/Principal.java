package leboroNPC;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Principal {
	
	private static ArrayList<Equipos> listaEquipos = new ArrayList<Equipos>();
	private static ArrayList<Jugadores> listaJugadores = new ArrayList<Jugadores>();
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException {
	//LEER DATOS DE UN FICHERO XML Y GENERAR UN FICHERO CSV (CAMPOS SEPARADOS CON ,)
	//GUARDAR LOS DATOS EN UNA BASE DE DATOS POSTGRES
	leerXML();
	escribirCSV();
	guardarBBDD();
	}

	private static void guardarBBDD() throws SQLException {
		// TODO guardar los datos en una base de datos
		Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost/AD_Control1", "postgres","1234");
		
		PreparedStatement psEq = conexion.prepareStatement("INSERT INTO equipo (codequipo,nombre,ciudad,anho) VALUES (?,?,?,?)");
		for(int i =0; i<listaEquipos.size();i++){
			psEq.setInt(1, listaEquipos.get(i).getCodEquipo());
			psEq.setString(2, listaEquipos.get(i).getNombre());
			psEq.setString(3, listaEquipos.get(i).getCiudad());
			psEq.setInt(4, listaEquipos.get(i).getAnho());
			
			psEq.executeUpdate();
		}
		
		PreparedStatement psJu = conexion.prepareStatement("INSERT INTO jugador (codjugador,nombre,posicion,codequipo) VALUES (?,?,?,?)");
		for(int i =0; i<listaJugadores.size();i++){
			psJu.setInt(1, listaJugadores.get(i).getCodJugador());
			psJu.setString(2, listaJugadores.get(i).getNombre());
			psJu.setString(3, listaJugadores.get(i).getPosicion());
			psJu.setInt(4, listaJugadores.get(i).getCodEquipo());
			
			psJu.executeUpdate();
		}
		
	}

	private static void escribirCSV() throws IOException {
		// TODO crea un archivo CSV a partir de los datos almacenados en el arraylist
		Path ficheroCSV = Paths.get("C:\\PRUEBAS\\EXAMEN\\leboro.csv");
		BufferedWriter bw = Files.newBufferedWriter(ficheroCSV);
			//PARA ESCRIBIR LA PRIMERA PARTE
		for(int i = 0; i< listaEquipos.size(); i++) {
			bw.write(listaEquipos.get(i).getCodEquipo() + "," 
					+ listaEquipos.get(i).getNombre() + "," 
					+ listaEquipos.get(i).getCiudad() + ","
					+ listaEquipos.get(i).getAnho()
					);
		}
			//PARA ESCRIBIR LA SEGUNDA PARTE
		for(int i = 0; i<listaJugadores.size();i++) {
			bw.write(listaJugadores.get(i).getCodJugador() + ","
					+ listaJugadores.get(i).getNombre() + ","
					+ listaJugadores.get(i).getPosicion() + ","
					+ listaJugadores.get(i).getCodEquipo()
					);
		}
		bw.flush();
		
	}

	private static void leerXML() throws ParserConfigurationException, SAXException, IOException {
		// TODO este metodo lee un XML y almacena los datos en arrayList
		FileInputStream fichero = new FileInputStream("C:\\PRUEBAS\\EXAMEN\\leboro.xml");
		
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factoria.newDocumentBuilder();
		
		Document documento = db.parse(fichero);
		
			//PARA LEER EQUIPOS XML
		NodeList equipoCodigo = documento.getElementsByTagName("codequipo");
		NodeList equipoNombre = documento.getElementsByTagName("nombreEq");
		NodeList equipoCiudad = documento.getElementsByTagName("Ciudad");
		NodeList equipoAnho = documento.getElementsByTagName("anho");
		
		for(int i = 0; i<equipoCodigo.getLength(); i++) {
			listaEquipos.add(new Equipos(Integer.parseInt(equipoCodigo.item(i).getTextContent()),
					equipoNombre.item(i).getTextContent(),
					equipoCiudad.item(i).getTextContent(),
					Integer.parseInt(equipoAnho.item(i).getTextContent())
					));
		}
		
			//PARA LEER JUGADORES XML
		NodeList jugadoresCodigo = documento.getElementsByTagName("codjugador");
		NodeList jugadoresNombre = documento.getElementsByTagName("nombreJu");
		NodeList jugadoresPos = documento.getElementsByTagName("posicion");
		NodeList jugadoresCodEq = documento.getElementsByTagName("codequipoJu");
		
		for(int i = 0;i<jugadoresNombre.getLength();i++) {
			listaJugadores.add(new Jugadores(Integer.parseInt(jugadoresCodigo.item(i).getTextContent()),
					jugadoresNombre.item(i).getTextContent(),
					jugadoresPos.item(i).getTextContent(),
					Integer.parseInt(jugadoresCodEq.item(i).getTextContent())
					));
		}
		
	}	
}
