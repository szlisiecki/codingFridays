package com.orange;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private ContactRepositoryDao contactRepositoryDao;
	
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
		
		
		Contact savedContact = contactRepositoryDao.saveAndFlush(contact);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
	}
	
	@GetMapping("/contacts/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable long id) {

		Optional<Contact> contact = contactRepositoryDao.findById(id);
		if(contact.isPresent()) {
			// jest kontakt
			return ResponseEntity.status(HttpStatus.OK).body(contact.get());			
		} else {
			// nie ma kontaktu
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/contacts")
	public ResponseEntity<Collection<Contact>> getAll() {	
		return ResponseEntity.status(HttpStatus.OK).body(contactRepositoryDao.findAll());
	}
}
