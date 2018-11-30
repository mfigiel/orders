package com.testing.api.mapping;

import com.testing.api.resource.PersonApi;
import com.testing.dto.PersonDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class PersonMapper {

    private PersonMapper() {
        throw new IllegalStateException("Utility class");
    }

    static ModelMapper modelMapper = new ModelMapper();

    public static PersonDto mapToDto(PersonApi personApi) {
        return modelMapper.map(personApi, PersonDto.class);
    }

    public static PersonApi mapToPerson(PersonDto personDto) {
        return modelMapper.map(personDto, PersonApi.class);
    }

    public static List<PersonApi> mapToListOfPersons(List<PersonDto> personDtoList) {
        List<PersonApi> personApiList = new ArrayList<>();
        for(PersonDto personDto:personDtoList) {
            personApiList.add(mapToPerson(personDto));
        }
        return personApiList;
    }
}
