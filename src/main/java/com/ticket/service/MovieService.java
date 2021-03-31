package com.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
	
	// find one
	public MovieDto getData(String _id) {
		Criteria criteria = new Criteria("_id");
		criteria.is(_id);
		
		Query query = new Query(criteria);
		
		return mongoTemplate.findOne(query, MovieDto.class, "Movie");
	}
	
	// remove
	public void delete(String _id) {
		Query query = new Query(new Criteria("_id").is(_id));
		mongoTemplate.remove(query, "Movie");
	}
	
	//update
	public void update(MovieDto dto) {
		Criteria criteria = new Criteria("_id");
		criteria.is(dto.get_id());
		
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("movieTitle", dto.getMovieTitle());
		update.set("photo", dto.getPhoto());
		update.set("location", dto.getLocation());
		update.set("watchDate", dto.getWatchDate());
		update.set("star", dto.getStar());
		update.set("review", dto.getReview());
		
		mongoTemplate.updateFirst(query, update, "Movie");
	}
}
