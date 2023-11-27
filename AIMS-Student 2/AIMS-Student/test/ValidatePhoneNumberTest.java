import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import controller.PlaceOrderController;

public class ValidatePhoneNumberTest {

	public PlaceOrderController placeOrderController;
	@Before
	public void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}
	
	@Test 
	public void not10CharactersShouldFail() {
		String phoneFail = "01234567890";
		assertEquals(placeOrderController.validatePhoneNumber(phoneFail), false);
		//fail("Not yet implemented");
	}
	
	@Test
	public void allNotDigitsShouldFail() {
		String phoneFail = "abcd012345";
		assertEquals(placeOrderController.validatePhoneNumber(phoneFail), false);
		//fail("Not yet implemented");
	}
	
	@Test 
	public void shouldPass() {
		String phoneFail = "0123456789";
		assertEquals(placeOrderController.validatePhoneNumber(phoneFail), true);
		//fail("Not yet implemented");
	}
	
	@Test 
	public void shouldFail() {
		String phoneFail = "Quang Bui";
		assertEquals(placeOrderController.validatePhoneNumber(phoneFail), false);
		//fail("Not yet implemented");
	}
	
	
	
	
	

}
