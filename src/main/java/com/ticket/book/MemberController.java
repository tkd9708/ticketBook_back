package com.ticket.book;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ticket.dto.MemberDto;
import com.ticket.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	MemberService service;
	
	@Autowired
	BCryptPasswordEncoder pwdEncoder;

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
		
		String pwd = pwdEncoder.encode(dto.getPass());
		dto.setPass(pwd);
		
		service.insert(dto);
	}
	
	@PostMapping("/member/login")
	public boolean login(@RequestBody MemberDto dto) {
		MemberDto login = service.chkEmail(dto.getEmail());
		
		if(login != null) {
			boolean pwdMatch = pwdEncoder.matches(dto.getPass(), login.getPass());
			
			if(pwdMatch)
				return true;
		}
		
		return false;
	}
	
	@GetMapping("/member/list")
	public List<MemberDto> getList() {
		return service.getList();
	}
	
	@GetMapping("/member/select")
	public MemberDto getData(@RequestParam String _id) {
		MemberDto dto = service.getData(_id);

		return dto;
	}
	
	@PostMapping("/member/update")
	public void update(@RequestBody MemberDto dto) {
		service.update(dto);
	}
	
	@GetMapping("member/delete")
	public void delete(@RequestParam String _id) {
		service.delete(_id);
	}
}
