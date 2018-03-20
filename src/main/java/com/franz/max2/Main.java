/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.franz.max2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.franz.max2.dao.PeopleDAO;
import com.franz.max2.model.FourSQResponse;
import com.franz.max2.model.People;
import com.franz.max2.model.PeoplePostResponse;
import com.franz.max2.parser.PCValidationFailure;
import com.franz.max2.parser.PeopleParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

/**
 * Main class of SpringBoot web service
 * @author Franz
 *
 */
@Controller
@SpringBootApplication
@ComponentScan("com.franz.max2")
public class Main {
	
	
	@Autowired
	private PeopleDAO pDAO;
	
	@Value("${client.id}")
	private String clientId;
	
	@Value("${client.secret}")
	private String clientSecret;
	
	@Autowired
	private PeopleParser pp;
	
	private Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	/**
	 * Assignment Part II
	 * @param query
	 * @param limit
	 * @return
	 */
	@RequestMapping("/search4sq")
	@ResponseBody
	public ResponseEntity<FourSQResponse> search4sq(@RequestParam(value="query") String query, @RequestParam(value="limit") int limit) {
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.get("https://api.foursquare.com/v2/venues/search")
					.queryString("client_id", this.clientId)
					.queryString("client_secret", this.clientSecret)
					.queryString("v", "20170801")
					.queryString("ll", "40.7243,-74.0018")
					.queryString("query", query)
					.queryString("limit", limit)				
					.asJson();
			
			JSONArray venues = response.getBody().getObject().getJSONObject("response").getJSONArray("venues");
			List<String> places = new ArrayList<>();
			for(int i = 0; i < venues.length(); i++) {
				JSONObject venue = venues.getJSONObject(i);
				places.add(venue.getString("name"));
			}
			FourSQResponse result = new FourSQResponse();
			result.setPlaces(places.toArray(new String[0]));
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			FourSQResponse result = new FourSQResponse();
			result.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Assignment part III
	 * @param payload
	 * @return
	 */
	@RequestMapping(value = "/people", method = RequestMethod.POST, consumes = "text/plain")
	public ResponseEntity<PeoplePostResponse> createPeople(@RequestBody String payload){
		String[] pplAttributes = payload.trim().split("\\s*,\\s*");
		PeoplePostResponse ppr = new PeoplePostResponse();
		try {
			People p = pp.validate(Arrays.asList(pplAttributes));
			int id = this.pDAO.createPeople(p);
			ppr.setStatusCode(200);
			ppr.setId(id);
			return new ResponseEntity<>(ppr, HttpStatus.OK);
		} catch (PCValidationFailure e) {
			logger.warn("Validation of incoming data failed: {}", payload);
			ppr.setStatusCode(400);
			ppr.setErrorMessage("Validation failed: " + payload);
			return new ResponseEntity<>(ppr, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.warn("Found error:{} with payload: {}", e.getMessage(), payload);
			ppr.setStatusCode(400);
			ppr.setErrorMessage("Found error: " + e.getMessage());
			return new ResponseEntity<>(ppr, HttpStatus.BAD_REQUEST);
		}
	}

	@PostConstruct
	public void init() {
		Unirest.setObjectMapper(new ObjectMapper() {
		    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
		                = new com.fasterxml.jackson.databind.ObjectMapper();

		    public <T> T readValue(String value, Class<T> valueType) {
		        try {
		            return jacksonObjectMapper.readValue(value, valueType);
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
		    }

		    public String writeValue(Object value) {
		        try {
		            return jacksonObjectMapper.writeValueAsString(value);
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException(e);
		        }
		    }
		});
	}
	
	@PreDestroy
	public void shutdown() {
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			logger.warn("Got exception while shutdown Unirest: {}", e);
		}
	}
}
