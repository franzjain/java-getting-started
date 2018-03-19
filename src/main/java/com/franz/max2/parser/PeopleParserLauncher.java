package com.franz.max2.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.franz.max2.model.People;

/**
 * Script launcher to start parsing user input csv file and print out result of Assignment Part I 
 * @author Franz
 *
 */
public class PeopleParserLauncher {

	private static Logger logger = LoggerFactory.getLogger(PeopleParserLauncher.class);
	
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
				// Add successfully parsed data to parsedPeople List, but only log warning when found corrupted row during parsing
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

			Map<String, Set<People>> pplColorIndex = pp.indexPeopleByColor(parsedPeople);
			
			System.out.println();
			System.out.println("===================================================================================");
			System.out.println("Result of Assignment 4");
			System.out.println("===================================================================================");
			System.out.println();
			
			for(Entry<String, Set<People>> entry: pplColorIndex.entrySet()) {
				System.out.println(String.format("%-8s  %3s", entry.getKey(), entry.getValue().size()));
			}
		
			System.out.println();
			System.out.println("===================================================================================");
			System.out.println("Result of Assignment 5");
			System.out.println("===================================================================================");
			System.out.println();
			
			for(Entry<String, Set<People>> entry: pplColorIndex.entrySet()) {
				System.out.println(String.format("%-8s  %3s  %s", entry.getKey(), entry.getValue().size(), pp.extractFullname(entry.getValue())));
			}
		
			System.out.println();
			System.out.println("===================================================================================");
			System.out.println("Result of Assignment 6 - question4 reply in Json");
			System.out.println("===================================================================================");
			System.out.println();
			
			System.out.println(pp.getPeopleSummaryCCJSON(parsedPeople));
			
			System.out.println();
			System.out.println("===================================================================================");
			System.out.println("Result of Assignment 6 - question5 reply in Json");
			System.out.println("===================================================================================");
			System.out.println();
			
			System.out.println(pp.getPeopleSummaryCCNJSON(parsedPeople));
		
			System.out.println();
			listReader.close();
			
		} catch (IOException e) {
			logger.error("Encountered error while parsing file", e);
		} finally {
			sc.close();
		}
	}
}
