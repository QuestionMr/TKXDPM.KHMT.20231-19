package subsystem;

import java.util.Map;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import utils.MyMap;

public interface VNPayInterface {
	/**
	 * Return the payment transaction results from response
	 * 
	 * @param response - the response url content after performing payment through VNPay
	 */
	public abstract PaymentTransaction makePaymentTransaction(Map<String,String> response)
			throws PaymentException, UnrecognizedException;

	/**
	 * Generate an url to display vnpay in webview
	 * @param amount   - the amount to refund
	 * @param contents - the transaction contents
	 * @return {@link entity.payment.PaymentTransaction PaymentTransaction} - if the
	 *         payment is successful
	 * @throws PaymentException      if responded with a pre-defined error code
	 * @throws UnrecognizedException if responded with an unknown error code or
	 *                               something goes wrong
	 */
	public String generateVNPayConnectionURL(int amount, String contents);
}
