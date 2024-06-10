package com.covid19.tracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	List <LocationStats> locationStatsList;
	@Autowired
	CoronaVirusDataService coronaVirusDataService ;
	@GetMapping("/")
	public String home (Model model) {
		locationStatsList = coronaVirusDataService.getLocationStatsList();
		model.addAttribute("locstat", locationStatsList);  
		int totalReportedCasesSum = locationStatsList.stream().mapToInt( stat -> Integer.parseInt(stat.getTotalCasesToday())).sum();
		model.addAttribute("totalReportedCases", totalReportedCasesSum);
		return "home";
	}
}
