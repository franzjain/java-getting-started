package com.franz.max2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Response type of PeopleSummary for Assignment Part I - Question 6 of q4
 * @author Franz
 *
 */
@JsonInclude(Include.NON_NULL)
public class PeopleSummaryCC {

	private String color;
	private int count;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
