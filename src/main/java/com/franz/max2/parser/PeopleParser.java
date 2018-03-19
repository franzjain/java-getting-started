package com.franz.max2.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.franz.max2.model.People;
import com.franz.max2.model.PeopleSummaryCC;
import com.franz.max2.model.PeopleSummaryCCN;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Holder of multiple validation strategy and entry point to validate People raw data 
 * @author Franz
 *
 */
@Component
public class PeopleParser {
	
	private static Logger logger = LoggerFactory.getLogger(PeopleParser.class);

	/**
	 * Start of Strategy Chain
	 */
	private PeopleValidationStrategy pvs;
	
	public People validate(List<String> values) throws PCValidationFailure {
		logger.info("Start validating {}", values);
		return this.pvs.validate(values);
	}
	
	/**
	 * Initialize four Strategy objects to parse raw data having four possible mixed column orders
	 */
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
	
	/**
	 * Convert People data to Json format for Assignment Part I q4
	 * @param ppl
	 * @return
	 */
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
	
	/**
	 * Convert People data to Json format for Assignment Part I q5
	 * @param ppl
	 * @return
	 */
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
	
	List<String> extractFullname(Set<People> ppl){
		List<String> fullnames = new ArrayList<>();
		for(People p: ppl) {
			fullnames.add(p.getFirstname() + " " + p.getLastname());
		}
		return fullnames;
	}

	Map<String, Set<People>> indexPeopleByColor(List<People> ppl) {
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
}
