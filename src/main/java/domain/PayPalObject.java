package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
// @Table(indexes = { @Index(columnList = "trackingId")})
public class PayPalObject extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String trackingId;
	private String payStatus;
	private String refundStatus;
	

	@NotNull
	@NotBlank
	@Column(unique = true)
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	@NotBlank
	// CREATED, COMPLETED, ...
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@NotBlank
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	// Relationships ----------------------------------------------------------
	
	private FeePayment feePayment;
	
	@OneToOne(optional=true)
	public FeePayment getFeePayment() {
		return feePayment;
	}
	public void setFeePayment(FeePayment feePayment) {
		this.feePayment = feePayment;
	}
	

}
