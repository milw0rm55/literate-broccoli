package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");
		
		try {
			HashMap<String,String> datosConexion;
			
			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();		
			
			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;
			
			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			} 

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			//e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		 HashMap<Integer, Deposito> hm_deposito = new  HashMap<Integer, Deposito>();
		   try (PreparedStatement stmt = conn1.prepareStatement("SELECT * FROM depositos")) { 
	            ResultSet rs = stmt.executeQuery(); 
	            while (rs.next()) { 
	                Deposito deposito = new Deposito(
	                		rs.getString("nombre"),
	                		rs.getInt("valor"),
	                		rs.getInt("cantidad")
	                		); 
	                hm_deposito.put(rs.getInt("valor"), deposito); 
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return  hm_deposito;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		 HashMap<String, Dispensador> hm_dispensador = new  HashMap<String, Dispensador>();
		   try (PreparedStatement stmt = conn1.prepareStatement("SELECT * FROM dispensadores")) { 
	            ResultSet rs = stmt.executeQuery(); 
	            while (rs.next()) { 
	                Dispensador dispensador = new Dispensador(
	                		rs.getString("clave"),
	                		rs.getString("nombre"),
	                		rs.getInt("precio"),
	                		rs.getInt("cantidad")
	                		); 
	                hm_dispensador.put(rs.getString("clave"), dispensador); 
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return  hm_dispensador;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		for(Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			try(PreparedStatement stmt = conn1.prepareStatement("UPDATE depositos SET cantidad = ? WHERE depositos.valor = ?")){
			stmt.setInt(2,entry.getKey());
			stmt.setInt(1, entry.getValue().getCantidad());
			stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(
			HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		
		for(Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			try(PreparedStatement stmt = conn1.prepareStatement("UPDATE dispensadores SET cantidad = ? WHERE dispensadores.clave = ?")){
			stmt.setInt(1, entry.getValue().getCantidad());
			stmt.setString(2, entry.getKey());
			stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return todoOK;
	}

} // Fin de la clase