package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("message", "패션피플 데이터베이스");
		
		return "index";
	}	
	
	@GetMapping("/individual/information/policy")
	public String getPolicy() {
		
		return "policy";
	}	

	@GetMapping("/termsOfUse")
	public String getTermsOfUser() {
		
		return "termsOfUse";
	}	
}