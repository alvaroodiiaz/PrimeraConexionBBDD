package com.primeraconexionbbdd.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.jdbc.PreparedStatement;
import com.primeraconexionbbdd.data.DataConnection;

public class View {
	static int op;
	private static DataConnection mc = DataConnection.getInstance();

	public static void main(String[] args) throws Exception {
		menu();
		op = readInt();
		exmenu(op);
	}

	private static String readString() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}

	private static int readInt() {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}

	public static void menu() {
		System.out.println("Seleccione una opcion del menú:");
		System.out.println("1. Añadir nuevo ordenador");
		System.out.println("2. Visualizar ordenadores");
		System.out.println("3. Actualizar ordenador");
		System.out.println("4. Eliminar ordenador");
		System.out.println("-1. Salir");
	}

	private static void exmenu(int op2) throws Exception {
		int id;
		String nom;
		String ord;
		String proc;
		String graf;
		int ram;
		boolean ok = false;

		switch (op2) {
		case 1: // Añadir producto
			System.out.println("Nombre del propietario");
			nom = readString();
			System.out.println("Marca del ordenador");
			ord = readString();
			System.out.println("Procesador del ordenador");
			proc = readString();
			System.out.println("Ram del ordenador");
			ram = readInt();
			System.out.println("Grafica del ordenador");
			graf = readString();
			addOrd(nom, ord, proc, ram, graf);
			System.out.println("Ordenador añadido");
			break;
		case 2:
			// Consultar todos los productos
			mostraOrd();
			break;
		case 3:
			System.out.println("Id del propietario");
			nom = readString();
			System.out.println("Ordenador nuevo");
			ord = readString();
			upOrd(ord, nom);
			System.out.println("Ordenador actualizado");
			break;
		case 4:
			System.out.println("Id del producto a eliminar");
			id=readInt();
			delOrd(id);
			System.out.println("Producto eliminado");
			break;
		case -1:
			mc.closeConnection();
			break;
		default:
			menu();
			op = readInt();
			exmenu(op);
			break;
		}
		if (op != -1) {
			System.out.println("Presiona intro para continuar");
			String nada = readString();// no se usa, es para pausar
										// por si ha de salir otra vez el menu para hacer otra accion
			menu();
			op = readInt();
			exmenu(op);
		}
	}

	static void addOrd(String nom, String ord, String proc, int ram, String graf) {
		try {
			mc.insertarOrdenador(nom, ord, proc, ram, graf);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void mostraOrd() {
		try {
			for (int i = 0; i < mc.devolverOrdenador().size(); i++) {
				System.out.println(mc.devolverOrdenador().get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void upOrd(String nom, String cat) {
		try {
			mc.actualizarOrdenador(nom, cat);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void delOrd(int id) {
		try {
			mc.borrarOrdenador(id);
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}
