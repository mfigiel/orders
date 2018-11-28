package com.testing.api.mapping;

import com.testing.api.resource.PersonAddressApi;
import com.testing.dto.PersonAddressDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(MockitoJUnitRunner.class)
public class AddressMapperTest {

    PersonAddressApi personAddressApi = new PersonAddressApi();
    PersonAddressDto personAddressDto = new PersonAddressDto();

    @Before
    public void init() {
        personAddressApi.setCity("Gliwice");
        personAddressApi.setHouseNumber(5);
        personAddressApi.setStreet("Street");
        personAddressApi.setZipCode("44-100");
        personAddressApi.setFlatNumber(4);

        personAddressDto.setCity("Gliwice");
        personAddressDto.setHouseNumber(5);
        personAddressDto.setStreet("Street");
        personAddressDto.setZipCode("44-100");
        personAddressDto.setFlatNumber(4);
    }

    @Test
    public void mapAddressToDto(){
        PersonAddressDto personAddressDto = AddressMapper.mapAdressToDto(personAddressApi);

        //then
        assertThat("Invalid mapper result", personAddressDto, is(notNullValue()));
        assertThat("Invalid mapper result", personAddressDto.getCreationDate().toString(), is(notNullValue()));
        assertThat("Invalid mapper result", personAddressDto.getCity(), is("Gliwice"));
        assertThat("Invalid mapper result", personAddressDto.getHouseNumber(), is(5));
        assertThat("Invalid mapper result", personAddressDto.getStreet(), is("Street"));
        assertThat("Invalid mapper result", personAddressDto.getZipCode(), is("44-100"));
        assertThat("Invalid mapper result", personAddressDto.getHouseNumber(), is(5));
        assertThat("Invalid mapper result", personAddressDto.getId(), is(nullValue()));
    }


    @Test
    public void mapAdressToApi(){
        PersonAddressApi personAddressApi = AddressMapper.mapAdressDtoToApi(personAddressDto);

        //then
        assertThat("Invalid mapper result", personAddressApi, is(notNullValue()));
        assertThat("Invalid mapper result", personAddressApi.getCity(), is("Gliwice"));
        assertThat("Invalid mapper result", personAddressApi.getHouseNumber(), is(5));
        assertThat("Invalid mapper result", personAddressApi.getStreet(), is("Street"));
        assertThat("Invalid mapper result", personAddressApi.getZipCode(), is("44-100"));
        assertThat("Invalid mapper result", personAddressApi.getHouseNumber(), is(5));
    }

}
