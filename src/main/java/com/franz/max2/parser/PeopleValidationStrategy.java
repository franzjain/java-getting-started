package com.franz.max2.parser;

import java.util.ArrayList;
import java.util.List;

import com.franz.max2.model.People;

/**
 * A single strategy holds all column validator for a row. Order, number
 * , and type of PeopleColumnValidator can be formulated to form different strategies
 * @author Franz
 *
 */
public class PeopleValidationStrategy {

	private List<PeopleColumnValidator> pcvList = new ArrayList<>();
	
	private PeopleValidationStrategy nextStrategy;
	
	public People validate(List<String> values) throws PCValidationFailure {
		People p = new People();
		try {
			for(int i = 0; i < pcvList.size(); i++) {
				pcvList.get(i).validateColumn(values.get(i), p);
			}
		} catch (PCValidationFailure e) {
			if(this.nextStrategy != null) {
				return this.nextStrategy.validate(values);
			} else {
				throw e;
			}
		}
		return p;
	}

	public PeopleValidationStrategy setNextStrategy(PeopleValidationStrategy nextStrategy) {
		this.nextStrategy = nextStrategy;
		return this.nextStrategy;
	}
	
	public void addPeopleColumnValidator(PeopleColumnValidator pcv) {
		this.pcvList.add(pcv);
	}
}
