package com.testing.api.validation;

import com.testing.api.resource.PersonAddressApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private AddressValidator validator;

    @BeforeEach
    public void init() {
        constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        validator = Mockito.spy(AddressValidator.class);
        doNothing().when(validator).buildConstraint(any(), anyString());
    }

    @Test
    public void testOK_correctData() {
        // for
        PersonAddressApi personAddressApi = new PersonAddressApi();
        personAddressApi.setCity("City");
        personAddressApi.setStreet("Street");
        personAddressApi.setZipCode("44-100");

        // when
        boolean result = validator.isValid(personAddressApi, constraintValidatorContext);

        // then
        assertThat(result).isTrue();
    }
}