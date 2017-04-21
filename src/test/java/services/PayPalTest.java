package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.paypal.base.rest.PayPalRESTException;

import utilities.AbstractTest;
import utilities.PayPal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PayPalTest extends AbstractTest{
	
//	@Test
//	public void doTest(){
//		try {
//			PayPal.simple(PayPal.generateTransaction(), PayPal.generatePayer());
//		} catch (PayPalRESTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void doTest2(){
//		try {
//			PayPal.addPayPal();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
