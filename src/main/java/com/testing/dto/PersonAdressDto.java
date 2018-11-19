package com.testing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PersonAdressDto {

    @Id
    private Long id;
    private Date creationDate;
    private String city;
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;
    private String zipCode;
}
