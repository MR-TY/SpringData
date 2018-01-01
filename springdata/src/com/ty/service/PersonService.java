package com.ty.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ty.entity.Person;
import com.ty.entity.PersonRepository;

@Service
@Transactional
public class PersonService {
	@Resource
	private PersonRepository personRepository;
	
	public void updatePerson(String email,Integer id){
		personRepository.updatePersonEmail(email, id);
	}
	
	public void deletePerson(Integer id){
		personRepository.deletePerson(id);
	}
	
	public void savePerson(String email,String lastName){
		personRepository.savePerson(email, lastName);
	}
	public void crudPerson(List<Person> persons){
		personRepository.save(persons);
	}
}
