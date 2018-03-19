package com.franz.max2.parser;

import com.franz.max2.model.People;

/**
 * Signature of each PeopleColumnValidator need to implement to validate all different kind of columns form input csv file
 * <p>
 * ex: Firstname, Lastname, address, Phone number ,color, Zipcode
 * @author Franz
 *
 */
public interface PeopleColumnValidator {

	/**
	 * If value argument is valid, use the value to initialize corresponding field of People object
	 * @param value
	 * @param p
	 * @throws PCValidationFailure
	 */
	public void validateColumn(String value, People p) throws PCValidationFailure;
}

/**
 * Validation rule -- Firstname can be composed only from alphabet characters, and at least one character long
 * @author Franz
 *
 */
class FirstnameColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if ((rawValue.length() > 0) && (rawValue.matches("[a-zA-Z]*"))) {
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

/**
 * Validation rule -- Full name can be composed only from alphabet characters, Firstname and Lastname are separated by a space, 
 * and each part at least one character long
 * @author Franz
 *
 */
class FullnameColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String[] rawValues = value.trim().split("\\s+");
			if (rawValues.length == 2 && ((rawValues[0].length() > 0) && (rawValues[0].matches("[a-zA-Z]*")))
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

/**
 * Validation rule -- Lastname can be composed only from alphabet characters, and at least one character long
 * @author Franz
 *
 */
class LastnameColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if ((rawValue.length() > 0) && (rawValue.matches("[a-zA-Z]*"))) {
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

/**
 * Validation rule -- Phone number has 3 parts, each with optional (), [], or - characters delimiter in between
 * @author Franz
 *
 */
class PhoneNumColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if ((rawValue.length() > 0) && (rawValue.matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$"))) {
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

/**
 * Validation rule -- Color can be composed only from alphabet characters, and at least one character long
 * @author Franz
 *
 */
class ColorColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if (rawValue.length() > 0 && rawValue.matches("[a-zA-Z]*")) {
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

/**
 * Validation rule -- Zipcode has 5 digits and trailing optional '-' character and 4 other digits
 * @author Franz
 *
 */
class ZipcodeColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String rawValue = value.trim();
			if ((rawValue.length() > 0) && (rawValue.matches("^[0-9]{5}(?:-[0-9]{4})?$"))) {
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

/**
 * Validation rule -- Address has at least 2 parts delimited by space, and first part must be house number
 * @author Franz
 *
 */
class AddressColumnValidator implements PeopleColumnValidator {

	@Override
	public void validateColumn(String value, People p) throws PCValidationFailure {
		try {
			String[] rawValues = value.trim().split("\\s+");
			// The non-production ready validation we perform here, is checking if address
			// have at least 2 parts, and first part is house number
			if (rawValues.length >= 2 && ((rawValues[0].length() > 0) && (rawValues[0].matches("[0-9]*")))
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
