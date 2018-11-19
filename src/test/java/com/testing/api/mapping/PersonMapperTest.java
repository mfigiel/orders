package com.testing.api.mapping;

import com.testing.api.resource.PersonApi;
import com.testing.dto.PersonDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PersonMapperTest {

    @Autowired
    PersonMapper mapper;
    PersonApi personApi = new PersonApi();
    PersonDto personDto = new PersonDto();

    @Before
    public void init() throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        personApi.setCreationDate(date.parse("17/07/1989"));
        personApi.setName("Name");
        personApi.setSurname("Surname");

        personDto.setCreationDate(date.parse("17/07/1989"));
        personDto.setName("Name");
        personDto.setId(1L);
        personDto.setSurname("Surname");
    }

    @Test
    public void testMapToDto() {
        PersonDto personDto = mapper.mapToDto(personApi);

        //then
        assertThat("Invalid mapper result", personDto, is(notNullValue()));
        assertThat("Invalid mapper result", personDto.getCreationDate().toString(), is("Mon Jul 17 00:00:00 CEST 1989"));
        assertThat("Invalid mapper result", personDto.getName(), is("Name"));
        assertThat("Invalid mapper result", personDto.getSurname(), is("Surname"));
        assertThat("Invalid mapper result", personDto.getId(), is(nullValue()));

    }

    @Test
    public void testMapToApi() {
        PersonApi personApi = mapper.mapToPerson(personDto);

        //then
        assertThat("Invalid mapper result", personApi, is(notNullValue()));
        assertThat("Invalid mapper result", personApi.getCreationDate().toString(), is("Mon Jul 17 00:00:00 CEST 1989"));
        assertThat("Invalid mapper result", personApi.getName(), is("Name"));
        assertThat("Invalid mapper result", personApi.getSurname(), is("Surname"));

    }

    @Test
    public void testMapToDtoWithConstructor() throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        PersonApi personApi = new PersonApi(date.parse("17/07/1989"), "Name", "Surname",null);
        PersonDto personDto = mapper.mapToDto(personApi);

        //then
        assertThat("Invalid mapper result", personDto, is(notNullValue()));
        assertThat("Invalid mapper result", personDto.getCreationDate().toString(), is("Mon Jul 17 00:00:00 CEST 1989"));
        assertThat("Invalid mapper result", personDto.getName(), is("Name"));
        assertThat("Invalid mapper result", personDto.getSurname(), is("Surname"));
        assertThat("Invalid mapper result", personDto.getAdress(), is(nullValue()));

    }

    @Test
    public void testMapToDtoList() {
        List<PersonDto> personDtoList = new ArrayList<>();
        personDtoList.add(personDto);
        List<PersonApi> personApiList = mapper.mapToListOfPersons(personDtoList);

        //then
        assertThat("Invalid mapper result", personApiList, is(notNullValue()));
        assertThat("Invalid mapper result", personApiList.size(), is(1));
        assertThat("Invalid mapper result", personApiList.get(0).getCreationDate().toString(), is("Mon Jul 17 00:00:00 CEST 1989"));
        assertThat("Invalid mapper result", personApiList.get(0).getName(), is("Name"));
        assertThat("Invalid mapper result", personApiList.get(0).getSurname(), is("Surname"));

    }

}
