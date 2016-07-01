/**
 * 
 */
package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;

import javax.transaction.RollbackException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import java.util.Iterator;
import java.util.Calendar;

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

	public void openConnection() throws ConfigurationAvailable, HibernateException {
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

	public void closeConnection() throws HibernateException {
		sf.close();
	}

	public boolean isOpen() {
		return sf != null;
	}

	public String employeeListToString()
			throws NoConfigurationAvailable, HibernateException, Throwable, RollbackException {
		testAndThrowNoConfigurationAvailable();
		openTransactionScope();

		StringBuilder sb;
		sb = new StringBuilder();

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

	public void insertNewEmployee(String name, byte age, String role)
			throws NoConfigurationAvailable, HibernateException, Throwable, RollbackException {
		testAndThrowNoConfigurationAvailable();
		openTransactionScope();

		em = new Employee();
		em.setName(name);
		em.setAge(age);
		em.setRole(role);
		em.setDate(Calendar.getInstance().getTime());
		s.save(em);

		closeTransactionScope();
	}

	public void deleteEmployee(String name, byte age, String role)
			throws NoConfigurationAvailable, IllegalStateException, HibernateException, Throwable, RollbackException {
		testAndThrowNoConfigurationAvailable();
		openTransactionScope();

		em = retrieveEmployee(name, age, role);
		s.delete(em);

		closeTransactionScope();
	}

	// Assumes a SessionFactory and a Transaction not already opened
	private Employee retrieveEmployee(String name, byte age, String role) throws IllegalStateException {
		String hql = "from Employee E where E.name='" + name + "' and E.age=" + age + " and E.role='" + role + "'";
		q = s.createQuery(hql);
		return q.getSingleResult();
	}

	// Assumes a SessionFactory is not already opened
	private void openTransactionScope() throws HibernateException, Throwable {
		s = sf.getCurrentSession();
		t = s.beginTransaction();
	}

	// Assumes a SessionFactory is already opened
	private void closeTransactionScope() throws RollbackException {
		t.commit();
	}

	private void testAndThrowNoConfigurationAvailable() throws NoConfigurationAvailable {
		if (null == sf) {
			throw new NoConfigurationAvailable();
		}
	}
}