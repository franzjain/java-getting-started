package com.franz.max2.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franz.max2.model.People;

public interface PeopleColumnValidator {

	public void validateColumn(String value, People p) throws PCValidationFailure;
}

class FirstnameColumnValidator implements PeopleColumnValidator{
	
	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if((rawValue.length() > 0) && (rawValue.matches("[a-zA-Z]*"))) {
				p.setFirstname(rawValue);
			} else {
				throw new PCValidationFailure(value + " is not valid Firstname");
			}
		} catch (PCValidationFailure pcvf) {
			throw pcvf;
		} catch (Exception e) {
			throw new PCValidationFailure(e);
		}
	}
}

class FullnameColumnValidator implements PeopleColumnValidator{
	
	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String[] rawValues = value.trim().split("\\s+");
			if(rawValues.length == 2
					&& ((rawValues[0].length() > 0) && (rawValues[0].matches("[a-zA-Z]*"))) 
					&& ((rawValues[1].length() > 0) && (rawValues[1].matches("[a-zA-Z]*")))) {
				p.setFirstname(rawValues[0]);
				p.setLastname(rawValues[1]);
			} else {
				throw new PCValidationFailure(value + " is not valid Fullname");
			}
		} catch (PCValidationFailure pcvf) {
			throw pcvf;
		} catch (Exception e) {
			throw new PCValidationFailure(e);
		}
	}
}

class LastnameColumnValidator implements PeopleColumnValidator{
	
	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if((rawValue.length() > 0) && (rawValue.matches("[a-zA-Z]*"))) {
				p.setLastname(rawValue);
			} else {
				throw new PCValidationFailure(value + " is not valid Lastname");
			}
		} catch (PCValidationFailure pcvf) {
			throw pcvf;
		} catch (Exception e) {
			throw new PCValidationFailure(e);
		}
	}
}

class PhoneNumColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if((rawValue.length() > 0) && (rawValue.matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$"))) {
				p.setPhoneNumber(rawValue);
			} else {
				throw new PCValidationFailure(value + " is not valid Phone number");
			}
		} catch (PCValidationFailure pcvf) {
			throw pcvf;
		} catch (Exception e) {
			throw new PCValidationFailure(e);
		}		
	}
	
}

class ColorColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if(rawValue.length() > 0 && rawValue.matches("[a-zA-Z]*")) {
				p.setColor(rawValue);
			} else {
				throw new PCValidationFailure(value + " is not valid Color");
			}
		} catch (PCValidationFailure pcvf) {
			throw pcvf;
		} catch (Exception e) {
			throw new PCValidationFailure(e);
		}
	}
	
}

class ZipcodeColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if((rawValue.length() > 0) && (rawValue.matches("^[0-9]{5}(?:-[0-9]{4})?$"))) {
				p.setZipcode(rawValue);
			} else {
				throw new PCValidationFailure(value + " is not valid Zip code");
			}
		} catch (PCValidationFailure pcvf) {
			throw pcvf;
		} catch (Exception e) {
			throw new PCValidationFailure(e);
		}
	}
	
}

class AddressColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String[] rawValues = value.trim().split("\\s+");
			// The non-production ready validation we perform here, is address should at least have 2 parts, and first part is house number
			if(rawValues.length >= 2
					&& ((rawValues[0].length() > 0) && (rawValues[0].matches("[0-9]*"))) 
					&& ((rawValues[1].length() > 0) && (rawValues[1].matches("[0-9a-zA-Z]*")))) {
				p.setAddress(value.trim());
			} else {
				throw new PCValidationFailure(value + " is not valid Address");
			}
		} catch (PCValidationFailure pcvf) {
			throw pcvf;
		} catch (Exception e) {
			throw new PCValidationFailure(e);
		}
	}
	
}

class PCValidationFailure extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PCValidationFailure() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PCValidationFailure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PCValidationFailure(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PCValidationFailure(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PCValidationFailure(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}