package com.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ticket.dto.MovieDto;

@Repository
public class MovieService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	// insert
	public void insert(MovieDto dto) {
		mongoTemplate.insert(dto, "Movie");
	}
	
	// find all : member id에 해당하는 list
	public List<MovieDto> getList(String memId){
		Criteria criteria = new Criteria("memId");
		criteria.is(memId);
			
		Query query = new Query(criteria);
		
		return mongoTemplate.find(query, MovieDto.class, "Movie");
	}
}
