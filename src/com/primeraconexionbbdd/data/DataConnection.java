package com.primeraconexionbbdd.data;

import java.sql.*;
import java.util.ArrayList;

public class DataConnection {
	private static Connection con;
	private static DataConnection INSTANCE = null;

	private DataConnection() {
	};

	public static void performConnection() {
		String host = "localhost:3306";
		String user = "root";
		String pass = "root";
		String dtbs = "alvaroodiiaz";

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dtbs + "?autoReconnect=true&useSSL=false",
					user, pass);
			System.out.println("Conexión establecida.");

		} catch (Exception e) {
			System.out.println("Error en el establecimiento de la connexion.");
		}
	}

	public ArrayList<String> devolverOrdenador() throws SQLException {
		ArrayList<String> ls = new ArrayList<String>();

		PreparedStatement ps = con.prepareStatement("SELECT * FROM ordenadores");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ls.add(rs.getString("id"));
			ls.add(rs.getString("propietario"));
			ls.add(rs.getString("marca"));
		}
		rs.close();
		return ls;

	}

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataConnection();
			performConnection();
		}
	}

	public static DataConnection getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;

	}

	public void insertarOrdenador(String nom, String ord, String proc, int ram, String graf) throws SQLException {

		String seleccio = "INSERT INTO ordenadores (propietario, marca, procesador, ram, grafica) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement ps = con.prepareStatement(seleccio);

		ps.setString(1, nom);

		ps.setString(2, ord);
		
		ps.setString(3, proc);
		
		ps.setInt(4, ram);
		
		ps.setString(5, graf);

		ps.executeUpdate();

	}

	public void actualizarOrdenador(String nom, String cat) throws SQLException {
		String seleccio = "UPDATE ordenadores SET marca=? WHERE id=?";
		PreparedStatement ps = con.prepareStatement(seleccio);

		ps.setString(1, nom);

		ps.setString(2, cat);

		ps.executeUpdate();
	}

	public void borrarOrdenador(int id) throws SQLException {
		String seleccio = "DELETE FROM ordenadores WHERE id=?";
		PreparedStatement ps = con.prepareStatement(seleccio);
		ps.setString(1, id + "");
		ps.executeUpdate();
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println("Error al establecer la conexion.");
		}
	}
}
