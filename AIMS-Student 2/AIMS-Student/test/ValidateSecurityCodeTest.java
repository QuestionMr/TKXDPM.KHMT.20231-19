import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import controller.PaymentController;

public class ValidateSecurityCodeTest {

	public PaymentController paymentController;
	@Before
	public void setUp() throws Exception {
		paymentController = new PaymentController();
	}
	
	@Test 
	public void not3CharactersShouldFail() {
		String cardFail = "1111";
		assertEquals(paymentController.validateSecurityCode(cardFail), false);
		//fail("Not yet implemented");
	}
	
	@Test
	public void allNotDigitsShouldFail() {
		String cardFail = "a11";
		assertEquals(paymentController.validateSecurityCode(cardFail), false);
		//fail("Not yet implemented");
	}
	
	@Test 
	public void shouldPass() {
		String cardPass = "111";
		assertEquals(paymentController.validateSecurityCode(cardPass), true);
		//fail("Not yet implemented");
	}
	
	
	
	
	
	

}
