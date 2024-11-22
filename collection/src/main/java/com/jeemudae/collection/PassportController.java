package com.jeemudae.collection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PassportController {

	@Autowired
	PassportRepository passportRepository;
	
	@GetMapping("/passports")
	public String showPassports(Model model) {
		List<Passport> passports = (List<Passport>) (List<?>) passportRepository.findAll();
		model.addAttribute("passports", passports);
		return "passports";
	}
}
