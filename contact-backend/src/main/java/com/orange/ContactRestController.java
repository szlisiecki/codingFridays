package com.orange;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactRestController {
	
	@Autowired
	private ContactRepositoryDao contactRepositoryDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
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
	public ResponseEntity<Contact> saveContact(@RequestBody ContactDto contactDto) {
		
		
		Contact savedContact = contactRepositoryDao.saveAndFlush(convertToEntity(contactDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
	}
	
	@GetMapping("/contacts/{id}")
	public ResponseEntity<ContactDto> getContact(@PathVariable long id) {

		Optional<Contact> contact = contactRepositoryDao.findById(id);
		if(contact.isPresent()) {
			// jest kontakt
			return ResponseEntity.status(HttpStatus.OK).body(convertToDto(contact.get()));			
		} else {
			// nie ma kontaktu
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/contacts")
	public ResponseEntity<Collection<ContactDto>> getAll(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page, 
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
			@RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy
			) {
		
		if (size > 100) {
			size = 100;
		}
		//contactRepositoryDao.findAll(PageRequest.of(4, 10));
		
		Direction direction = "asc".equalsIgnoreCase(sort) ? Direction.ASC : Direction.DESC;

		
//		Direction direction = null;
//		if (sort.equals("asc")) {
//			direction = Direction.ASC; 
//		} else {
//			direction = Direction.DESC;
//		}
		
		Page<Contact> contactPage = contactRepositoryDao.findAll(PageRequest.of(page, size, direction, sortBy.split(",")));
		
		List<ContactDto> allContactDto = contactPage.stream().map(contact -> convertToDto(contact)).collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(allContactDto);

		//		ArrayList<ContactDto> contactDtoList = new ArrayList<ContactDto>();
//		for(Contact contact : allContacts) {
//			ContactDto contactDto = convertToDto(contact);
//			contactDtoList.add(contactDto);
//		}
		
		
	}
	
	private ContactDto convertToDto(Contact contact) {
		return modelMapper.map(contact, ContactDto.class);
	}
	
	private Contact convertToEntity(ContactDto contactDto) {
		return modelMapper.map(contactDto, Contact.class);
	}
	
//	private ContactDto convertToDto(Contact contact) {
//		ContactDto contactDto = new ContactDto();
//		contactDto.setId(contact.getId());
//		contactDto.setName(contact.getName());
//		contactDto.setLastName(contact.getLastName());
//		contactDto.setPhone(contact.getPhone());
//		return contactDto;
//	}
}
