import subsystem.interbank.InterbankSubsystemController;

public class VNPayTest {
	public static void main(String[] args) {
		InterbankSubsystemController itb = new InterbankSubsystemController();
		String connectResponse = itb.connectToVNPay(10000, "yes");
		System.out.println(connectResponse);
	}
}
