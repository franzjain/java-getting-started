package com.franz.max2.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.franz.max2.model.People;

public class PeopleColumnValidatorTests {

	
	@Test
	public void testFirstnameColumnValidatorPass() throws PCValidationFailure {
		FirstnameColumnValidator fcv = new FirstnameColumnValidator();
		People p = new People();
		fcv.validateColumn("Donald", p);
		assertEquals("Donald", p.getFirstname());
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testFirstnameColumnValidatorFail() throws PCValidationFailure {
		FirstnameColumnValidator fcv = new FirstnameColumnValidator();
		fcv.validateColumn("Duck123", null);
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testFirstnameColumnValidatorFail2() throws PCValidationFailure {
		FirstnameColumnValidator fcv = new FirstnameColumnValidator();
		fcv.validateColumn(" ", null);
	}
	
	@Test
	public void testLastnameColumnValidatorPass() throws PCValidationFailure {
		LastnameColumnValidator lcv = new LastnameColumnValidator();
		People p = new People();
		lcv.validateColumn("Duck", p);
		assertEquals("Duck", p.getLastname());
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testLastnameColumnValidatorFail() throws PCValidationFailure {
		LastnameColumnValidator lcv = new LastnameColumnValidator();
		lcv.validateColumn("Donald456", null);
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testLastnameColumnValidatorFail2() throws PCValidationFailure {
		LastnameColumnValidator lcv = new LastnameColumnValidator();
		lcv.validateColumn(" ", null);
	}
	
	@Test
	public void testFullnameColumnValidatorPass() throws PCValidationFailure {
		FullnameColumnValidator fcv = new FullnameColumnValidator();
		People p = new People();
		fcv.validateColumn("Donald Duck", p);
		assertEquals("Donald", p.getFirstname());
		assertEquals("Duck", p.getLastname());
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testFullnameColumnValidatorFail() throws PCValidationFailure {
		FullnameColumnValidator fcv = new FullnameColumnValidator();
		fcv.validateColumn("DonaldDuck", null);
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testFullnameColumnValidatorFail2() throws PCValidationFailure {
		FullnameColumnValidator fcv = new FullnameColumnValidator();
		fcv.validateColumn("Donald Duck 3", null);
	}
	
	@Test
	public void testPhoneNumColumnValidator() throws PCValidationFailure {
		PhoneNumColumnValidator fcv = new PhoneNumColumnValidator();
		People p = new People();
		fcv.validateColumn("(703)-742-0996", p);
		assertEquals("(703)-742-0996", p.getPhoneNumber());
	}
	
	@Test
	public void testPhoneNumColumnValidator2() throws PCValidationFailure {
		PhoneNumColumnValidator fcv = new PhoneNumColumnValidator();
		People p = new People();
		fcv.validateColumn("703 955 0373", p);
		assertEquals("703 955 0373", p.getPhoneNumber());
	}
	
	@Test
	public void testPhoneNumColumnValidator3() throws PCValidationFailure {
		PhoneNumColumnValidator fcv = new PhoneNumColumnValidator();
		People p = new People();
		fcv.validateColumn("646 111 0101", p);
		assertEquals("646 111 0101", p.getPhoneNumber());
	}
	
	@Test
	public void testPhoneNumColumnValidator4() throws PCValidationFailure {
		PhoneNumColumnValidator fcv = new PhoneNumColumnValidator();
		People p = new People();
		fcv.validateColumn("876-543-2104", p);
		assertEquals("876-543-2104", p.getPhoneNumber());
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testPhoneNumColumnValidatorFail() throws PCValidationFailure {
		PhoneNumColumnValidator fcv = new PhoneNumColumnValidator();
		People p = new People();
		fcv.validateColumn("8825252", p);
	}
	
	@Test
	public void testZipcodeColumnValidator() throws PCValidationFailure {
		ZipcodeColumnValidator zcv = new ZipcodeColumnValidator();
		People p = new People();
		zcv.validateColumn("99999", p);
		assertEquals("99999", p.getZipcode());
	}
	
	@Test
	public void testZipcodeColumnValidator2() throws PCValidationFailure {
		ZipcodeColumnValidator zcv = new ZipcodeColumnValidator();
		People p = new People();
		zcv.validateColumn("99999-1234", p);
		assertEquals("99999-1234", p.getZipcode());
	}
	
	@Test(expected = PCValidationFailure.class)
	public void testZipcodeColumnValidatorFail() throws PCValidationFailure {
		ZipcodeColumnValidator zcv = new ZipcodeColumnValidator();
		People p = new People();
		zcv.validateColumn("8825252", p);
	}
}
