package com.testing.api.validation;

import com.testing.api.mapping.PersonMapper;
import com.testing.api.resource.PersonAddressApi;
import com.testing.api.resource.PersonApi;
import com.testing.dto.PersonDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class AddressValidatorTestt {


    private ConstraintValidatorContext constraintValidatorContext;

    private AddressValidator validator;

    @BeforeEach
    public void init() {
        constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        validator = Mockito.spy(AddressValidator.class);
        doNothing().when(validator).buildConstraint(any(), anyString());
    }

    @org.junit.jupiter.api.Test
    public void testOK_correctData() {
        // for
        PersonAddressApi personAddressApi = new PersonAddressApi();
        personAddressApi.setCity("City");
        personAddressApi.setStreet("Street");
        personAddressApi.setZipCode("44-100");

        // when
        boolean result = validator.isValid(personAddressApi, constraintValidatorContext);

        // then
        Assertions.assertThat(result).isTrue();
    }

}
