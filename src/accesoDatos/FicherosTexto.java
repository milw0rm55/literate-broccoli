package accesoDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {
	File fDis = new File("Ficheros\\datos\\dispensadores.txt"); // FicheroDispensadores
	File fDep = new File("Ficheros\\datos\\depositos.txt"); // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> hmDep = new HashMap<>();

		if (fDep.exists()) {
			try {
				// InputStream is = new FileInputStream(fDis);

				String line;
				BufferedReader reader = new BufferedReader(new FileReader(fDep));
				while ((line = reader.readLine()) != null) {
					String[] abc = line.split(";");
					if (abc.length >= 2) {
						int key = Integer.parseInt(abc[1]);
						String nombre = abc[0];
						int cantidad = Integer.parseInt(abc[2]);
						Deposito dep = new Deposito(nombre, key, cantidad);
						hmDep.put(key, dep);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El fichero no existe");
		}
		return hmDep;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> hmDis = new HashMap<>();
		if (fDis.exists()) {
			try {
				String line1;
				BufferedReader reader = new BufferedReader(new FileReader(fDis));
				while ((line1 = reader.readLine()) != null) {
					String[] abc = line1.split(";");
					if (abc.length >= 2) {

						String key = (abc[0]);
						String nombre = abc[1];
						int precio = Integer.parseInt(abc[2]);
						int cantidad = Integer.parseInt(abc[3]);

						Dispensador dep = new Dispensador(key, nombre, precio, cantidad);
						hmDis.put(key, dep);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El fichero no existe");
		}
		return hmDis;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean todoOK = true;
        try {
            FileWriter fw = new FileWriter(fDep, false);
            BufferedWriter bw = new BufferedWriter(fw) ;
            fw.write("");
             bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		  for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
		        try {
		            FileWriter fw = new FileWriter(fDep, true);
		            BufferedWriter bw = new BufferedWriter(fw) ;
		            fw.write(entry.getValue().getNombreMoneda() + ";");
		            fw.write(entry.getKey() + ";");
		            fw.write(entry.getValue().getCantidad()+";"+"\n");
		            bw.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		  }
		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;
		   try {
	            FileWriter fw = new FileWriter(fDis, false);
	            BufferedWriter bw = new BufferedWriter(fw) ;
	            fw.write("");
	             bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			  for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			        try {
			            FileWriter fw = new FileWriter(fDis, true);
			            BufferedWriter bw = new BufferedWriter(fw) ;
			            fw.write(entry.getKey() + ";");
			            fw.write(entry.getValue().getNombreProducto() + ";");
			            fw.write(entry.getValue().getCantidad() + ";");
			            fw.write(entry.getValue().getCantidad()+";"+"\n");
			            bw.close();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			  }
		return todoOK;
	}

} // Fin de la clase