/**
 * 
 */
package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;

import javax.transaction.RollbackException;

import java.util.Iterator;

import tables.Employee;
import exceptions.ConfigurationAvailable;
import exceptions.NoConfigurationAvailable;

/**
 * @author andres.quesada
 *
 */
public class QueryUtil {
	private SessionFactory sf;
	private Session s;
	private Transaction t;
	private Query<Employee> q;
	private Iterator<Employee> it;
	private Employee em;

	/**
	 * 
	 */
	public QueryUtil() {
		sf = null;
		s = null;
		t = null;
		q = null;
		it = null;
		em = null;
	}

	public void startConfiguration() throws ConfigurationAvailable, HibernateException {
		if (null != sf) {
			throw new ConfigurationAvailable();
		}

		try {
			sf = new Configuration().configure().buildSessionFactory();

		} catch (Exception e) {
			sf = null;
			throw e;
		}
	}

	public void closeConfiguration() throws HibernateException {
		sf.close();
	}

	public boolean isConfigured() {
		return sf != null;
	}

	public String employeeListToString()
			throws NoConfigurationAvailable, HibernateException, Throwable, RollbackException {
		testAndThrowNoConfigurationAvailable();

		StringBuilder sb;
		sb = new StringBuilder();

		openTransactionScope();

		q = s.createQuery("from Employee");
		it = q.getResultList().iterator();
		while (it.hasNext()) {
			em = it.next();
			sb.append("Id: " + em.getId() + ". Nombre: " + em.getName() + ". Edad: " + em.getAge() + ". Rol: "
					+ em.getRole() + ". Fecha: " + em.getDate() + ".\n");
		}

		closeTransactionScope();

		return sb.toString();
	}

	private void openTransactionScope() throws HibernateException, Throwable {
		s = sf.getCurrentSession();
		t = s.beginTransaction();
	}

	private void closeTransactionScope() throws RollbackException {
		testAndThrowNoConfigurationAvailable();
		t.commit();
	}

	private void testAndThrowNoConfigurationAvailable() throws NoConfigurationAvailable {
		if (null == sf) {
			throw new NoConfigurationAvailable();
		}
	}
}