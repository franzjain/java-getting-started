package com.franz.max2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Response type of PeopleSummary for Assignment Part I - Question 6 of q5
 * @author Franz
 *
 */
@JsonInclude(Include.NON_NULL)
public class PeopleSummaryCCN {

	private String color;
	private int count;
	private String[] names;
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
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	
}
