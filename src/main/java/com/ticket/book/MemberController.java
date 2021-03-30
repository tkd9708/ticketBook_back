package com.ticket.book;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ticket.dto.MemberDto;
import com.ticket.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	MemberService service;

	@GetMapping("/")
	public ModelAndView goHome() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		return mv;
	}
	
	@PostMapping("/member/insert")
	public void insert(@RequestBody MemberDto dto, HttpServletRequest request) {
		
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/save");
		System.out.println(path);
		
		
		service.insert(dto);
	}
	
	@GetMapping("/member/list")
	public List<MemberDto> getList() {
		return service.getList();
	}
	
	@GetMapping("/member/select")
	public MemberDto getData(String _id) {
		MemberDto dto = service.getData(_id);

		return dto;
	}
	
	@PostMapping("/member/update")
	public void update(@RequestBody MemberDto dto) {
		service.update(dto);
	}
	
	@GetMapping("member/delete")
	public void delete(String _id) {
		service.delete(_id);
	}
}
