package com.testing.api.controller;

import com.testing.repository.PersonRepository;
import com.testing.api.resource.PersonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.testing.api.mapping.PersonMapper.mapToDto;
import static com.testing.api.mapping.PersonMapper.mapToListOfPersons;

@RestController
@RequestMapping("/Person")
public class PersonController {

    private PersonRepository personRepository;


    @RequestMapping(method = RequestMethod.GET)
    public List<PersonApi> get() {
        return mapToListOfPersons(personRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save() {
         personRepository.save(mapToDto(new PersonApi(new Date(),"name","surname", null)));
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
