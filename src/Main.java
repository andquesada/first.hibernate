import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.Iterator;

import tables.Employee;

public final class Main {

	public static void main(String[] args) {
		SessionFactory sf = null;
		Session s = null;
		Transaction t = null;
		Query q = null;
		Iterator<Employee> it = null;
		Employee em = null;

		// Opening connection to database
		try {
			sf = new Configuration().configure().buildSessionFactory();
			s = sf.getCurrentSession();
			t = s.beginTransaction();

			q = s.createQuery("from Employee");
			it = q.iterate();
			while (it.hasNext()) {
				em = it.next();
				System.out.println("Id: " + em.getId() + ". Nombre: " + em.getName() + ". Edad: " + em.getAge()
						+ ". Rol: " + em.getRole() + ". Fecha: " + em.getDate() + ".");
			}
		} catch (org.hibernate.HibernateException e) {
			System.out.println("HibernateException: " + e.toString());
			e.printStackTrace();
		} catch (Throwable e) {
			if (null != t) {
				t.rollback();
			}

			System.out.println("Non HibernateException: " + e.toString() + e.fillInStackTrace().toString());
		} finally {
			if (null != sf) {
				sf.close();
			}
		}
	}
}
