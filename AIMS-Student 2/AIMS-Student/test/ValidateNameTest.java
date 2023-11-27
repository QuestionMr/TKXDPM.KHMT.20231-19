import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import controller.PlaceOrderController;

public class ValidateNameTest {

	public PlaceOrderController placeOrderController;
	@Before
	public void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}
	@Test
	public void nullShouldFail() {
		String nameFail = "";
		assertEquals(placeOrderController.validateName(nameFail), false);
	}
	@Test
	public void containsDigitsShouldFail() {
		String nameFail = "Quang Bui12";
		assertEquals(placeOrderController.validateName(nameFail), false);
	}
	
	@Test 
	public void containsNonSpaceSpecialCharacterShouldFail() {
		String nameFail = "Qu@ng Bui";
		assertEquals(placeOrderController.validateName(nameFail), false);
	}
	@Test
	public void containsNoAlphaBeticalShouldFail() {
		String nameFail = "   ";
		assertEquals(placeOrderController.validateName(nameFail), false);
	}
	
	@Test 
	public void shouldPass() {
		String namePass = "Quang Bui";
		assertEquals(placeOrderController.validateName(namePass), true);
	}

}
