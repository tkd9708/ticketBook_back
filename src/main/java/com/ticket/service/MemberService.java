package com.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ticket.dto.MemberDto;

@Repository
public class MemberService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	// insert
	public void insert(MemberDto dto) {
		mongoTemplate.insert(dto, "Member");
	}
	
	// find all
	public List<MemberDto> getList(){
		return mongoTemplate.findAll(MemberDto.class, "Member");
	}
	
	// find one
	public MemberDto getData(String _id) {
		Criteria criteria = new Criteria("_id");
		criteria.is(_id);
			
		Query query = new Query(criteria);
		MemberDto mem = mongoTemplate.findOne(query, MemberDto.class, "Member");
			
		return mem;
	}
	
	// update
	public void update(MemberDto dto) {
		Criteria criteria = new Criteria("_id");
		criteria.is(dto.get_id());
		
		Query query = new Query(criteria);
		
		Update update = new Update();
		update.set("name", dto.getName());
		update.set("pass", dto.getPass());
		
		mongoTemplate.updateFirst(query, update, "Member");
	}
	
	// remove data in collection
	public void delete(String _id) {
		Query query = new Query(new Criteria("_id").is(_id));
		mongoTemplate.remove(query, "Member");
	}
}
