import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import controller.PlaceOrderController;

public class ValidateAddressTest {

	public PlaceOrderController placeOrderController;
	@Before
	public void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}
	@Test
	public void nullShouldFail() {
		String addressFail = "";
		assertEquals(placeOrderController.validateAddress(addressFail), false);
	}

	@Test
	public void noAlphabeticalShouldFail() {
		String addressFail = "12 ";
		assertEquals(placeOrderController.validateAddress(addressFail), false);
	}
	
	@Test 
	public void shouldPass() {
		String addressPass = "Cau Giay, Ha Noi";
		assertEquals(placeOrderController.validateAddress(addressPass), true);
	}

}
