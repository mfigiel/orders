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
public class PersonDto {

    @Id
    private Long id;
    private Date creationDate;
    private String Name;
    private String Surname;
    private PersonAdressDto adress;
}
