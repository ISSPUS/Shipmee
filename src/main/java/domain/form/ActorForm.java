package domain.form;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ActorForm {

	// Attributes -------------------------------------------------------------
	private int id;
	private String name;
	private String surname;
	private String email;
	private Date birthDate;
	private String phone;
	private String dni;
	private CommonsMultipartFile dniPhoto;
	private CommonsMultipartFile photo;
	private String userName;
	private String password;
	private String repeatedPassword;
	private boolean acceptLegalCondition;
	private String localePreferences;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
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

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	public CommonsMultipartFile getDniPhoto() {
		return dniPhoto;
	}
	public void setDniPhoto(CommonsMultipartFile dniPhoto) {
		this.dniPhoto = dniPhoto;
	}
	
	public CommonsMultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(CommonsMultipartFile photo) {
		this.photo = photo;
	}
	
	@NotBlank
	@NotNull
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	
	public boolean getAcceptLegalCondition() {
		return acceptLegalCondition;
	}
	public void setAcceptLegalCondition(boolean acceptLegalCondition) {
		this.acceptLegalCondition = acceptLegalCondition;
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
	
}
