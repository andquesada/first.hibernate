import util.QueryUtil;

public final class Main {

	public static void main(String[] args) {
		QueryUtil qu;
		qu = new QueryUtil();

		try {
			qu.startConfiguration();			
			System.out.println(qu.employeeListToString());
			qu.closeConfiguration();
		} catch (Throwable e) {
			if (qu.isConfigured()) {

				try {
					qu.closeConfiguration();
				} catch (Throwable e2) {
					System.out.println("Problem when closing configuration.");
					e2.printStackTrace();
				}
			}
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}
