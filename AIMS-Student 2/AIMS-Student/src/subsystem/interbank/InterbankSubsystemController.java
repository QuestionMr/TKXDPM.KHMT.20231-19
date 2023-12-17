package subsystem.interbank;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import common.exception.InternalServerErrorException;
import common.exception.InvalidCardException;
import common.exception.InvalidTransactionAmountException;
import common.exception.InvalidVersionException;
import common.exception.NotEnoughBalanceException;
import common.exception.NotEnoughTransactionInfoException;
import common.exception.SuspiciousTransactionException;
import common.exception.UnrecognizedException;

//Violation of DIP: Below imports are all concrete classes
//Solution: Create interfaces that these classes implement to communicate with them
//If concrete classes require instantiation, create a factory class and interface with the purpose of creating these objects
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import utils.Configs;
import utils.MyMap;
import utils.Utils;

//Communication cohesion (All variables inside this class are accessible only through methods)
//Logical cohesion: refund, generate data, payorder, makePaymentTransaction
public class InterbankSubsystemController {

	private static final String PUBLIC_KEY = "AQzdE8O/fR8=";
	private static final String SECRET_KEY = "BUXj/7/gHHI=";
	private static final String PAY_COMMAND = "pay";
	private static final String VERSION = "1.0.0";

	private static InterbankBoundary interbankBoundary = new InterbankBoundary();

	
	//Stamp coupling (CreditCard, int, String, all not used)
	public PaymentTransaction refund(CreditCard card, int amount, String contents) {
		return null;
	}
	
	private String generateData(Map<String, Object> data) {
		return ((MyMap) data).toJSON();
	}

	//Stamp coupling (Creditcard) - used, data coupling (int amount, String contents)
	public PaymentTransaction payOrder(CreditCard card, int amount, String contents) {
		Map<String, Object> transaction = new MyMap();

		try {
			transaction.putAll(MyMap.toMyMap(card));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new InvalidCardException();
		}
		//Content coupling: tight (Modifying transaction with put())
		//Functional cohesion: transaction.put
		//Sequential cohesion: transaction -> responseText -> response -> makePaymentTransaction
		transaction.put("command", PAY_COMMAND);
		transaction.put("transactionContent", contents);
		transaction.put("amount", amount);
		transaction.put("createdAt", Utils.getToday());

		Map<String, Object> requestMap = new MyMap();
		requestMap.put("version", VERSION);
		requestMap.put("transaction", transaction);

		String responseText = interbankBoundary.query(Configs.PROCESS_TRANSACTION_URL, generateData(requestMap));
		MyMap response = null;
		try {
			response = MyMap.toMyMap(responseText, 0);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new UnrecognizedException();
		}

		//Control coupling: makePaymentTransaction behaves differently according to argument "response" (91)
		return makePaymentTransaction(response);
	}
	
	//Stamp coupling (MyMap - used)
	private PaymentTransaction makePaymentTransaction(MyMap response) {
		if (response == null)
			return null;
		//Sequential cohesion: transactio -> card -> trans
		MyMap transcation = (MyMap) response.get("transaction");
		CreditCard card = new CreditCard((String) transcation.get("cardCode"), (String) transcation.get("owner"),
				Integer.parseInt((String) transcation.get("cvvCode")), (String) transcation.get("dateExpired"));
		PaymentTransaction trans = new PaymentTransaction((String) response.get("errorCode"), card,
				(String) transcation.get("transactionId"), (String) transcation.get("transactionContent"),
				Integer.parseInt((String) transcation.get("amount")), (String) transcation.get("createdAt"));

		switch (trans.getErrorCode()) {
		case "00":
			break;
		case "01":
			throw new InvalidCardException();
		case "02":
			throw new NotEnoughBalanceException();
		case "03":
			throw new InternalServerErrorException();
		case "04":
			throw new SuspiciousTransactionException();
		case "05":
			throw new NotEnoughTransactionInfoException();
		case "06":
			throw new InvalidVersionException();
		case "07":
			throw new InvalidTransactionAmountException();
		default:
			throw new UnrecognizedException();
		}

		return trans;
	}
	
	//Data coupling (int, String)
	//SRP violation: connectToVNPay should be a separate responsibility
	//Solution: move function to another class
	public static String connectToVNPay(int amount, String contents) {
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = contents;
        String orderType = "other";
        String vnp_TxnRef = "1";
        String vnp_IpAddr = "127.0.1.1";
        String vnp_TmnCode = "95IAC7Y6";

		//Functional cohesion: vnp_Params.put
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = "VNBANK";
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
     
        vnp_Params.put("vnp_ReturnUrl", "https%3A%2F%2Fdomainmerchant.vn%2FReturnUrl");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            System.out.println(vnp_Params.get(fieldName));
            String fieldValue;
            if (vnp_Params.get(fieldName) instanceof String) fieldValue = (String) vnp_Params.get(fieldName);
            else fieldValue = String.valueOf(vnp_Params.get(fieldName));
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                //Build query
                try {
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                query.append('=');
                try {
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
		//Sequential cohesion: queryUrl -> vnp_Securehash -> queryUrl -> responseText -> return
        String queryUrl = query.toString();
        String vnp_HashSecret = "LFNLPDQQWDJSVRUNYMYLNFZEKGXNHLRC";
        //String vnp_SecureHash = "3e0d61a0c0534b2e36680b3f7277743e8784cc4e1d68fa7d276e79c23be7d6318d338b477910a27992f5057bb1582bd44bd82ae8009ffaf6d141219218625c42";
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());

        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        System.out.println(queryUrl);
        return queryUrl;
		
        //Common coupling (Global variable Configs.PROCESS_TRANSACTION_URL of Configs class)
//		String responseText = interbankBoundary.getReturnLink(Configs.PROCESS_TRANSACTION_URL + "?" + queryUrl);
//		return responseText;
	}
    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
}
