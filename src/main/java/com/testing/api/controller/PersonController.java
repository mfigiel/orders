package com.testing.api.controller;

import com.testing.dto.PersonDto;
import com.testing.repository.PersonRepository;
import com.testing.api.resource.PersonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.testing.api.mapping.PersonMapper.mapToDto;
import static com.testing.api.mapping.PersonMapper.mapToPerson;

@RestController
@RequestMapping("/Person")
public class PersonController {

    private PersonRepository personRepository;


    @RequestMapping(
            value = "/get",
            method = RequestMethod.GET)
    public PersonApi get(@RequestParam String surname) {
        return mapToPerson(personRepository.findBySurname(surname));
    }

    @RequestMapping(
            value = "/delete",
            method = RequestMethod.GET)
    public Long delete(@RequestParam String surname) {
        return personRepository.removeBySurname(surname);

    }

    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST)
    public PersonDto save(@RequestBody PersonApi personApi) {
        return personRepository.save(mapToDto(personApi));
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
