package services.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import domain.FundTransferPreference;
import domain.User;
import domain.form.FundTransferPreferenceForm;
import services.UserService;

@Service
@Transactional
public class FundTransferPreferenceFormService {

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public FundTransferPreferenceFormService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------


	public FundTransferPreferenceForm create() {
		FundTransferPreferenceForm result;
		FundTransferPreference fundTransferPreference;
		User user;
		
		user = userService.findByPrincipal();
		
		result = new FundTransferPreferenceForm();
		
		if(user.getFundTransferPreference() != null) {
			fundTransferPreference = user.getFundTransferPreference();
			
			result.setCountry(fundTransferPreference.getCountry());
			result.setAccountHolder(fundTransferPreference.getAccountHolder());
			result.setBankName(fundTransferPreference.getBankName());
			result.setIBAN(fundTransferPreference.getIBAN());
			result.setBIC(fundTransferPreference.getBIC());
			result.setPaypalEmail(fundTransferPreference.getPaypalEmail());
		}
		
		return result;
	}
	
	public User reconstruct(FundTransferPreferenceForm fundTransferPreferenceForm) {
		User result;
		FundTransferPreference fundTransferPreference;
		
		result = userService.findByPrincipal();
		fundTransferPreference = new FundTransferPreference();
		
		fundTransferPreference.setCountry(fundTransferPreferenceForm.getCountry());
		fundTransferPreference.setAccountHolder(fundTransferPreferenceForm.getAccountHolder());
		fundTransferPreference.setBankName(fundTransferPreferenceForm.getBankName());
		fundTransferPreference.setIBAN(fundTransferPreferenceForm.getIBAN());
		fundTransferPreference.setBIC(fundTransferPreferenceForm.getBIC());
		fundTransferPreference.setPaypalEmail(fundTransferPreferenceForm.getPaypalEmail());
		
		result.setFundTransferPreference(fundTransferPreference);
		
		return result;
	}
}
