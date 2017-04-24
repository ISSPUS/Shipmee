package domain.form;

import org.hibernate.validator.constraints.NotBlank;


public class FundTransferPreferenceForm {
	
	private String country;
	private String accountHolder;
	private String bankName;
	private String IBAN;
	private String BIC;
	private String paypalEmail;
	
	@NotBlank
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@NotBlank
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	@NotBlank
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@NotBlank
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	
	@NotBlank
	public String getBIC() {
		return BIC;
	}
	public void setBIC(String bIC) {
		BIC = bIC;
	}
	
	@NotBlank
	public String getPaypalEmail() {
		return paypalEmail;
	}
	public void setPaypalEmail(String paypalEmail) {
		this.paypalEmail = paypalEmail;
	}

	
}
