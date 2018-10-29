package accesoDatos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class Hibernate implements I_Acceso_Datos {
	 Session session;
	 public Hibernate() {

	        HibernateUtil util = new HibernateUtil();

	        session = util.getSessionFactory().openSession();
	        System.out.println("conected");
	    }
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		// TODO Auto-generated method stub
		 HashMap<Integer, Deposito> depositos = new HashMap<Integer, Deposito>();
		   Query q = session.createQuery("select e from Deposito e");
		   List results = q.list();
		   Iterator entitiesIterator = results.iterator();
	        while (entitiesIterator.hasNext()) {
	            Deposito deposito = (Deposito) entitiesIterator.next();
	            depositos.put(deposito.getValor(), deposito);

	        }


	        return depositos;
	    }
	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		 HashMap<String, Dispensador> dispensador1 = new HashMap<String, Dispensador>();
		   Query q = session.createQuery("select e from Dispensador e");
		   List results = q.list();
		   Iterator entitiesIterator = results.iterator();
	        while (entitiesIterator.hasNext()) {
	            Dispensador dispensadores = (Dispensador) entitiesIterator.next();
	            dispensador1.put(dispensadores.getClave(), dispensadores);

	        }


	        return dispensador1;
	    
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		// TODO Auto-generated method stub
		return false;
	}

}
