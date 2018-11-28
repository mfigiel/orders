package com.testing.api.controller;

import com.testing.repository.PersonRepository;
import com.testing.api.resource.PersonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.testing.api.mapping.PersonMapper.mapToDto;
import static com.testing.api.mapping.PersonMapper.mapToListOfPersons;

@RestController
@RequestMapping("/Person")
public class PersonController {

    private PersonRepository personRepository;


    @GetMapping
    public List<PersonApi> get() {
        return mapToListOfPersons(personRepository.findAll());
    }

    @PostMapping
    public void save() {
         personRepository.save(mapToDto(new PersonApi("name","surname", null)));
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
