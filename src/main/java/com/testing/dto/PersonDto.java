package com.testing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "person")
public class PersonDto {

    @Id
    private Long id;
    private Date creationDate = new Date();
    private String Name;
    private String Surname;
    @OneToOne
    @JoinColumn(name = "personaddressdto_id", nullable = false)
    private PersonAddressDto address;
}
