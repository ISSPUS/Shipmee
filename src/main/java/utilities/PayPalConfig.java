package utilities;

import java.util.HashMap;
import java.util.Map;

public class PayPalConfig {

	public static final String CHARACTERS_TRACKING_ID = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	public static final int LENGTH_TRACKING_ID = 25;

	// sandbox/live
	public static String getMode() {
		return System.getenv("PAYPAL_MODE");
	}
	
	// http://localhost:8080/Shipmee
	// https://dev.shipmee.es
	public static String getUrlBase() {
		return System.getenv("URL_DEPLOY");
	}
	
	public static String getBusinessEmail() {
		return System.getenv("PAYPAL_BUSINESS_EMAIL");
	}
	
	// APP-806767gfgcgh543T
	public static String getBusinessAdaptiveApiKey() {
		return System.getenv("PAYPAL_BUSINESS_ADAPTIVE_API_KEY");
	}
	
	// xxxxxxxx_api1.paypp.es
	public static String getBusinessUserName() {
		return System.getenv("PAYPAL_BUSINESS_USERNAME");
	}
	
	// K4HY6666666NVTLNN
	public static String getBusinessPassword() {
		return System.getenv("PAYPAL_BUSINESS_PASSWORD");
	}
	
	// AFcWxV21C7fd0v3bYY666666664FBATQZFLpAo2Gt4aDgz3S5m
	public static String getBusinessSignature() {
		return System.getenv("PAYPAL_BUSINESS_SIGNATURE");
	}
	
	// https://www.sandbox.paypal.com/cgi-bin/webscr
	public static String getPayRedirectUrl() {
		return System.getenv("PAYPAL_PAY_REDIRECT_URL");
	}
	
	public static Map<String, String> getConfigurationMap(){
		Map<String, String> customConfigurationMap = new HashMap<String, String>();
		customConfigurationMap.put("mode", getMode());
//		customConfigurationMap.put("acct.ClientId", clientId);
//		customConfigurationMap.put("acct.ClientSecret", clientSecret);
		customConfigurationMap.put("acct1.AppId", getBusinessAdaptiveApiKey());
		customConfigurationMap.put("acct1.UserName", getBusinessUserName());
		customConfigurationMap.put("acct1.Password", getBusinessPassword());
		customConfigurationMap.put("acct1.Signature", getBusinessSignature());
//		customConfigurationMap.put("acct1.UserName", "jb-us-seller_api1.paypal.com");
//		customConfigurationMap.put("acct1.Password", "WX4WTU3S8MY44S7F");
//		customConfigurationMap.put("acct1.Signature", "AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu-RWy");
		
		return customConfigurationMap;
	}


}
