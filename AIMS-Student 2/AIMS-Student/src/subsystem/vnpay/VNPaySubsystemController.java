package subsystem.vnpay;

import java.io.IOException;
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

import common.exception.InternalServerErrorException;
import common.exception.InvalidCardException;
import common.exception.InvalidDeliveryInfoException;
import common.exception.InvalidVersionException;
import common.exception.NotEnoughBalanceException;
import common.exception.SuspiciousTransactionException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentTransaction;
import utils.Configs;
import utils.MyMap;

public class VNPaySubsystemController {
	public PaymentTransaction makePaymentTransaction(Map<String,String> response) {
		if (response == null) return null;
		PaymentTransaction trans = 
				new PaymentTransaction(
						(String) response.get("vnp_TransactionStatus"),
						(String) response.get("vnp_TransactionNo"), 
						(String) response.get("vnp_OrderInfo"),
						Integer.parseInt((String) response.get("vnp_Amount")) / 100,
						(String)response.get("vnp_PayDate"));

		switch (trans.getErrorCode()) {
		case "00":
			break;
		case "06":
			throw new InvalidVersionException();
		case "07":
			throw new SuspiciousTransactionException();
		case "09":
			throw new InvalidCardException();
		case "10":
			throw new InvalidDeliveryInfoException();
		case "12":
			throw new InvalidCardException();
		case "51":
			throw new NotEnoughBalanceException();
		case "75":
			throw new InternalServerErrorException();
		case "79":
			throw new InvalidDeliveryInfoException();
		default:
			throw new UnrecognizedException();
		}
		return trans;
	}
	
	public static String generateVNPayConnectionURL(int amount, String contents)throws IOException {
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = contents;
        String orderType = "other";
        String vnp_TxnRef = Configs.getRandomNumber(8);
        String vnp_IpAddr = "127.0.1.1";
        String vnp_TmnCode = "95IAC7Y6";

		//Functional cohesion: vnp_Params.put
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", (amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = "VNBANK";
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
     
        vnp_Params.put("vnp_ReturnUrl", Configs.vnp_ReturnUrl);
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
        String vnp_SecureHash = Configs.hmacSHA512(vnp_HashSecret, hashData.toString());

        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        System.out.println(queryUrl);
        return queryUrl;
		
        //Common coupling (Global variable Configs.PROCESS_TRANSACTION_URL of Configs class)
	}
}
