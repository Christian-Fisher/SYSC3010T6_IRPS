package sqlTest;

public class Main {
	public static void main(String args[]) {
		DBconnect connect = new DBconnect();
		connect.getData();
		connect.checkInputs();
		
	}

}
