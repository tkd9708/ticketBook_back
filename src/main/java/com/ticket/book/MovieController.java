package com.ticket.book;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ticket.dto.MovieDto;
import com.ticket.service.MovieService;

@RestController
public class MovieController {

	@Autowired
	MovieService service;
	
	String photoname;
	
	@PostMapping(value = "/movie/upload", consumes = {"multipart/form-data"})
	public Map<String, String> upload(@RequestParam MultipartFile upload, HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/save");
		System.out.println(path);
		
		int pos = upload.getOriginalFilename().lastIndexOf(".");
		String ext = upload.getOriginalFilename().substring(pos);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		photoname = "book" + sdf.format(date) + ext;
		
		try {
			upload.transferTo(new File(path + "\\" + photoname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("photoname", upload.getOriginalFilename());
		
		return map;
	}
	
	@GetMapping("/movie/delupload")
	public void delUpload(HttpServletRequest request) {
		
		if(photoname != null) {
			String path = request.getSession().getServletContext().getRealPath("/WEB-INF/save");
			System.out.println(path);
			File file = new File(path + "\\" + photoname);
			
			if(file.exists())
				file.delete();
		}
		
		photoname = null;
		
	}
	
	@PostMapping("/movie/insert")
	public void insert(@RequestBody MovieDto dto, HttpServletRequest request) {
		if(photoname == null)
			dto.setPhoto("n");
		else 
			dto.setPhoto(photoname);
		
		service.insert(dto);
		
		photoname = null;
	}
	
	@GetMapping("/movie/list")
	public List<MovieDto> getList(@RequestParam String memId){
		return service.getList(memId);
	}
	
	@GetMapping("/movie/select")
	public MovieDto getData(@RequestParam String _id) {
		return service.getData(_id);
	}
	
	@GetMapping("/movie/delete")
	public void delete(@RequestParam String _id, HttpServletRequest request) {
		String delPhoto = service.getData(_id).getPhoto();
		
		if(!delPhoto.equals("n")) {
			String path = request.getSession().getServletContext().getRealPath("/WEB-INF/save");
			System.out.println(path);
			File file = new File(path + "\\" + delPhoto);
			
			if(file.exists())
				file.delete();
		}
		
		service.delete(_id);
	}
	
	@PostMapping("/movie/update")
	public void update(@RequestBody MovieDto dto) {
		if(photoname == null) {
			dto.setPhoto("n");
		}
		else
			dto.setPhoto(photoname);
		
		service.update(dto);
	}
}
