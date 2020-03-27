package com.orange;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactRestController {

	private Map<Long, Contact> db = new HashMap<Long, Contact>(); 
	
	private static long index = 1;
	
	/**
	 * 
	   {
         "name": "Szymon",
         "lastName": "Lisiecki",
         "phone": "999 999 999"
       }
	 * @param contact
	 */
	@PostMapping("/contacts")
	public ResponseEntity<Contact> saveContact(@RequestBody Contact contact) {
		
		contact.setId(index++);
		db.put(contact.getId(), contact);
		return ResponseEntity.status(HttpStatus.CREATED).body(contact);
	}
	
	@GetMapping("/contacts/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable long id) {
		Contact contact = db.get(id);
		if(contact !=null) {
			// jest kontakt
			return ResponseEntity.status(HttpStatus.OK).body(contact);			
		} else {
			// nie ma kontaktu
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/contacts")
	public Collection<Contact> getAll() {
		return db.values();
	}
}
