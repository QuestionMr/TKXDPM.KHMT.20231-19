package subsystem;

import java.io.IOException;
import java.util.Map;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentTransaction;
import subsystem.vnpay.VNPaySubsystemController;
import utils.MyMap;

public class VnPaySubsystem implements VNPayInterface {

	private VNPaySubsystemController vnCtrl;
	public VnPaySubsystem() {
	        this.vnCtrl = new VNPaySubsystemController();
	}
	@Override
	public String generateVNPayConnectionURL(int amount, String contents) {
		 try{
			 return vnCtrl.generateVNPayConnectionURL(amount, contents);
	     } 
		 catch (IOException e) {
			 throw new RuntimeException(e);
	     }
	}

	@Override
	public PaymentTransaction makePaymentTransaction(Map<String, String> response)
			throws PaymentException, UnrecognizedException {
			return vnCtrl.makePaymentTransaction(response);
	}

}
