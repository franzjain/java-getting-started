package com.franz.max2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Response type of Foursquare facade service
 * @author Franz
 *
 */
@JsonInclude(Include.NON_NULL)
public class FourSQResponse {

	private String[] places;
	
	private String errorMessage;

	public String[] getPlaces() {
		return places;
	}

	public void setPlaces(String[] places) {
		this.places = places;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
