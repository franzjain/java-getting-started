package com.franz.max2.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.franz.max2.model.People;

/**
 * Data Access Object for People data in persistence layer
 * @author Franz
 *
 */
@Repository
public class JdbcPeopleDAOImpl implements PeopleDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int createPeople(People p) {
		SimpleJdbcInsert ji = new SimpleJdbcInsert(this.jdbcTemplate);
		ji.withTableName("people").usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("firstname", p.getFirstname());
		parameters.put("lastname", p.getLastname());
		parameters.put("address", p.getAddress());
		parameters.put("zipcode", p.getZipcode());
		parameters.put("phone_number", p.getPhoneNumber());
		parameters.put("color", p.getColor());
		Number key = ji.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return key.intValue();
	}

}
