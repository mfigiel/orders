package com.testing.api.mapping;

import com.testing.api.resource.PersonApi;
import com.testing.dto.PersonDto;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class PersonMapper {

    public static PersonDto mapToDto(PersonApi personApi) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(personApi, PersonDto.class);
    }

    public static PersonApi mapToPerson(PersonDto personDto) {
        ModelMapper modelMapper = new ModelMapper();
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
