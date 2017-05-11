package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import org.hibernate.validator.constraints.Email;


@Embeddable
@Access(AccessType.PROPERTY)
public class FundTransferPreference{

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String country;
	private String accountHolder;
	private String bankName;
	private String IBAN;
	private String BIC;
	private String paypalEmail;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	
	public String getBIC() {
		return BIC;
	}
	public void setBIC(String bIC) {
		BIC = bIC;
	}
	
	@Email
	public String getPaypalEmail() {
		return paypalEmail;
	}
	public void setPaypalEmail(String paypalEmail) {
		this.paypalEmail = paypalEmail;
	}
	
	// Relationships ----------------------------------------------------------

}
