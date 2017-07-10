package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String name;
	private String surname;
	private String email;
	private Date birthDate;
	private String phone;
	private String dni;
	private String photo;
	private String passwordResetToken;
	private String localePreferences;
	
	@NotBlank
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Email
	@NotNull
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Past
	@NotNull
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPasswordResetToken() {
		return passwordResetToken;
	}
	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}
	
	
	@Pattern(regexp="^(es|en)$")
	@NotNull
	@NotBlank
	public String getLocalePreferences() {
		return localePreferences;
	}
	public void setLocalePreferences(String localePreferences) {
		this.localePreferences = localePreferences;
	}



	// Relationships ----------------------------------------------------------
	private UserAccount userAccount;

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	

}
