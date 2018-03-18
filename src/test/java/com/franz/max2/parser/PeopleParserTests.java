package com.franz.max2.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.franz.max2.model.People;

public class PeopleParserTests {

	@Test
	public void testValidatingMultipleEntries() throws PCValidationFailure {
		PeopleParser pp = new PeopleParser();
		pp.init();
		
		People p1 = pp.validate(Arrays.asList("Duck", "Donald", "(703)-742-0996", "Golden", "99999"));
		assertEquals("Duck", p1.getFirstname());
		assertEquals("Donald", p1.getLastname());
		assertEquals("(703)-742-0996", p1.getPhoneNumber());
		assertEquals("Golden", p1.getColor());
		assertEquals("99999", p1.getZipcode());
		
		People p2 = pp.validate(Arrays.asList("Donald Duck", "Golden", "99999-1234", "703 955 0373"));
		assertEquals("Donald", p2.getFirstname());
		assertEquals("Duck", p2.getLastname());
		assertEquals("703 955 0373", p2.getPhoneNumber());
		assertEquals("Golden", p2.getColor());
		assertEquals("99999-1234", p2.getZipcode());
		
		People p3 = pp.validate(Arrays.asList("Donald Duck", "1 Disneyland", "99999", "876-543-2104", "Golden"));
		assertEquals("Donald", p3.getFirstname());
		assertEquals("Duck", p3.getLastname());
		assertEquals("1 Disneyland", p3.getAddress());
		assertEquals("876-543-2104", p3.getPhoneNumber());
		assertEquals("Golden", p3.getColor());
		assertEquals("99999", p3.getZipcode());
		
		People p4 = pp.validate(Arrays.asList("Mickey Mouse", "1 Disneyland", "99999", "876-543-2104", "Green"));
		assertEquals("Mickey", p4.getFirstname());
		assertEquals("Mouse", p4.getLastname());
		assertEquals("1 Disneyland", p4.getAddress());
		assertEquals("876-543-2104", p4.getPhoneNumber());
		assertEquals("Green", p4.getColor());
		assertEquals("99999", p4.getZipcode());
	}
	
	@Test
	public void test() {
		System.out.printf("%-25s : %25s%n", "left justified", "right justified");
		System.out.println(String.format("%-10s  %3s", "Yellow", 6));
	}

}
