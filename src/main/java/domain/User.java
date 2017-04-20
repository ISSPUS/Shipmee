package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Attributes -------------------------------------------------------------
	
	private boolean isVerified;
	private boolean isActive;
	private String dniPhoto;
	private FundTransferPreference fundTransferPreference;
	
	public boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
		
	@NotNull
	public String getDniPhoto() {
		return dniPhoto;
	}
	public void setDniPhoto(String dniPhoto) {
		this.dniPhoto = dniPhoto;
	}

	@Valid
	public FundTransferPreference getFundTransferPreference() {
		return fundTransferPreference;
	}
	public void setFundTransferPreference(FundTransferPreference fundTransferPreference) {
		this.fundTransferPreference = fundTransferPreference;
	}



	// Relationships ----------------------------------------------------------
	private Collection<Route> routes;
	private Rank rank;

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(Collection<Route> routes) {
		this.routes = routes;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Rank getRank() {
		return rank;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
}
