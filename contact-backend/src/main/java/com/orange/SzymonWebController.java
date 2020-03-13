package com.orange;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SzymonWebController {

	
	@GetMapping("/")
	public String hello() {
		return "Hello";
	}
	
	@GetMapping("/szymon")
	public String helloSzymon() {
		return "Hello Szymon";
	}
	
	@GetMapping("/szymon2")
	public String helloSzymon2() {
		return "Hello Szymon 2";
	}
}
