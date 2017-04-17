package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


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
	
	@NotBlank
	@NotNull
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@NotBlank
	@NotNull
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	@NotBlank
	@NotNull
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@NotBlank
	@NotNull
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	
	@NotBlank
	@NotNull
	public String getBIC() {
		return BIC;
	}
	public void setBIC(String bIC) {
		BIC = bIC;
	}

	// Relationships ----------------------------------------------------------

}
