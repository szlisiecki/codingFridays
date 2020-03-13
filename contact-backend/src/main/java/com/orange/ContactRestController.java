package com.orange;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactRestController {

	@PostMapping("/contacts")
	public void saveContact(Contact contact) {
		
	}
}
