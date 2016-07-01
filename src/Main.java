import util.QueryUtil;

public final class Main {

	public static void main(String[] args) {
		QueryUtil qu = null;
		String name = null;
		byte age = 0;
		String role = null;

		qu = new QueryUtil();
		name = "Darío";
		age = (byte) 9;
		role = "huevito";

		try {
			qu.openConnection();
			System.out.println(qu.employeeListToString());

			qu.insertNewEmployee(name, age, role);
			System.out.println(qu.employeeListToString());

			qu.deleteEmployee(name, age, role);
			System.out.println(qu.employeeListToString());

			qu.closeConnection();
		} catch (Throwable e) {
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			// Testing whether the DB is opened
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			if (qu.isOpen()) {
				try {
					qu.closeConnection();
				} catch (Throwable e2) {
					System.out.println("Problem when closing configuration.");
					e2.printStackTrace();
				}
			}
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}
