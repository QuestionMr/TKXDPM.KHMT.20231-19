package controller;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Pattern;

import common.exception.InvalidCardException;
import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.cart.Cart;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import subsystem.VnPaySubsystem;
import subsystem.VNPayInterface;
import utils.MyMap;


/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 * 
 * @author hieud
 *
 */
public class PaymentController extends BaseController {

	/**
	 * Represent the card used for payment
	 */
	private CreditCard card;
	
	private VNPayInterface vnPay;


	/**
	 * Validate the input date which should be in the format "mm/yy", and then
	 * return a {@link java.lang.String String} representing the date in the
	 * required format "mmyy" .
	 * 
	 * @param date - the {@link java.lang.String String} represents the input date
	 * @return {@link java.lang.String String} - date representation of the required
	 *         format
	 * @throws InvalidCardException - if the string does not represent a valid date
	 *                              in the expected format
	 */
	private String getExpirationDate(String date) throws InvalidCardException {
		String[] strs = date.split("/");
		if (strs.length != 2) {
			throw new InvalidCardException();
		}

		String expirationDate = null;
		int month = -1;
		int year = -1;

		try {
			month = Integer.parseInt(strs[0]);
			year = Integer.parseInt(strs[1]);
			if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
				throw new InvalidCardException();
			}
			expirationDate = strs[0] + strs[1];

		} catch (Exception ex) {
			throw new InvalidCardException();
		}

		return expirationDate;
	}


	public Map<String, String> generateResult(Map<String,String> response) {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			
			this.vnPay = new VnPaySubsystem();
			PaymentTransaction transaction = vnPay.makePaymentTransaction(response);

			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid the order!");
		} catch (PaymentException | UnrecognizedException ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}
	/**
	 * Pay order, and then return the result with a message.
	 * 
	 * @param amount         - the amount to pay
	 * @param contents       - the transaction contents
	 * @return {String} represent the payment result with a
	 *         message.
	 */
	
	public String generateOrderPayment(int amount, String content) {
		this.vnPay = new VnPaySubsystem();
		return vnPay.generateVNPayConnectionURL(amount, content);
	}
	public void emptyCart(){
        Cart.getCart().emptyCart();
    }
	
	public boolean validateCardNumber(String cardNumber) {
		if (cardNumber.length() != 16) return false;
    	Pattern pattern = Pattern.compile("([0-9])+");
    	return pattern.matcher(cardNumber).matches();
	}
	
	public boolean validateName(String name) {
	    // TODO: your work
	    if (name.length() == 0) return false;
	    Pattern pattern = Pattern.compile("^([a-zA-Z]+ ?)+$");
	    return pattern.matcher(name).matches();
	}
	
	public boolean validateSecurityCode(String securityCode) {
		if (securityCode.length() != 3) return false;
    	Pattern pattern = Pattern.compile("([0-9])+");
    	return pattern.matcher(securityCode).matches();
	}

	
}