package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Rank extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String title;
	private String description;
	private String icon;
	private int since;
	
	@NotNull
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull
	@NotBlank
	@URL
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Min(0)
	public int getSince() {
		return since;
	}
	public void setSince(int since) {
		this.since = since;
	}
	
	
	// Relationships ----------------------------------------------------------

}
