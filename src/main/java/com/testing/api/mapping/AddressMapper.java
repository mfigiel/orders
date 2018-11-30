package com.testing.api.mapping;

import com.testing.api.resource.PersonAddressApi;
import com.testing.dto.PersonAddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

public class AddressMapper {

    private AddressMapper() {
        throw new IllegalStateException("Utility class");
    }

    static ModelMapper modelMapper = new ModelMapper();

    public static PersonAddressDto mapAdressToDto(PersonAddressApi personAddressApi) {
        return modelMapper.map(personAddressApi, PersonAddressDto.class);
    }

    public static PersonAddressApi mapAdressDtoToApi(PersonAddressDto personAddressDto) {
        return modelMapper.map(personAddressDto, PersonAddressApi.class);
    }
}
