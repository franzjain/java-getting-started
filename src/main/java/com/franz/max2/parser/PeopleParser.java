package com.franz.max2.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.franz.max2.model.People;
import com.franz.max2.model.PeopleSummaryCC;
import com.franz.max2.model.PeopleSummaryCCN;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
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
	
	public void printPeopleAssignment4(List<People> ppl) {
		Map<String, Set<People>> pplColorIndex = indexPeopleByColor(ppl);
		
		System.out.println();
		System.out.println("===================================================================================");
		System.out.println("Result of Assignment 4");
		System.out.println("===================================================================================");
		System.out.println();
		
		for(Entry<String, Set<People>> entry: pplColorIndex.entrySet()) {
			System.out.println(String.format("%-8s  %3s", entry.getKey(), entry.getValue().size()));
		}
	}
	
	public void printPeopleAssignment5(List<People> ppl) {
		Map<String, Set<People>> pplColorIndex = indexPeopleByColor(ppl);
		
		System.out.println();
		System.out.println("===================================================================================");
		System.out.println("Result of Assignment 5");
		System.out.println("===================================================================================");
		System.out.println();
		
		for(Entry<String, Set<People>> entry: pplColorIndex.entrySet()) {
			System.out.println(String.format("%-8s  %3s  %s", entry.getKey(), entry.getValue().size(), extractFullname(entry.getValue())));
		}
	}
	
	public void printPeopleAssignment6(List<People> ppl) {
		
		System.out.println();
		System.out.println("===================================================================================");
		System.out.println("Result of Assignment 6 - question4 reply in Json");
		System.out.println("===================================================================================");
		System.out.println();
		
		System.out.println(this.getPeopleSummaryCCJSON(ppl));
		
		System.out.println();
		System.out.println("===================================================================================");
		System.out.println("Result of Assignment 6 - question5 reply in Json");
		System.out.println("===================================================================================");
		System.out.println();
		
		System.out.println(this.getPeopleSummaryCCNJSON(ppl));
	}
	
	public String getPeopleSummaryCCJSON(List<People> ppl) {
		Map<String, Set<People>> indexedPpl = this.indexPeopleByColor(ppl);
		List<PeopleSummaryCC> ccList = new ArrayList<>();
		for(Entry<String, Set<People>> entry: indexedPpl.entrySet()) {
			PeopleSummaryCC pscc = new PeopleSummaryCC();
			pscc.setColor(entry.getKey());
			pscc.setCount(entry.getValue().size());
			ccList.add(pscc);
		}
		Gson gson = new GsonBuilder().create();
		return gson.toJson(ccList.toArray(new PeopleSummaryCC[0]));
	}
	
	public String getPeopleSummaryCCNJSON(List<People> ppl) {
		Map<String, Set<People>> indexedPpl = this.indexPeopleByColor(ppl);
		List<PeopleSummaryCCN> ccnList = new ArrayList<>();
		for(Entry<String, Set<People>> entry: indexedPpl.entrySet()) {
			PeopleSummaryCCN psccn = new PeopleSummaryCCN();
			psccn.setColor(entry.getKey());
			psccn.setCount(entry.getValue().size());
			psccn.setNames(this.extractFullname(entry.getValue()).toArray(new String[0]));
			ccnList.add(psccn);
		}
		Gson gson = new GsonBuilder().create();
		return gson.toJson(ccnList.toArray(new PeopleSummaryCCN[0]));
	}
	
	private List<String> extractFullname(Set<People> ppl){
		List<String> fullnames = new ArrayList<>();
		for(People p: ppl) {
			fullnames.add(p.getFirstname() + " " + p.getLastname());
		}
		return fullnames;
	}

	private Map<String, Set<People>> indexPeopleByColor(List<People> ppl) {
		Map<String, Set<People>> pplColorIndex = new HashMap<>();
		for(People p: ppl) {
			Set<People> sameColorPpl = pplColorIndex.get(p.getColor());
			if(sameColorPpl == null) {
				sameColorPpl = new HashSet<People>();
				pplColorIndex.put(p.getColor(), sameColorPpl);
			}
			sameColorPpl.add(p);
		}
		return pplColorIndex;
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

			pp.printPeopleAssignment4(parsedPeople);
			pp.printPeopleAssignment5(parsedPeople);
			pp.printPeopleAssignment6(parsedPeople);
			
			System.out.println();
			listReader.close();
			
		} catch (IOException e) {
			logger.error("Encountered error while parsing file", e);
		} finally {
			sc.close();
		}

	}
	
	

}
