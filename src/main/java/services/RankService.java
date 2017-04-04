package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Rank;
import repositories.RankRepository;

@Service
@Transactional
public class RankService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private RankRepository rankRepository;

	//Supporting services ----------------------------------------------------
	
	
	
	//Constructors -----------------------------------------------------------

	public RankService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------
	
	
	//Other business methods -------------------------------------------------

	/**
	 * Used by define the default Rank when a user is created
	 * @return
	 */
	public Rank initializeUser(){
		Rank res;
		
		System.out.println("RankService.initializeUser funciona random ! !");
		
		res = rankRepository.findAll().iterator().next();
		
		return res;
	}
}
