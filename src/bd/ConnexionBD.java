package bd;

import java.sql.*;

public class ConnexionBD {
	private final static String driver = "oracle.jdbc.driver.OracleDriver";
	private final static String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
	private final static String login = "djabiria";
	private final static String mdp = "4305006840L";
	private static Connection instanceConnection = null;

	private ConnexionBD() {

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.getMessage();
			e.printStackTrace();
		}

		try {
			instanceConnection = DriverManager.getConnection(url, login, mdp);

			System.out.println("Connexion à la base de données réussie !");

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	public synchronized static Connection getInstance() {
		if (instanceConnection == null) {
			new ConnexionBD();
		}

		return instanceConnection;
	}

	public static boolean deconnexion() {
		try {
			instanceConnection.close();
			instanceConnection = null;
			System.out.println("Déconnexion à la base de données réussie !");
			return true;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return false;
	}

}
