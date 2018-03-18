package com.franz.max2.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.franz.max2.model.People;

@Resource
public class PeopleParser {
	
	private static Logger logger = LoggerFactory.getLogger(PeopleParser.class);

	private PeopleValidationStrategy pvs;
	
	public People validate(List<String> values) throws PCValidationFailure {
		return this.pvs.validate(values);
	}
	
	@PostConstruct
	public void init() {
		PeopleValidationStrategy pvs1 = new PeopleValidationStrategy();
		pvs1.addPeopleColumnValidator(new FirstnameColumnValidator());
		pvs1.addPeopleColumnValidator(new LastnameColumnValidator());
		pvs1.addPeopleColumnValidator(new PhoneNumColumnValidator());
		pvs1.addPeopleColumnValidator(new ColorColumnValidator());
		pvs1.addPeopleColumnValidator(new ZipcodeColumnValidator());
		
		PeopleValidationStrategy pvs2 = new PeopleValidationStrategy();
		pvs2.addPeopleColumnValidator(new FullnameColumnValidator());
		pvs2.addPeopleColumnValidator(new ColorColumnValidator());
		pvs2.addPeopleColumnValidator(new ZipcodeColumnValidator());
		pvs2.addPeopleColumnValidator(new PhoneNumColumnValidator());
		
		PeopleValidationStrategy pvs3 = new PeopleValidationStrategy();
		pvs3.addPeopleColumnValidator(new FirstnameColumnValidator());
		pvs3.addPeopleColumnValidator(new LastnameColumnValidator());
		pvs3.addPeopleColumnValidator(new ZipcodeColumnValidator());
		pvs3.addPeopleColumnValidator(new PhoneNumColumnValidator());
		pvs3.addPeopleColumnValidator(new ColorColumnValidator());
		
		PeopleValidationStrategy pvs4 = new PeopleValidationStrategy();
		pvs4.addPeopleColumnValidator(new FullnameColumnValidator());
		pvs4.addPeopleColumnValidator(new AddressColumnValidator());
		pvs4.addPeopleColumnValidator(new ZipcodeColumnValidator());
		pvs4.addPeopleColumnValidator(new PhoneNumColumnValidator());
		pvs4.addPeopleColumnValidator(new ColorColumnValidator());
		
		pvs1.setNextStrategy(pvs2).setNextStrategy(pvs3).setNextStrategy(pvs4);
		this.pvs = pvs1;
	}
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		PeopleParser pp = new PeopleParser();
		pp.init();
		
		System.out.println("Please enter csv file path");
		
		try {
			ICsvListReader listReader = new CsvListReader(new FileReader(sc.nextLine()), CsvPreference.STANDARD_PREFERENCE);
			
			List<People> parsedPeople = new ArrayList<>();
			List<String> currentRow;
			int rowCount = 0;
			while((currentRow = listReader.read()) != null) {
				rowCount++;
				try {
					parsedPeople.add(pp.validate(currentRow));
				} catch (PCValidationFailure e) {
					logger.warn("Unable to parse following data with all available strategies");
					logger.warn("Data with error -> {}", currentRow);
				} catch (Exception e) {
					logger.error("Encountered error while parsing file", e);
				} 
			}
			
			logger.info("Finished parsing {} people objects out of {} lines", parsedPeople.size(), rowCount);
			System.out.println(parsedPeople);
			listReader.close();
			
		} catch (IOException e) {
			logger.error("Encountered error while parsing file", e);
		} finally {
			sc.close();
		}

	}
	
	

}
