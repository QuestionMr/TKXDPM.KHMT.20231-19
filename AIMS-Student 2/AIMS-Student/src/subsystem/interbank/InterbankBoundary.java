package subsystem.interbank;

import common.exception.UnrecognizedException;
import utils.API;

public class InterbankBoundary {

	//Data coupling (url, data with API.post)
	String query(String url, String data) {
		String response = null;
		try {
			response = API.post(url, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UnrecognizedException();
		}
		return response;
	}

	//Data coupling (string with API.get)
	public String getReturnLink(String string) {
		String response = null;
		try {
			response = API.get(string,"");
		} catch (Exception e) {
			throw new UnrecognizedException();
		}
		return response;
	}

}
