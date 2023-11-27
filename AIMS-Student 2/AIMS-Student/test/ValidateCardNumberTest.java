import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import controller.PaymentController;

public class ValidateCardNumberTest {

	public PaymentController paymentController;
	@Before
	public void setUp() throws Exception {
		paymentController = new PaymentController();
	}
	
	@Test 
	public void not16CharactersShouldFail() {
		String cardFail = "01234567890";
		assertEquals(paymentController.validateCardNumber(cardFail), false);
		//fail("Not yet implemented");
	}
	
	@Test
	public void allNotDigitsShouldFail() {
		String cardFail = "abcd012345123456";
		assertEquals(paymentController.validateCardNumber(cardFail), false);
		//fail("Not yet implemented");
	}
	
	@Test 
	public void shouldPass() {
		String cardPass = "0123456789876543";
		assertEquals(paymentController.validateCardNumber(cardPass), true);
		//fail("Not yet implemented");
	}
	
	
	
	
	
	

}
