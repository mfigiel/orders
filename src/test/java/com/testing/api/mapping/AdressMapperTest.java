package com.testing.api.mapping;

import com.testing.api.resource.PersonAddressApi;
import com.testing.dto.PersonAddressDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class AdressMapperTest {

    @Autowired
    PersonMapper mapper;
    PersonAddressApi personAdressApi = new PersonAddressApi();
    PersonAddressDto personAdressDto = new PersonAddressDto();

    @Test
    public void testMethod(){

    }

}
